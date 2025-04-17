package com.match4padel.match4padel_api.exceptions;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String field, String value) {
        super("El " + field + " " + value + " ya está registrado.");
    }
}
