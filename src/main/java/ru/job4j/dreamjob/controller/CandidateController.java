package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;


import java.time.LocalDateTime;

@ThreadSafe
@Controller
public class CandidateController {
    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("cities", cityService.getAllCities());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute(
                "candidate",
                new Candidate(
                        0,
                        "Заполните ФИО",
                        null,
                        "Заполните описание",
                        LocalDateTime.now()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("city.id") int cityId) {
        candidate.setCity(cityService.findById(cityId));
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidate", candidate);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("city.id", candidate.getCity().getId());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("city.id") int cityId) {
        candidate.setCity(cityService.findById(cityId));
        candidateService.update(candidate);
        return "redirect:/candidates";
    }
}
