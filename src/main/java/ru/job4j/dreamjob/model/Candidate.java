package ru.job4j.dreamjob.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private City city;
    private String desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    public Candidate() {

    }

    public Candidate(
            int id,
            String name,
            City city,
            String desc,
            LocalDateTime created
    ) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.desc = desc;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
