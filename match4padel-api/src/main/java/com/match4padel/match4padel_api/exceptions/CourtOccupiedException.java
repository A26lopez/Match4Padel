package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CourtOccupiedException extends RuntimeException {

    public CourtOccupiedException(Reservation reservation) {
        super(buildMessage(reservation.getCourt().getName(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime()));
    }

    private static String buildMessage(String courtName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
        String formattedStarTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return "La pista " + courtName + " está ocupada el día " + formattedDate
                + " entre las " + formattedStarTime + " y las " + formattedEndTime + ".";
    }
}
