package com.devxsquad.harmony.component.error;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import com.devxsquad.harmony.model.error.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InputOutputException.class)
    public ResponseEntity<Errors> inputOutputException(InputOutputException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new Errors(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Errors> constraintValidationException(ConstraintViolationException e) {
        log.error(e.getMessage());

        List<Errors.Error> errorList = e.getConstraintViolations().stream().map(
            constraintViolation ->
                new Errors.Error(constraintViolation.getMessage(),
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getInvalidValue().toString()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new Errors(errorList), BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Errors> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new Errors(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> exception(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new Errors(e.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
