package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;

import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDbStore store;
    private final CityService cityService;

    private CandidateService(CandidateDbStore store,
                             CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Candidate> findAll() {
        List<Candidate> list = store.findAll();
        list.forEach(candidate -> candidate.setCity(
                cityService.findById(candidate.getCity().getId()))
        );
        return list;
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
