package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger counterId = new AtomicInteger(0);

    private CandidateStore() {
        City city = new City(1, "Москва");
        candidates.put(1, new Candidate(
                1,
                "Bob Sinkler",
                city,
                "middle",
                LocalDateTime.of(2014, 9, 19, 10, 5))
        );
        candidates.put(2, new Candidate(
                2,
                "Bill Gates",
                city,
                "senior",
                LocalDateTime.of(2004, 6, 9, 14, 34))
        );
        candidates.put(3, new Candidate(
                3,
                "Erik Krause",
                city,
                "junior",
                LocalDateTime.of(2022, 1, 29, 12, 50))
        );
        counterId.addAndGet(3);
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public int getNextId() {
        return counterId.incrementAndGet();
    }

    public void add(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
