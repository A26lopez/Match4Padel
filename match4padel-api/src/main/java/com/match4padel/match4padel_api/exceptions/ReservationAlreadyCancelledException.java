package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;

public class ReservationAlreadyCancelledException extends RuntimeException {

    public ReservationAlreadyCancelledException(Reservation reservation) {
        super("La reserva ya está cancelada.");
    }
}
