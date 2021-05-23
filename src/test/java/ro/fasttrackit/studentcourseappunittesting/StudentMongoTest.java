package ro.fasttrackit.studentcourseappunittesting;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = StudentMongoTest.Initializer.class)
public class StudentMongoTest {
    @Container
    private static MongoDBContainer mongoDb = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    static {
        mongoDb.start();
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentRepository repository;

    @SneakyThrows
    @Test
    @DisplayName("GET /students")
    void getAllCountriesTest() {
        repository.saveAll(List.of(
                StudentEntity.builder()
                        .name("Ioana")
                        .age(25).build(),
                StudentEntity.builder()
                        .name("Luna")
                        .age(1).build()
        ));

        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content.[0].name", is("Ioana")));
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    String.format("spring.data.mongodb.uri: %s", mongoDb.getReplicaSetUrl())
            ).applyTo(configurableApplicationContext);
        }
    }
}
