package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {
    private static final Logger LOGGER = Logger.getLogger(CandidateDbStore.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM candidate";
    private static final String SQL_ADD = "INSERT INTO "
            + "candidate(name, city_id, description, created, photo) "
            + "VALUES (?,?,?,?,?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE candidate SET "
            + "name=?, city_id=?, description=?, created=?, photo=?"
            + " WHERE id=?";
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            createCandidate(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all candidates. " + e.getMessage(), e);
        }
        return candidates;
    }

    private static Candidate createCandidate(ResultSet it) throws SQLException {
        return new Candidate(
                it.getInt("id"),
                it.getString("name"),
                new City(it.getInt("city_id"), null),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(),
                it.getBytes("photo")
        );
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
            LOGGER.error("add candidate=" + candidate + ". " + e.getMessage(), e);
        }
        return candidate;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidate(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error("find candidate by id=" + id + ". " + e.getMessage(), e);
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                SQL_UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCity().getId());
            ps.setString(3, candidate.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(5, candidate.getPhoto());
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Update candidate=" + candidate + ". " + e.getMessage(), e);
        }
    }
}
