package ru.job4j.dreamjob.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>IndexControl class.</p>
 *
 * @author nikez
 * @version $Id: $Id
 */
@RestController
public class IndexControl {

    /**
     * <p>index.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/index")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
