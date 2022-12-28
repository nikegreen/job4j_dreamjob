package ru.job4j.dreamjob.util;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public final class ModelSet {
    private  ModelSet() {
        throw new AssertionError();
    }

    public static Model fromSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return model;
    }

}
