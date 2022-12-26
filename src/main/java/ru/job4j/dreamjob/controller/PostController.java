package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService,
                          CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
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
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @RequestParam("city.id") int id,
                             HttpServletRequest req) {
        post.setCity(cityService.findById(id));
        this.postService.add(post);
        HttpSession session = req.getSession();
        Optional<User> userDb = Optional.ofNullable((User) session.getAttribute("user"));
        if (userDb.isEmpty()) {
            userDb = Optional.of(new User(0, "", "", "Гость"));
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model,
                                 @PathVariable("postId") int id,
                                 HttpSession session) {
        Post post = this.postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("city.id", post.getCity().getId());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam("city.id") int id,
                             HttpServletRequest req) {
        post.setCity(cityService.findById(id));
        this.postService.update(post);
        HttpSession session = req.getSession();
        Optional<User> userDb = Optional.ofNullable((User) session.getAttribute("user"));
        if (userDb.isEmpty()) {
            userDb = Optional.of(new User(0, "", "", "Гость"));
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/posts";
    }
}