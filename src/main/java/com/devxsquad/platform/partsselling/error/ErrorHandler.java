package com.devxsquad.platform.partsselling.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(GeneralSecurityAppException.class)
    public void generalSecurityException(GeneralSecurityAppException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(InputOutputException.class)
    public void inputOutputException(InputOutputException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(DriveFileDoesntExistException.class)
    public void driveFileDoesntExistException(DriveFileDoesntExistException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(DatabaseFileDoesntExistException.class)
    public void databaseFileDoesntExistException(DatabaseFileDoesntExistException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(WorkBookException.class)
    public void workBookException(WorkBookException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(KafkaException.class)
    public void kafkaException(KafkaException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void exception(Exception e) {
        log.error(e.getMessage());
    }
}
