package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;

public class ReservationAlreadyCompletedException extends RuntimeException {

    public ReservationAlreadyCompletedException(Reservation reservation) {
        super("La reserva " + reservation.getId()+ " ya ha terminado.");
    }
}
