package ru.job4j.dreamjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Main class. Minimal Sping boot application</p>
 * 1. run application.
 * 2. in web browser open page "http://localhost:8080/index".
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootApplication
public class Main {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
