package com.match4padel.match4padel_api.exceptions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String field, String value) {
        super("No se ha encontrado ninguna reserva con " + field + " " + value + ".");
    }

    public ReservationNotFoundException(String courtName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super(buildMessage(courtName, date, startTime, endTime));
    }

    private static String buildMessage(String courtName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
        String formattedStarTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return "No se ha encontrado ninguna reserva en la pista " + courtName + " para el día " + formattedDate
                + " de " + formattedStarTime + " a " + formattedEndTime + ".";
    }
}
