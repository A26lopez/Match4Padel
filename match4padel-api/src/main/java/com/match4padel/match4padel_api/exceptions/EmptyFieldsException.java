package com.match4padel.match4padel_api.exceptions;

    public class EmptyFieldsException extends RuntimeException{
    public EmptyFieldsException(){
        super("Por favor, rellene todos los campos obligatorios.");
    }
}
