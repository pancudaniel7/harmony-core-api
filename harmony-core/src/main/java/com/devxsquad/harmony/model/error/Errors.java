package com.devxsquad.harmony.model.error;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Errors {
    private final List<Error> errors;

    public Errors(String singleErrorMessage) {
        this.errors = new ArrayList<>();
        errors.add(new Error(singleErrorMessage));
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private final String message;
        private String propertyPath;
        private String invalidValue;
    }
}

