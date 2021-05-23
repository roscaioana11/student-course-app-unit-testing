package ro.fasttrackit.studentcourseappunittesting;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoursesTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private StudentRepository repository;

    @SneakyThrows
    @Test
    @DisplayName("GET /students")
    void getStudentsTest() {
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
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name", CoreMatchers.is("Ioana")));
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }
}
