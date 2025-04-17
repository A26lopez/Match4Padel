package com.match4padel.match4padel_api.exceptions;


public class ReservationNotFoundException extends ResourceNotFoundException {

    public ReservationNotFoundException(String field, String value) {
        super("No se ha encontrado ninguna reserva con " + field + " " + value + ".");
    }
}
