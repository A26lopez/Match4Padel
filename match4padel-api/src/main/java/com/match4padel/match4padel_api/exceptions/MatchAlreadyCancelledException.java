package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;

public class MatchAlreadyCancelledException extends RuntimeException {

    public MatchAlreadyCancelledException() {
        super("No peudes salir de un partido cancelado.");
    }
}
