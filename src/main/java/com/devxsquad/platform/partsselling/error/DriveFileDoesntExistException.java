package com.devxsquad.platform.partsselling.error;

public class DriveFileDoesntExistException extends RuntimeException {
    public DriveFileDoesntExistException(String message) {
        super(message);
    }
}
