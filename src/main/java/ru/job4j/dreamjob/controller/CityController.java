package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CityService;

@ThreadSafe
@Controller
public class CityController {
    private final CityService store;

    public CityController(CityService store) {
        this.store = store;
    }

    @GetMapping("/cities")
    public String cities(Model model) {
        model.addAttribute("cities", store.getAllCities());
        return "cities";
    }

    @GetMapping("/formAddCity")
    public String addCity(Model model) {
        model.addAttribute(
                "city",
                new City(
                        store.getNextId(),
                        "Заполните название города"
                )
        );
        return "addCity";
    }

    @PostMapping("/createCity")
    public String createCity(@ModelAttribute City city) {
        store.add(city);
        return "redirect:/cities";
    }
}
