package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger counterId = new AtomicInteger(0);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "desc1", LocalDateTime.of(2014, 9, 19, 14, 5)));
        posts.put(2, new Post(2, "Middle Java Job", "desc2", LocalDateTime.of(2014, 9, 19, 14, 6)));
        posts.put(3, new Post(3, "Senior Java Job", "desc3", LocalDateTime.of(2014, 9, 19, 14, 7)));
        counterId.addAndGet(3);
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        posts.put(post.getId(), post);
    }

    public int getNextId() {
        return counterId.incrementAndGet();
    }

    public Object findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
