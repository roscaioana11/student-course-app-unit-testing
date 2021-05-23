package ro.fasttrackit.studentcourseappunittesting.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.studentcourseappunittesting.model.StudentFilter;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseEntity;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseStudent;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;

import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class StudentDao {
    private final MongoTemplate mongo;

    public List<StudentEntity> getAll(StudentFilter filters) {

        Criteria criteria = new Criteria();

        ofNullable(filters.getName())
                .ifPresent(name -> criteria.and("name").is(name));
        ofNullable(filters.getMinAge())
                .ifPresent(minAge -> criteria.and("age").gte(minAge));
        ofNullable(filters.getMaxAge())
                .ifPresent(maxAge -> criteria.and("age").lte(maxAge));
        ofNullable(filters.getStudentId())
                .ifPresent(student -> criteria.and("studentId").is(student));

        Query query = Query.query(criteria);
        return mongo.find(query, StudentEntity.class);
    }

    public List<StudentEntity> getAll(String courseId) {

        Criteria courseStudentCriteria = new Criteria();

        ofNullable(courseId)
                .ifPresent(course -> courseStudentCriteria.and("courseId").is(course));

        Query courseStudentQuery = Query.query(courseStudentCriteria);
        List<CourseStudent> assignedCourses = mongo.find(courseStudentQuery, CourseStudent.class);

        Criteria studentCriteria = new Criteria();

        studentCriteria.and("id").in(assignedCourses.stream()
                .map(assignedCourse -> assignedCourse.getStudentId())
                .collect(toList()));

        Query studentQuery = Query.query(studentCriteria);

        return mongo.find(studentQuery, StudentEntity.class);
    }
}
