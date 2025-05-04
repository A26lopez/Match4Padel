package com.match4padel.match4padel_api.exceptions;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String field, String value) {
        super("No se ha encontrado ningún pago con " + field + " " + value + ".");
    }
}
