package ro.fasttrackit.studentcourseappunittesting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;

@Repository
public interface StudentRepository extends MongoRepository<StudentEntity, String> {
}
