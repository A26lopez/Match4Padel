package com.match4padel.match4padel_api.exceptions;

public class PastDateTimeException extends RuntimeException{

    public PastDateTimeException() {
        super("Lo sentimos, no puedes hacer una reserva para una fecha o hora pasada.");
    }
}
