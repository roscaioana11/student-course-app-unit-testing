package ro.fasttrackit.studentcourseappunittesting.exception;

import lombok.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handle(ValidationException ex) {
        return new ApiError(ex.getMessage());
    }
}

@Value
class ApiError {
    String message;
}
