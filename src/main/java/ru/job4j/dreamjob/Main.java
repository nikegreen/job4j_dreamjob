package ru.job4j.dreamjob;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * <p>Main class. Minimal Sping boot application</p>
 * 1. run application.
 * 2. in web browser open page "http://localhost:8080/index".
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootApplication
public class Main {
    @SpringBootApplication
    public class Main {

        private Properties loadDbProperties() {
            Properties cfg = new Properties();
            try (BufferedReader io = new BufferedReader(
                    new InputStreamReader(
                            Main.class.getClassLoader()
                                    .getResourceAsStream("db.properties")
                    )
            )) {
                cfg.load(io);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            try {
                Class.forName(cfg.getProperty("jdbc.driver"));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return cfg;
        }

        @Bean
        public BasicDataSource loadPool() {
            Properties cfg = loadDbProperties();
            BasicDataSource pool = new BasicDataSource();
            pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
            pool.setUrl(cfg.getProperty("jdbc.url"));
            pool.setUsername(cfg.getProperty("jdbc.username"));
            pool.setPassword(cfg.getProperty("jdbc.password"));
            pool.setMinIdle(5);
            pool.setMaxIdle(10);
            pool.setMaxOpenPreparedStatements(100);
            return pool;
        }

        /**
         * <p>main.</p>
         *
         * @param args an array of {@link java.lang.String} objects.
         */
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
            System.out.println("Go to http://localhost:8080/index");
        }
    }

    /*
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    */
}
