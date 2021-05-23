package ro.fasttrackit.studentcourseappunittesting.exception;

import java.util.List;

public class ValidationException extends RuntimeException{
    public ValidationException(List<String> errors){
        super("Validation errors: " + String.join(",", errors));
    }
}
