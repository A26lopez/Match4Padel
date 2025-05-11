package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Match;
import com.match4padel.match4padel_api.models.Reservation;

public class ReservationNotAvailableException extends RuntimeException {

    public ReservationNotAvailableException(Reservation reservation) {
        super("La reserva " + reservation.getId() + " tiene el estado " + reservation.getStatus());
    }
}
