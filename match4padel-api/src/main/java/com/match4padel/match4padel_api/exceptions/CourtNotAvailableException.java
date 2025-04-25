package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Court;

public class CourtNotAvailableException extends RuntimeException{

        public CourtNotAvailableException(Court court) {
        super("La pista " + court.getName() + " está en mantenimiento.");
    }
}
