package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Candidate1;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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
                        LocalDateTime.now(),
                        new byte[]{}
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate1 candidate1,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate1.setPhoto(file.getBytes());
        candidateService.add(
                new Candidate(
                        candidate1.getId(),
                        candidate1.getName(),
                        cityService.findById(candidate1.getCity()),
                        candidate1.getDesc(),
                        candidate1.getCreated(),
                        candidate1.getPhoto()
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
                        candidate.getCreated(),
                        candidate.getPhoto()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updatePost(
            @ModelAttribute Candidate1 candidate1,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            candidate1.setPhoto(
                    candidateService.findById(candidate1.getId()).getPhoto()
            );
        } else {
            candidate1.setPhoto(file.getBytes());
        }
        candidateService.update(
                new Candidate(
                        candidate1.getId(),
                        candidate1.getName(),
                        cityService.findById(candidate1.getCity()),
                        candidate1.getDesc(),
                        candidate1.getCreated(),
                        candidate1.getPhoto()
                )
        );
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
