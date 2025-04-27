package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CourtOccupiedException extends RuntimeException {

    public CourtOccupiedException(List<Reservation> conflictingReservations) {
        super(buildMessage(conflictingReservations));

    }

    private static String buildMessage(List<Reservation> conflictingReservations) {
      
        conflictingReservations.sort((r1, r2) -> r1.getStartTime().compareTo(r2.getStartTime()));
     
        Reservation firstReservation = conflictingReservations.get(0);
        Reservation lastReservation = conflictingReservations.get(conflictingReservations.size() - 1);

        String formattedDate = firstReservation.getDate().format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
        String formattedStartTime = firstReservation.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String formattedEndTime = lastReservation.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String courtName = firstReservation.getCourt().getName();

        
        return "La pista " + courtName + " está ocupada el día " + formattedDate
                + " desde las " + formattedStartTime + " hasta las " + formattedEndTime + ".";
    }
}
