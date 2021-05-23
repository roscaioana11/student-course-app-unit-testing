package ro.fasttrackit.studentcourseappunittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.fasttrackit.studentcourseappunittesting.StudentCourseAppUnitTestingApplication;
import ro.fasttrackit.studentcourseappunittesting.model.StudentFilter;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.model.mapper.StudentMapper;
import ro.fasttrackit.studentcourseappunittesting.service.student.StudentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentCourseAppUnitTestingApplication.class, StudentControllerTest.TestBeans.class})
class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper mapper;

    @Test
    @DisplayName("GET /students")
    void getAllCountriesTest() throws Exception {

        StudentFilter filter = new StudentFilter(null, null, null, null);

        doReturn(List.of(
                StudentEntity.builder()
                        .name("Ioana")
                        .age(25).build(),
                StudentEntity.builder()
                        .name("Luna")
                        .age(1).build()
        )).when(studentService).getFilteredStudents(filter);

        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Ioana")));

        verify(studentService, times(1)).getFilteredStudents(filter);
    }

    @Test
    @DisplayName("POST /students")
    void postStudent() throws Exception {
        StudentEntity student = StudentEntity.builder()
                .name("Ioana")
                .age(25).build();
        doReturn(student).when(studentService).addStudent(student);

        mvc.perform(MockMvcRequestBuilders.post("/students")
                .content(asJsonString(student))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Ioana")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(25)));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Configuration
    static class TestBeans {

        @Bean
        StudentService studentService() {
            return mock(StudentService.class);
        }

        @Bean
        StudentMapper studentMapper() {
            return mock(StudentMapper.class);
        }
    }
}