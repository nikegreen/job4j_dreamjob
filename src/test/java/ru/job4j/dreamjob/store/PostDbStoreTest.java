package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.time.LocalDateTime;
import java.util.List;

public class PostDbStoreTest {
    @Test
    public void whenCreatePostAndFindById() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        LocalDateTime ldt = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city = new City(1, "Москва");
        Post post = new Post(0, "Java Job", false, city, "Desc", ldt);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
        assertThat(postInDb.getVisible()).isEqualTo(post.getVisible());
        assertThat(postInDb.getCity()).isEqualTo(post.getCity());
        assertThat(postInDb.getDescription()).isEqualTo(post.getDescription());
        assertThat(postInDb.getCreated()).isEqualTo(post.getCreated());
    }

    @Test
    public void whenCreate2PostAndFindById() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Post post1 = new Post(0, "Java Job", false, city1, "Desc", ldt1);
        store.add(post1);
        LocalDateTime ldt2 = LocalDateTime.of(2021, 1, 29, 10, 8, 1);
        City city2 = new City(1, "Москва");
        Post post2 = new Post(0, "Java Job", false, city2, "Desc", ldt2);
        store.add(post2);
        Post postInDb1 = store.findById(post1.getId());
        assertThat(postInDb1.getName()).isEqualTo(post1.getName());
        assertThat(postInDb1.getVisible()).isEqualTo(post1.getVisible());
        assertThat(postInDb1.getCity()).isEqualTo(post1.getCity());
        assertThat(postInDb1.getDescription()).isEqualTo(post1.getDescription());
        assertThat(postInDb1.getCreated()).isEqualTo(post1.getCreated());
        Post postInDb2 = store.findById(post2.getId());
        assertThat(postInDb2.getName()).isEqualTo(post2.getName());
        assertThat(postInDb2.getVisible()).isEqualTo(post2.getVisible());
        assertThat(postInDb2.getCity()).isEqualTo(post2.getCity());
        assertThat(postInDb2.getDescription()).isEqualTo(post2.getDescription());
        assertThat(postInDb2.getCreated()).isEqualTo(post2.getCreated());
    }

    @Test
    public void whenCreate2PostAndFindAll() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        int count = store.findAll().size();
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Post post1 = new Post(0, "Java Job", false, city1, "Desc", ldt1);
        store.add(post1);
        LocalDateTime ldt2 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city2 = new City(2, "Ленинград");
        Post post2 = new Post(0, "Java Middle Job", false, city2, "Desc2", ldt2);
        store.add(post2);
        List<Post> list = store.findAll();
        Post postInDb1 = list.get(count);
        assertThat(postInDb1.getName()).isEqualTo(post1.getName());
        assertThat(postInDb1.getVisible()).isEqualTo(post1.getVisible());
        assertThat(postInDb1.getCity()).isEqualTo(post1.getCity());
        assertThat(postInDb1.getDescription()).isEqualTo(post1.getDescription());
        assertThat(postInDb1.getCreated()).isEqualTo(post1.getCreated());
        Post postInDb2 = list.get(++count);
        assertThat(postInDb2.getName()).isEqualTo(post2.getName());
        assertThat(postInDb2.getVisible()).isEqualTo(post2.getVisible());
        assertThat(postInDb2.getCity()).isEqualTo(post2.getCity());
        assertThat(postInDb2.getDescription()).isEqualTo(post2.getDescription());
        assertThat(postInDb2.getCreated()).isEqualTo(post2.getCreated());
    }

    @Test
    public void whenCreate2PostAndFindByIdAndUpdate() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Post post1 = new Post(0, "Java Job", false, city1, "Desc", ldt1);
        store.add(post1);
        LocalDateTime ldt2 = LocalDateTime.of(2021, 1, 29, 10, 8, 1);
        City city2 = new City(1, "Москва");
        Post post2 = new Post(0, "Java Job", false, city2, "Desc", ldt2);
        store.add(post2);
        Post postInDb1 = store.findById(post1.getId());
        assertThat(postInDb1.getName()).isEqualTo(post1.getName());
        assertThat(postInDb1.getVisible()).isEqualTo(post1.getVisible());
        assertThat(postInDb1.getCity()).isEqualTo(post1.getCity());
        assertThat(postInDb1.getDescription()).isEqualTo(post1.getDescription());
        assertThat(postInDb1.getCreated()).isEqualTo(post1.getCreated());
        Post postInDb2 = store.findById(post2.getId());
        assertThat(postInDb2.getName()).isEqualTo(post2.getName());
        assertThat(postInDb2.getVisible()).isEqualTo(post2.getVisible());
        assertThat(postInDb2.getCity()).isEqualTo(post2.getCity());
        assertThat(postInDb2.getDescription()).isEqualTo(post2.getDescription());
        assertThat(postInDb2.getCreated()).isEqualTo(post2.getCreated());
        LocalDateTime ldt1u = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1u = new City(3, "Екатеринбург");
        post1.setName("Super Java Job");
        post1.setVisible(true);
        post1.setCity(city1u);
        post1.setDescription("Desc3");
        post1.setCreated(ldt1u);
        store.update(post1);
        postInDb1 = store.findById(post1.getId());
        assertThat(postInDb1.getName()).isEqualTo(post1.getName());
        assertThat(postInDb1.getVisible()).isEqualTo(post1.getVisible());
        assertThat(postInDb1.getCity()).isEqualTo(post1.getCity());
        assertThat(postInDb1.getDescription()).isEqualTo(post1.getDescription());
        assertThat(postInDb1.getCreated()).isEqualTo(post1.getCreated());
        LocalDateTime ldt2u = LocalDateTime.of(2022, 11, 6, 11, 55, 1);
        City city2u = new City(4, "Казань");
        post2.setName("Super Puper Java Job");
        post2.setVisible(true);
        post2.setCity(city2u);
        post2.setDescription("Desc4");
        post2.setCreated(ldt2u);
        store.update(post2);
        postInDb2 = store.findById(post2.getId());
        assertThat(postInDb2.getName()).isEqualTo(post2.getName());
        assertThat(postInDb2.getVisible()).isEqualTo(post2.getVisible());
        assertThat(postInDb2.getCity()).isEqualTo(post2.getCity());
        assertThat(postInDb2.getDescription()).isEqualTo(post2.getDescription());
        assertThat(postInDb2.getCreated()).isEqualTo(post2.getCreated());
    }
}