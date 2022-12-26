package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class UserDbStoreTest {
    private static BasicDataSource dataSource;

    @BeforeAll
    public static void initConnection() {

            dataSource = new Main().loadPool();
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        dataSource.close();
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = dataSource
                .getConnection()
                .prepareStatement("delete from users")) {
            statement.execute();
        }
    }
    @Test
    public void whenCreateUserAndFindById() {
        UserDbStore store = new UserDbStore(dataSource);
        User user = new User(0, "m1@mail.ru", "1234");
        user = store.add(user).orElse(null);
        User userInDb = store.findById(user.getId()).orElse(null);
        assertThat(userInDb.getId()).isEqualTo(user.getId());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
        assertThat(userInDb.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void whenCreate2UserAndFindById() {
        UserDbStore store = new UserDbStore(dataSource);
        User user1 = new User(0, "m1@mail.ru", "1234");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());

        User user2 = new User(0, "m2@mail.ru", "1234");
        user2 = store.add(user2).orElse(null);
        User userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void whenCreate2UserAndFindAll() {
        UserDbStore store = new UserDbStore(dataSource);
        int count = store.findAll().size();
        User user1 = new User(0, "m1@mail.ru", "1234");
        user1 = store.add(user1).orElse(null);
        User user2 = new User(0, "m2@mail.ru", "1234");
        user2 = store.add(user2).orElse(null);
        List<User> list = store.findAll();
        User userInDb1 = list.get(count);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        User userInDb2 = list.get(++count);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void whenCreate2PostAndFindByIdAndUpdate() {
        UserDbStore store = new UserDbStore(dataSource);
        User user1 = new User(0, "m1@mail.ru", "1234");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        User user2 = new User(0, "m2@mail.ru", "1234");
        user2 = store.add(user2).orElse(null);
        User userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
        user1.setEmail("new1@mail.ru");
        user1.setPassword("new12345");
        store.update(user1);
        userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        user2.setEmail("new2@mail.ru");
        user2.setPassword("new12345");
        store.update(user2);
        userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void whenCreate2EqualUserAndFindById() {
        UserDbStore store = new UserDbStore(dataSource);
        User user1 = new User(0, "m1@mail.ru", "1234");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());

        User user2 = new User(0, "m1@mail.ru", "1234");
        user2 = store.add(user2).orElse(null);
        assertThat(user2).isNull();
    }
}