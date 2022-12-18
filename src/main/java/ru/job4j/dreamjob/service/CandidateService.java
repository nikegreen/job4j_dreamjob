package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.List;

public class CandidateService {
    private static final CandidateService INST = new CandidateService();
    private final CandidateStore store;

    private CandidateService() {
        store = CandidateStore.instOf();
    }

    public static CandidateService instOf() {
        return INST;
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

    public Object findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
