# job4j_dreamjob
## 1. Техническое задание - проект "Работа мечты" [#504836]

---
В этом блоке мы поговорим о создании веб приложений на языка Java.

Мы будем использовать Spring boot, как основную библиотеку, но рассматривать детали этого фреймворка не будем. Это будет в блоке Spring.

Основная задача этого блока, понять архитектуру веб приложений.

Мы будем разрабатывать приложение "Работа мечты".

В системе будут две модели: вакансии и кандидаты. Кандидаты будут публиковать резюме. Кадровики будут публиковать вакансии о работе.

Кандидаты могут откликнуться на вакансию. Кадровик может пригласить на вакансию кандидата.

---
# Задание.

## 1. Создайте новый репозиторий job4j_dreamjob.

## 2. Добавьте maven сборку.

---

Файл .gitignore

# Compiled class file
*.class

# Log file
*.log

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*

.idea
*.iml

target
out

/testdb.mv.db
/testdb.trace.db

---

Файл pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

    <groupId>ru.job4j</groupId>
    <artifactId>job4j_dreamjob</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>19</maven.compiler.source>
        <maven.compiler.target>19</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.23.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.1.2</version>
                <dependencies>
                    <dependency>
                        <groupId>com.puppycrawl.tools</groupId>
                        <artifactId>checkstyle</artifactId>
                        <version>9.0</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>validate</id>
                        <phase>validate</phase>
                        <configuration>
                            <configLocation>checkstyle.xml</configLocation>
                            <encoding>UTF-8</encoding>
                            <consoleOutput>true</consoleOutput>
                            <failsOnError>true</failsOnError>
                            <includeTestSourceDirectory>true</includeTestSourceDirectory>
                        </configuration>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.8</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.2.0</version>
            </plugin>
        </plugins>
    </build>
</project>

---
Добавим в файл pom.xml зависимости для spring boot

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.7.4</version>
<relativePath/> <!-- lookup parent from repository -->
</parent>

    <groupId>ru.job4j</groupId>
    <artifactId>job4j_dreamjob</artifactId>
    <version>1.0</version>
    <name>Job4j Dream Job</name>

    <properties>
        <java.version>18</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

---

Элементы конфигурации мы рассмотрим в другой задаче. Сейчас наша задачу запустить минимальное приложение.

3. Создайте класс ru.job4j.dreamjob.Main

package ru.job4j.dreamjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
public static void main(String[] args) {
SpringApplication.run(Main.class, args);
}
}
4. Создайте класс ru.job4j.dreamjob.controller.IndexControl.

