package ro.fasttrackit.studentcourseappunittesting.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.studentcourseappunittesting.exception.ResourceNotFoundException;
import ro.fasttrackit.studentcourseappunittesting.model.StudentFilter;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentDao;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentValidator validator;
    private final StudentDao dao;

    public List<StudentEntity> getFilteredStudents(StudentFilter filter){
        return dao.getAll(filter);
    }

    public StudentEntity getStudentById(String studentId){
        validator.validateExistsOrThrow(studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find student with id " + studentId));
    }

    public StudentEntity addStudent(StudentEntity studentEntity){
        return studentRepository.save(studentEntity);
    }
}
