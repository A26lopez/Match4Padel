package com.match4padel.match4padel_api.exceptions;

import java.time.LocalTime;

public class ClubClosedException extends RuntimeException{
    public ClubClosedException(LocalTime openingTime, LocalTime closingTime){
        super("El club está abierto de " + openingTime + " a " + closingTime + ". Por favor, elija un horario dentro de ese rango.");

    }
}
