package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityStore;

import java.util.*;

@Service
public class CityService {
    private final CityStore store;

    public CityService(CityStore store) {
        this.store = store;
    }

    public List<City> getAllCities() {
        return List.copyOf(store.findAll());
    }

    public City findById(int id) {
        return store.findById(id);
    }

    public int getNextId() {
        return store.getNextId();
    }

    public void add(City city) {
        store.add(city);
    }
}
