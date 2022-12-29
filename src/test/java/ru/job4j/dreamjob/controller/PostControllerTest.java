package ru.job4j.dreamjob.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(
                        1,
                        "New post",
                        false,
                        new City(1, "Москва"),
                        "description",
                        LocalDateTime.of(2022, 12, 18, 10, 35, 55)),
                new Post(2, "New post",
                        false,
                        new City(1, "Москва"),
                        "description",
                        LocalDateTime.of(2022, 12, 18, 10, 35, 55))
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("cities", cityService.getAllCities());
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(
                1,
                "New post",
                false,
                new City(1, "Москва"),
                "description",
                LocalDateTime.of(2022, 12, 18, 10, 35, 55));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input, input.getId());
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        City city1 = new City(1, "Москва");
        Post input = new Post(
                1,
                "New post",
                false,
                city1,
                "description",
                LocalDateTime.of(2022, 12, 18, 10, 35, 55));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(1)).thenReturn(city1);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input, input.getId());
        verify(postService).update(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenAddPost() {
        City city1 = new City(1, "Москва");
        Post input = new Post(
                0,
                "Заполните название",
                true,
                city1,
                "Заполните описание",
                LocalDateTime.now()
        );
        User user = new User(1, "user1@mail.ru", "password1", "user1");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(1)).thenReturn(city1);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.addPost(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", input);
        verify(model).addAttribute("cities", cityService.getAllCities());
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        City city1 = new City(1, "Москва");
        Post input = new Post(
                1,
                "super puper job",
                true,
                city1,
                "for junior",
                LocalDateTime.of(2022, 11, 28, 14, 45, 6)
        );
        User user = new User(1, "user1@mail.ru", "password1", "user1");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        PostService postService = mock(PostService.class);
        when(postService.findById(1)).thenReturn(input);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(1)).thenReturn(city1);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, input.getId(), session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", input);
        verify(model).addAttribute("cities", cityService.getAllCities());
        verify(model).addAttribute("city.id", input.getCity().getId());
        assertThat(page).isEqualTo("updatePost");
    }
}