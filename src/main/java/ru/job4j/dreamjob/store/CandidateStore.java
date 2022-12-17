package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(
                1,
                "Bob Sinkler",
                "middle",
                LocalDateTime.of(2014, 9, 19, 10, 5)));
        candidates.put(2, new Candidate(
                2,
                "Bill Gates",
                "senior",
                LocalDateTime.of(2004, 6, 9, 14, 34)));
        candidates.put(3, new Candidate(
                3,
                "Erik Krause",
                "junior",
                LocalDateTime.of(2022, 1, 29, 12, 50)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(nextCount());
        candidates.put(candidate.getId(), candidate);
    }

    public Object findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }

    public int nextCount() {
        return candidates.size() + 1;
    }
}
