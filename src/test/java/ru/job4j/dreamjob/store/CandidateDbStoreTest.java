package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.time.LocalDateTime;
import java.util.List;

class CandidateDbStoreTest {
    @Test
    public void whenCreateCandidateAndFindById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        LocalDateTime ldt = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city = new City(1, "Москва");
        Candidate candidate = new Candidate(0, "Java Job", city, "Desc", ldt, null);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
        assertThat(candidateInDb.getCity()).isEqualTo(candidate.getCity());
        assertThat(candidateInDb.getDesc()).isEqualTo(candidate.getDesc());
        assertThat(candidateInDb.getCreated()).isEqualTo(candidate.getCreated());
        assertThat(candidateInDb.getPhoto()).isEqualTo(candidate.getPhoto());
    }

    @Test
    public void whenCreate2CandidateAndFindById() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Candidate candidate1 = new Candidate(0, "Java Job", city1, "Desc", ldt1, null);
        store.add(candidate1);
        LocalDateTime ldt2 = LocalDateTime.of(2021, 1, 29, 10, 8, 1);
        City city2 = new City(1, "Москва");
        Candidate candidate2 = new Candidate(0, "Java Junior Job", city2, "Desc1", ldt2, null);
        store.add(candidate2);
        Candidate candidateInDb1 = store.findById(candidate1.getId());
        assertThat(candidateInDb1.getName()).isEqualTo(candidate1.getName());
        assertThat(candidateInDb1.getCity()).isEqualTo(candidate1.getCity());
        assertThat(candidateInDb1.getDesc()).isEqualTo(candidate1.getDesc());
        assertThat(candidateInDb1.getCreated()).isEqualTo(candidate1.getCreated());
        assertThat(candidateInDb1.getPhoto()).isEqualTo(candidate1.getPhoto());
        Candidate candidateInDb2 = store.findById(candidate2.getId());
        assertThat(candidateInDb2.getName()).isEqualTo(candidate2.getName());
        assertThat(candidateInDb2.getCity()).isEqualTo(candidate2.getCity());
        assertThat(candidateInDb2.getDesc()).isEqualTo(candidate2.getDesc());
        assertThat(candidateInDb2.getCreated()).isEqualTo(candidate2.getCreated());
        assertThat(candidateInDb2.getPhoto()).isEqualTo(candidate2.getPhoto());
    }

    @Test
    public void whenCreate2CandidateAndFindAll() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        int count = store.findAll().size();
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Candidate candidate1 = new Candidate(0, "Java Job", city1, "Desc", ldt1, null);
        store.add(candidate1);
        LocalDateTime ldt2 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city2 = new City(2, "Ленинград");
        Candidate candidate2 = new Candidate(0, "Java Middle Job", city2, "Desc2", ldt2, null);
        store.add(candidate2);
        List<Candidate> list = store.findAll();
        Candidate candidateInDb1 = list.get(count);
        assertThat(candidateInDb1.getName()).isEqualTo(candidate1.getName());
        assertThat(candidateInDb1.getCity()).isEqualTo(candidate1.getCity());
        assertThat(candidateInDb1.getDesc()).isEqualTo(candidate1.getDesc());
        assertThat(candidateInDb1.getCreated()).isEqualTo(candidate1.getCreated());
        assertThat(candidateInDb1.getPhoto()).isEqualTo(candidate1.getPhoto());
        Candidate candidateInDb2 = list.get(++count);
        assertThat(candidateInDb2.getName()).isEqualTo(candidate2.getName());
        assertThat(candidateInDb2.getCity()).isEqualTo(candidate2.getCity());
        assertThat(candidateInDb2.getDesc()).isEqualTo(candidate2.getDesc());
        assertThat(candidateInDb2.getCreated()).isEqualTo(candidate2.getCreated());
        assertThat(candidateInDb2.getPhoto()).isEqualTo(candidate2.getPhoto());
    }

    @Test
    public void whenCreate2PostAndFindByIdAndUpdate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        LocalDateTime ldt1 = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1 = new City(1, "Москва");
        Candidate candidate1 = new Candidate(0, "Java Job", city1, "Desc", ldt1, null);
        store.add(candidate1);
        LocalDateTime ldt2 = LocalDateTime.of(2021, 1, 29, 10, 8, 1);
        City city2 = new City(1, "Москва");
        Candidate candidate2 = new Candidate(0, "Java Job", city2, "Desc", ldt2, null);
        store.add(candidate2);
        Candidate candidateInDb1 = store.findById(candidate1.getId());
        assertThat(candidateInDb1.getName()).isEqualTo(candidate1.getName());
        assertThat(candidateInDb1.getCity()).isEqualTo(candidate1.getCity());
        assertThat(candidateInDb1.getDesc()).isEqualTo(candidate1.getDesc());
        assertThat(candidateInDb1.getCreated()).isEqualTo(candidate1.getCreated());
        assertThat(candidateInDb1.getPhoto()).isEqualTo(candidate1.getPhoto());
        Candidate candidateInDb2 = store.findById(candidate2.getId());
        assertThat(candidateInDb2.getName()).isEqualTo(candidate2.getName());
        assertThat(candidateInDb2.getCity()).isEqualTo(candidate2.getCity());
        assertThat(candidateInDb2.getDesc()).isEqualTo(candidate2.getDesc());
        assertThat(candidateInDb2.getCreated()).isEqualTo(candidate2.getCreated());
        assertThat(candidateInDb2.getPhoto()).isEqualTo(candidate2.getPhoto());
        LocalDateTime ldt1u = LocalDateTime.of(2022, 12, 26, 19, 58, 16);
        City city1u = new City(3, "Екатеринбург");
        candidate1.setName("Super Java Job");
        candidate1.setCity(city1u);
        candidate1.setDesc("Desc3");
        candidate1.setCreated(ldt1u);
        candidate1.setPhoto(null);
        store.update(candidate1);
        candidateInDb1 = store.findById(candidate1.getId());
        assertThat(candidateInDb1.getName()).isEqualTo(candidate1.getName());
        assertThat(candidateInDb1.getCity()).isEqualTo(candidate1.getCity());
        assertThat(candidateInDb1.getDesc()).isEqualTo(candidate1.getDesc());
        assertThat(candidateInDb1.getCreated()).isEqualTo(candidate1.getCreated());
        assertThat(candidateInDb1.getPhoto()).isEqualTo(candidate1.getPhoto());
        LocalDateTime ldt2u = LocalDateTime.of(2022, 11, 6, 11, 55, 1);
        City city2u = new City(4, "Казань");
        candidate2.setName("Super Puper Java Job");
        candidate2.setCity(city2u);
        candidate2.setDesc("Desc4");
        candidate2.setCreated(ldt2u);
        candidate2.setPhoto(null);
        store.update(candidate2);
        candidateInDb2 = store.findById(candidate2.getId());
        assertThat(candidateInDb2.getName()).isEqualTo(candidate2.getName());
        assertThat(candidateInDb2.getCity()).isEqualTo(candidate2.getCity());
        assertThat(candidateInDb2.getDesc()).isEqualTo(candidate2.getDesc());
        assertThat(candidateInDb2.getCreated()).isEqualTo(candidate2.getCreated());
        assertThat(candidateInDb2.getPhoto()).isEqualTo(candidate2.getPhoto());
    }
}