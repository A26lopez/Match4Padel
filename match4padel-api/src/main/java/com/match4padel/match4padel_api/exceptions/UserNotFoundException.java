package com.match4padel.match4padel_api.exceptions;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String field, String value) {
        super("No hay ningún usuario con " + field + " " + value + ".");
    }
}
