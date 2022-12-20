package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                        new Post(
                                it.getInt("id"),
                                it.getString("name"),
                                it.getBoolean("visible"),
                                new City(it.getInt("city_id"), null),
                                it.getString("description"),
                                it.getTimestamp("created").toLocalDateTime()
                        )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, visible, city_id, description, created) "
                     + "VALUES (?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.getVisible());
            ps.setInt(3, post.getCity().getId());
            ps.setString(4, post.getDescription());
            //String str = post.getCreated().format(DTF);
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
          PreparedStatement ps =  cn.prepareStatement(
          "UPDATE post SET name=?, visible=?, city_id=?, description=?, created=? WHERE id=?",
           PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.getVisible());
            ps.setInt(3, post.getCity().getId());
            ps.setString(4, post.getDescription());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getBoolean("visible"),
                            new  City(it.getInt("city_id"), null),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
