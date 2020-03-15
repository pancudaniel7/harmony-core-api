package com.harmony.error;

public class DriveFileDoesntExistException extends RuntimeException {
    public DriveFileDoesntExistException(String message) {
        super(message);
    }
}
