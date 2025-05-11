package com.match4padel.match4padel_api.exceptions;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super("Contraseña incorrecta, por favor, inténtalo otra vez.");
    }
}
