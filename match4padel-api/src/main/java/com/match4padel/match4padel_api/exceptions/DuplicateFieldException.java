package com.match4padel.match4padel_api.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DuplicateFieldException extends RuntimeException {
    private Map<String, String> fieldErrors = new HashMap<>();

    public DuplicateFieldException(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
