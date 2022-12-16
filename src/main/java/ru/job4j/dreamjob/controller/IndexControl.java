package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>IndexControl class. Spring boot index controller</p>
 * return allways text "Greetings from Spring Boot!"
 * @author nikez
 * @version $Id: $Id
 */
@Controller
public class IndexControl {

    /**
     * <p>index.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
