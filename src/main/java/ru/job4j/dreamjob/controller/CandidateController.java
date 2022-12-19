package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Candidate1;
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
                new Candidate1(
                        candidateService.getNextId(),
                        "Заполните ФИО",
                        1,
                        "Заполните описание",
                        LocalDateTime.now()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate1 candidate1) {
        candidateService.add(
                new Candidate(
                        candidate1.getId(),
                        candidate1.getName(),
                        cityService.findById(candidate1.getCity()),
                        candidate1.getDesc(),
                        candidate1.getCreated()
                )
        );
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        Candidate candidate = candidateService.findById(id);
        model.addAttribute(
                "candidate",
                new Candidate1(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getCity().getId(),
                        candidate.getDesc(),
                        LocalDateTime.now()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updatePost(@ModelAttribute Candidate1 candidate1) {
        candidateService.update(
                new Candidate(
                        candidate1.getId(),
                        candidate1.getName(),
                        cityService.findById(candidate1.getCity()),
                        candidate1.getDesc(),
                        candidate1.getCreated()
                )
        );
        return "redirect:/candidates";
    }
}
