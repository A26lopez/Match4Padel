package com.match4padel.match4padel_api.exceptions;

import java.time.LocalTime;


public class ReservationTimeNotValidException extends RuntimeException {

    public ReservationTimeNotValidException(LocalTime attemptedTime) {
        super("La hora " + attemptedTime + " no es válida. Las reservas solo pueden hacerse en punto o a y media.");
    }

}