package ru.job4j.dreamjob.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexControl {

    @GetMapping("/index")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
5. Запустить метод main



6. Откройте браузер по ссылке http://localhost:8080/index



В методе main происходит запуск сервера Tomcat. Сервер Tomcat встроен в Spring boot.



Задание.

1. Подключите к проекту Spring boot.

2. Запустите приложение и убедитесь, что в браузере доступна ваша страница.

3. Загрузите изменения в репозиторий. Оставьте ссылку на коммит.

---
2. Формы. Списки. [#504854 #372722] - 4 исправления. MERGE path504849_372077. resolve conflicts.

---

# Task 504859.

## 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]

В этом уроке мы подключим базу данных. Использование базы данных в веб приложении аналогично тому, как мы делали до этого, то есть через jdbc.

Единственное отличие, что нам нужно использовать не одно соединение, а пул соединений с базой данных.

Почему нужен пул? Напомню, что все запросы от клиентов обрабатываются в отдельных нитях.
По умолчанию в Tomcat используется 200 нитей для обработки запросов. Если мы будем использовать одно соединение с базой данных, то остальные нити будут ждать завершения работы с базой данных, а для веб приложений такая ситуация неприемлема.

Запомните, что веб приложения - это многопоточные приложения, где блокировать работу пользователя нельзя.

Схема работы пулов нитей и пулов соединений.

То есть с базой данных мы работаем в многопоточном режиме. Про этот момент мы поговорим в следующем уроке.
Здесь же рассмотрим, как использовать базу в веб приложении.

Как работает пул соединений? Он работает по аналогии с пулом нитей, так же как мы делали в задании ThreadPool.

В курсе мы будем использовать commons-dbcp2. Сначала мы создаем объект класса BasicDataSource. Внутри этого объекта создаются коннекты к базе данных, которые находятся в многопоточной очереди. Каждый объект connection обернут в прокси объект. Это позволяет его вернуть в очередь после использования.

Опишем работу пула в псевдокоде.

ini
BasicDataSource cp = .... - пул соединений.
ConnectionProxy connection = cp.getConnnection() - получаем соединение с базой из многопоточной очереди.
То есть вызываем метод queue.poll(). В очереди больше нет Объект ConnectionProxу.
connection - работаем с соединением.
connection.close() - завершаем работу. Вызов метода close у прокси возвращает соединение в очередь, но не закрываем его.
Так мы можем переиспользовать соединения.
Операция создания соединения с базой данных может занимать несколько секунд. Это критично для многопользовательского приложения, поэтому коннекты мы не закрываем, а переиспользуем.

В этом уроке мы сделаем хранилище для работы с базой postgresql.

1. Создадим базу в postgresql сервере и с именем dreamjob.

2. Создадим файл в папку src/main/resource/db.properties.

---
jdbc.url=jdbc:postgresql://127.0.0.1:5432/dreamjob
jdbc.driver=org.postgresql.Driver
jdbc.username=postgres
jdbc.password=password
---


3. Создадим файл db/scripts/001_ddl_create_posts_table.sql. В нем опишем схему таблицу post.
---
CREATE TABLE post (
id SERIAL PRIMARY KEY,
name TEXT
);
---


4.Теперь нужно добавить зависимости: драйвер и пул (pom.xml).

---
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.2.9</version>
</dependency>

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.7.0</version>
</dependency>
---

5. Пул соединений нужен, чтобы не открывать новое соединение при каждом запросе.
Сделаем класс PostDbStore.
---
package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }


    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void update(Post post) {

    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

---


6. Откройте класс PostService и замените зависимость PostStore на PostDbStore.

---
private final PostDBStore store;

public PostService(PostDBStore store) {
this.store = store;
}
---

Класс PostDbStore имеет конструктор с параметром объекта BacisDataSource.
Объект BasicDataSource мы создадим в класса Main и попросим Spring загрузить его к себе в контекст.
Это нужно, чтобы проинициализировать класс PostDbStore.

---
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

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}

---

Разберем код класса Main.

1. Пул содержит активные соединение с базой. Когда вызывается метод Connection.close() соединение не закрывается, а возвращается обратно в пул.

Пула активируется в метод loadPool(). Это метод имеет аннотация @Bean, это аннотация указывает Spring загрузить объект BasicDataSource в контекст.

---
@Bean
public BasicDataSource loadPool() {
---

Разберем код класса PostDbStore. Создание вакансии. Здесь выполняется обычный sql запрос.

---
private Post add(Post post) {
try (Connection cn = pool.getConnection();
PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
) {
ps.setString(1, post.getName());
ps.execute();
try (ResultSet id = ps.getGeneratedKeys()) {
if (id.next()) {
post.setId(id.getInt(1));
}
}
} catch (Exception e) {
e.printStackTrace();
}
return post;
}

---
В модели Post есть поле City, поэтому в базу данных нужно добавить поле city_id для таблицы Post.

Это нужно сделать через скрипт для liquibase.

Хранение.

---
try (Connection cn = pool.getConnection();
PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name, city_id) VALUES (?, ?)",
PreparedStatement.RETURN_GENERATED_KEYS)
) {
ps.setString(1, post.getName());
ps.setInt(2, post.getCity().getId());
---

Загрузку городов сделать в сервисе PostService.
---
public List<Post> findAll() {
List<Post> posts = store.findAll();
posts.forEach(
post -> post.setCity(
cityService.findById(post.getCity().getId())
)
);
return posts;
}
---

Объект City в базе не храним. Он хранится в памяти в сервисе CityService.

## Задание.

### 1. Реализуйте недостающие методы класса PostDbStore. При этом в блоках catch вместо вывода в трейс e.printStackTrace() используйте логирование.

### 2. Замените зависимость в сервисе PostService класса PostStore на PostDbStore.

### 3. Добавьте методы loadDbProperties и loadPool в класс Main для загрузки пула соединений.

---

