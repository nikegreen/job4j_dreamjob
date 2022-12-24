package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {
    private static final Logger LOGGER = Logger.getLogger(CandidateDbStore.class);
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    new City(it.getInt("city_id"), null),
                                    it.getString("description"),
                                    it.getTimestamp("created").toLocalDateTime(),
                                    it.getBytes("photo")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all candidates. " + e.getMessage());
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name, city_id, description, created, photo) "
                             + "VALUES (?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCity().getId());
            ps.setString(3, candidate.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(5, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error("add candidate=" + candidate + ". " + e.getMessage());
        }
        return candidate;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            new  City(it.getInt("city_id"), null),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBytes("photo")
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find candidate by id=" + id + ". " + e.getMessage());
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                "UPDATE candidate SET name=?, city_id=?, description=?, created=?, photo=?"
                     + " WHERE id=?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCity().getId());
            ps.setString(3, candidate.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(5, candidate.getPhoto());
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Update candidate=" + candidate + ". " + e.getMessage());
        }
    }
}
