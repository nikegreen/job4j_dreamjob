package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.List;

@Service
public class PostService {
    private final PostStore store;

    public PostService(PostStore store) {
        this.store = store;
    }

    public List<Post> findAll() {
        return List.copyOf(store.findAll());
    }

    public void add(Post post) {
        store.add(post);
    }

    public int getNextId() {
        return store.getNextId();
    }

    public Object findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
