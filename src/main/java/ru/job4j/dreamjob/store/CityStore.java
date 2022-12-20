package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CityStore {
    private final AtomicInteger counterId = new AtomicInteger(0);
    private Map<Integer, City> cities = new ConcurrentHashMap<Integer, City>();

    private CityStore() {
        cities.put(1, new City(1, "Москва"));
        cities.put(2, new City(2, "СПб"));
        cities.put(3, new City(3, "Екб"));
        counterId.addAndGet(3);
    }

    public Collection<City> findAll() {
        return cities.values();
    }

    public void add(City city) {
        city.setId(getNextId());
        cities.put(city.getId(), city);
    }

    private int getNextId() {
        return counterId.incrementAndGet();
    }

    public City findById(int id) {
        return cities.get(id);
    }

    public void update(City city) {
        cities.replace(city.getId(), city);
    }
}


