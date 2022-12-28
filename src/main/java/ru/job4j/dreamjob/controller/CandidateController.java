package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.util.ModelSet;

import javax.servlet.http.HttpSession;
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
    public String candidates(Model model, HttpSession session) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("cities", cityService.getAllCities());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute(
                "candidate",
                new Candidate(
                        0,
                        "Заполните ФИО",
                        null,
                        "Заполните описание",
                        LocalDateTime.now(),
                        new byte[]{}
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("city.id") int cityId,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setCity(cityService.findById(cityId));
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model,
                                      @PathVariable("candidateId") int id,
                                      HttpSession session) {
        model = ModelSet.fromSession(model, session);
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidate", candidate);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("city.id", candidate.getCity().getId());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("city.id") int cityId,
                                  @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            candidate.setPhoto(
                    candidateService.findById(candidate.getId()).getPhoto()
            );
        } else {
            candidate.setPhoto(file.getBytes());
        }
        candidate.setCity(cityService.findById(cityId));
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> photoCandidate(
            @PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
