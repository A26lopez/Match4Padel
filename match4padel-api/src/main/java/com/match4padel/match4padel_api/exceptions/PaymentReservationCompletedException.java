package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Reservation;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PaymentReservationCompletedException extends RuntimeException {

    public PaymentReservationCompletedException(Reservation reservation) {
        super(buildMessage(reservation));
    }

    private static String buildMessage(Reservation reservation) {
        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES")));
        String formattedStartTime = reservation.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        return "No se puede procesar el pago de la reserva en la pista " + reservation.getCourt().getName() + " para el día " + formattedDate
                + " a las " + formattedStartTime + ". La reserva ya ha finalizado.";
    }
}
