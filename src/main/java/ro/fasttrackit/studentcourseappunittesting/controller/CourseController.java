package ro.fasttrackit.studentcourseappunittesting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseEntity;
import ro.fasttrackit.studentcourseappunittesting.model.entity.CourseStudent;
import ro.fasttrackit.studentcourseappunittesting.model.entity.StudentEntity;
import ro.fasttrackit.studentcourseappunittesting.service.course.CourseService;

import java.util.List;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    List<CourseEntity> getCourses(@RequestParam(required = false) String studentId){
        return courseService.getAllCourses(studentId);
    }

    @GetMapping("{courseId}")
    CourseEntity getCourseById(@PathVariable String courseId){
        return courseService.getCourseById(courseId);
    }

    @PostMapping
    CourseEntity addCourse(@RequestBody CourseEntity courseEntity){
        return courseService.addCourse(courseEntity);
    }

    @GetMapping("/{courseId}/students")
    List<StudentEntity> getAllStudentsForCourse(@PathVariable String courseId){
        return courseService.getAllStudentsForCourse(courseId);
    }

    @PostMapping("/{courseId}/students")
    CourseStudent addStudentToCourse(@PathVariable String courseId,
                                     @RequestBody String studentId){
        return courseService.addStudentToCourse(courseId,studentId);
    }
}
