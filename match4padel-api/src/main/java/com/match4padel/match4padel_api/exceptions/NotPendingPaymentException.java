package com.match4padel.match4padel_api.exceptions;

import com.match4padel.match4padel_api.models.Payment;

public class NotPendingPaymentException extends RuntimeException {

    public NotPendingPaymentException(Payment payment) {
        super("No hay ningún pago pendiente con id " + payment.getId());
    }
}
