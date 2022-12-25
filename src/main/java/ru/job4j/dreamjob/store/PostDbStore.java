package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {
    private static final Logger LOGGER = Logger.getLogger(PostDBStore.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM post";
    private static final String SQL_ADD = "INSERT INTO "
            + "post(name, visible, city_id, description, created) "
            + "VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE post SET name=?, visible=?, city_id=?, description=?, created=? WHERE id=?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM post WHERE id = ?";
    private final BasicDataSource pool;

    public PostDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            createPost(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all Post. " + e.getMessage(), e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.getVisible());
            ps.setInt(3, post.getCity().getId());
            ps.setString(4, post.getDescription());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error("add post=" + post + ". " + e.getMessage(), e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
          PreparedStatement ps =  cn.prepareStatement(
                  SQL_UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.getVisible());
            ps.setInt(3, post.getCity().getId());
            ps.setString(4, post.getDescription());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Update post=" + post + ". " + e.getMessage(), e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createPost(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error("find by id=" + id + ". " + e.getMessage(), e);
        }
        return null;
    }

    private Post createPost(ResultSet it) throws SQLException {
        return new Post(
                it.getInt("id"),
                it.getString("name"),
                it.getBoolean("visible"),
                new City(it.getInt("city_id"), null),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime()
        );
    }
}
