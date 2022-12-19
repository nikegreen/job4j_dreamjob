package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateStore store;

    private CandidateService(CandidateStore store) {
        this.store = store;
    }

    public List<Candidate> findAll() {
        return List.copyOf(store.findAll());
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public int getNextId() {
        return store.getNextId();
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
