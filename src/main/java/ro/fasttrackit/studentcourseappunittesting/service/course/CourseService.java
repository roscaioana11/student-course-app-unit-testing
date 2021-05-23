package ro.fasttrackit.studentcourseappunittesting.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.studentcourseappunittesting.exception.ResourceNotFoundException;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseEntity;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseStudent;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.repository.CourseDao;
import ro.fasttrackit.studentcourseappunittesting.repository.CourseRepository;
import ro.fasttrackit.studentcourseappunittesting.repository.CourseStudentRepository;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentDao;
import ro.fasttrackit.studentcourseappunittesting.service.student.StudentValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final CourseValidator courseValidator;
    private final StudentValidator studentValidator;
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public List<CourseEntity> getAllCourses(String studentId){
        if(studentId != null){
            return courseDao.getAll(studentId);
        }else {
            return courseRepository.findAll();
        }
    }

    public CourseEntity getCourseById(String courseId){
        courseValidator.validateExistsOrThrow(courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + courseId));
    }

    public CourseEntity addCourse(CourseEntity courseEntity){
        return courseRepository.save(courseEntity);
    }

    public List<StudentEntity> getAllStudentsForCourse(String courseId){
        return studentDao.getAll(courseId);
    }

    public CourseStudent addStudentToCourse(String courseId, String studentId){
        courseValidator.validateExistsOrThrow(courseId);
        studentValidator.validateExistsOrThrow(studentId);

        CourseStudent newCourseStudent = new CourseStudent(courseId, studentId, 0);
        return courseStudentRepository.save(newCourseStudent);
    }
}
