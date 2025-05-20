package com.match4padel.match4padel_api.exceptions;


public class ReservationAlreadyCompletedException extends RuntimeException {

    public ReservationAlreadyCompletedException() {
        super("La reserva ya ha terminado.");
    }
}
