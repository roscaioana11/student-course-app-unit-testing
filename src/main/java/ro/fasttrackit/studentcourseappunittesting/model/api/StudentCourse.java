package ro.fasttrackit.studentcourseappunittesting.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourse {
    private String name;
    private Integer age;
    private String discipline;
    private float grade;
}
