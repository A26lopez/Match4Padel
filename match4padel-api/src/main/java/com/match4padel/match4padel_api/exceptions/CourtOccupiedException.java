package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CourtOccupiedException extends RuntimeException {

    public CourtOccupiedException(Reservation reservation, List<LocalTime> freeHours) {
        super(buildMessage(reservation, freeHours));

    }

    private static String buildMessage(Reservation reservation, List<LocalTime> freeHours) {

        LocalTime startTime = reservation.getStartTime();
        LocalTime endTime = reservation.getEndTime();

        LocalTime nextFreeHour = freeHours.stream()
                .filter(hour -> hour.isAfter(endTime) || hour.equals(endTime))
                .findFirst()
                .orElse(null);

        String formattedDate = reservation.getDate().format(
                DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"))
        );
        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String courtName = reservation.getCourt().getName();

        String nextFreeHourText = (nextFreeHour != null)
                ? nextFreeHour.format(DateTimeFormatter.ofPattern("HH:mm"))
                : "no hay más disponibilidad ese día";

        return "La pista " + courtName + " está ocupada el día " + formattedDate
                + " a las " + formattedStartTime + ". La próxima hora libre: " + nextFreeHourText + ".";
    }

}
