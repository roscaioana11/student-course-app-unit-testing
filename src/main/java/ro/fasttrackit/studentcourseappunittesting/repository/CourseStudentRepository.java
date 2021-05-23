package ro.fasttrackit.studentcourseappunittesting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseStudent;

import java.util.List;

@Repository
public interface CourseStudentRepository extends MongoRepository<CourseStudent, String> {
    List<CourseStudent> findByStudentId(String studentId);
    List<CourseStudent> findByCourseId(String courseId);
}
