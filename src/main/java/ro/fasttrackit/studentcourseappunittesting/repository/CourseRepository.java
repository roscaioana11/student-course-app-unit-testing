package ro.fasttrackit.studentcourseappunittesting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseEntity;

@Repository
public interface CourseRepository extends MongoRepository<CourseEntity, String> {
    //List<Course> findByCourseId(String courseId);
}
