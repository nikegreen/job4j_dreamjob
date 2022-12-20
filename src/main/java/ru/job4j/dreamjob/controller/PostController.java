package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import java.time.LocalDateTime;
import org.springframework.ui.Model;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute(
                "post",
                new Post(
                        0,
                        "Заполните название",
                        true,
                        cityService.findById(1),
                        "Заполните описание",
                        LocalDateTime.now()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @RequestParam("city.id") int id) {
        post.setCity(cityService.findById(id));
        this.postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        Post post = this.postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("city.id", post.getCity().getId());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam("city.id") int id) {
        post.setCity(cityService.findById(id));
        this.postService.update(post);
        return "redirect:/posts";
    }
}