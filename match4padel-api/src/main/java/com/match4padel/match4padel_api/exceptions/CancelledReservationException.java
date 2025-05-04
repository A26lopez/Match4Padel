package com.match4padel.match4padel_api.exceptions;

public class CancelledReservationException extends RuntimeException {

    public CancelledReservationException() {
        super("Las reservas canceladas no se pueden modificar.");

    }
}
