package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CityStore;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.List;

@ThreadSafe
@Service
public class PostService {
    private final PostDBStore store;

    public PostService(PostDBStore store) {
        this.store = store;
    }

    public List<Post> findAll() {
        return List.copyOf(store.findAll());
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
