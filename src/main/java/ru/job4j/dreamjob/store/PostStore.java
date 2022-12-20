package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.PostService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger counterId = new AtomicInteger(0);

    private PostStore() {
        City city = new City(1, "Москва");
        posts.put(
                1,
                new Post(
                        1,
                        "Junior Java Job",
                        false,
                        city,
                        "desc1",
                        LocalDateTime.of(2014, 9, 19, 14, 5)
                )
        );
        posts.put(
                2,
                new Post(
                        2,
                        "Middle Java Job",
                        false,
                        city,
                        "desc2",
                        LocalDateTime.of(2014, 9, 19, 14, 6)
                )
        );
        posts.put(
                3,
                new Post(
                        3,
                        "Senior Java Job",
                        false,
                        city,
                        "desc3",
                        LocalDateTime.of(2014, 9, 19, 14, 7)
                )
        );
        counterId.addAndGet(3);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(getNextId());
        posts.put(post.getId(), post);
    }

    private int getNextId() {
        return counterId.incrementAndGet();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
