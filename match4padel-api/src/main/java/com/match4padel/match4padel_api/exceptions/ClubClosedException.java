package com.match4padel.match4padel_api.exceptions;

import java.time.LocalTime;

public class ClubClosedException extends RuntimeException{
    public ClubClosedException(LocalTime openingTime, LocalTime closingTime){
        super("El horario del club es de " + openingTime + " a " + closingTime + ".");

    }
}
