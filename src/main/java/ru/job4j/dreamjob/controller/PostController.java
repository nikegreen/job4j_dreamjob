package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.Post1;
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
                new Post1(
                        0,
                        "Заполните название",
                        true,
                        1,
                        "Заполните описание",
                        LocalDateTime.now()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post1 post1) {
        System.out.println("========> create post (id) ->" + post1.toString());
        City city = cityService.findById(post1.getCity());
        Post post = new Post(
                post1.getId(),
                post1.getName(),
                post1.getVisible(),
                city,
                post1.getDescription(),
                post1.getCreated()
        );
        System.out.println("========> create post      ->" + post.toString());
        this.postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        Post post = this.postService.findById(id);
        model.addAttribute(
                "post",
                new Post1(
                        id,
                        post.getName(),
                        post.getVisible(),
                        post.getCity().getId(),
                        post.getDescription(),
                        post.getCreated()
                )
        );
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post1 post1) {
        this.postService.update(
                new Post(
                        post1.getId(),
                        post1.getName(),
                        post1.getVisible(),
                        cityService.findById(post1.getCity()),
                        post1.getDescription(),
                        post1.getCreated()
                )
        );
        return "redirect:/posts";
    }
}