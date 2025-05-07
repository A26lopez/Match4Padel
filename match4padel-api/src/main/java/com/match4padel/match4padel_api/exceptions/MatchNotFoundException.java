package com.match4padel.match4padel_api.exceptions;



public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(String field, String value) {
        super("No se ha encontrado ningun partido con " + field + " " + value + ".");
    }
}
