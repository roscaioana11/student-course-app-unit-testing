package ro.fasttrackit.studentcourseappunittesting.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseEntity;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseStudent;

import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class CourseDao {
    private final MongoTemplate mongo;

    public List<CourseEntity> getAll(String studentId) {

        Criteria courseStudentCriteria = new Criteria();

        ofNullable(studentId)
                .ifPresent(student -> courseStudentCriteria.and("studentId").is(student));

        Query courseStudentQuery = Query.query(courseStudentCriteria);
        List<CourseStudent> assignedCourses = mongo.find(courseStudentQuery, CourseStudent.class);

        Criteria courseCriteria = new Criteria();

        courseCriteria.and("id").in(assignedCourses.stream()
                                .map(assignedCourse -> assignedCourse.getCourseId())
                                .collect(toList()));

        Query courseQuery = Query.query(courseCriteria);

        return mongo.find(courseQuery, CourseEntity.class);
    }
}
