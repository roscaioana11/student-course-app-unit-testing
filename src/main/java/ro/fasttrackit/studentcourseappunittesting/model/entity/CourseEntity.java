package ro.fasttrackit.studentcourseappunittesting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {
    @Id
    private String id;

    private String discipline;

    private String description;
}
