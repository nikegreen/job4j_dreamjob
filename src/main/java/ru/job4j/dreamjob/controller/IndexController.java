package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

/**
 * <p>IndexController class. Spring boot index controller</p>
 * return allways text "Greetings from Spring Boot!"
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
public class IndexController {

    /**
     * <p>index.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "index";
    }
}
