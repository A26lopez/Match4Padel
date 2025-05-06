package com.match4padel.match4padel_api.exceptions;

public class CancelledReservationException extends RuntimeException {

    public CancelledReservationException() {
        super("No se pueden añadir pagos a reservas canceladas.");
    }
}
