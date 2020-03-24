package com.devxsquad.harmony.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InputOutputException.class)
    public void inputOutputException(InputOutputException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void exception(Exception e) {
        log.error(e.getMessage());
    }
}
