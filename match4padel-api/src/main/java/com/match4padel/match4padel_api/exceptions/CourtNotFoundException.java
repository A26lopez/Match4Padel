package com.match4padel.match4padel_api.exceptions;



public class CourtNotFoundException extends RuntimeException {

    public CourtNotFoundException(String field, String value) {
        super("No se ha encontrado ninguna pista con " + field + " " + value + ".");
    }
}
