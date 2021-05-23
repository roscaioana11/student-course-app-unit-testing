package ro.fasttrackit.studentcourseappunittesting.model;

import lombok.Value;

@Value
public class StudentFilter {
    String name;
    Integer minAge;
    Integer maxAge;
    String studentId;
}
