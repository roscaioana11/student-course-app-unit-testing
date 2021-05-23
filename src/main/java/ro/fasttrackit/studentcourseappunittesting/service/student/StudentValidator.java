package ro.fasttrackit.studentcourseappunittesting.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.studentcourseappunittesting.exception.ValidationException;
import ro.fasttrackit.studentcourseappunittesting.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class StudentValidator {
    private final StudentRepository repository;

    private Optional<ValidationException> exists(String studentId) {
        return repository.existsById(studentId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Student with id " + studentId + " doesn't exist")));
    }

    public void validateExistsOrThrow(String studentId) {
        exists(studentId).ifPresent(ex -> {
            throw ex;
        });
    }
}
