package com.match4padel.match4padel_api.exceptions;

public class PastDateTimeException extends RuntimeException{

    public PastDateTimeException() {
        super("No se puede hacer una reserva para una fecha u hora pasada.");
    }
}
