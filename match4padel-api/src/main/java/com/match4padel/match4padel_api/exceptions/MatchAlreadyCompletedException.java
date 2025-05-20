package com.match4padel.match4padel_api.exceptions;


public class MatchAlreadyCompletedException extends RuntimeException {

    public MatchAlreadyCompletedException() {
        super("No puedes salir de un partido terminado.");
    }
}
