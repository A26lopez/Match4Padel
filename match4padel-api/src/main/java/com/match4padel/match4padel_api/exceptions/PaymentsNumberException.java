package com.match4padel.match4padel_api.exceptions;

public class PaymentsNumberException extends RuntimeException {

    public PaymentsNumberException() {
        super("La reserva no puede tener más de 4 pagos.");
    }
}
