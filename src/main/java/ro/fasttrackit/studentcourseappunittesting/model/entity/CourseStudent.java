package ro.fasttrackit.studentcourseappunittesting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseStudent {
    private String courseId;
    private String studentId;
    private float grade;
}
