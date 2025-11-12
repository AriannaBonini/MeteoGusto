package com.example.meteo_gusto.eccezione;

import java.io.Serial;

public class ValidazioneException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public ValidazioneException(String message) {super(message);}
    public ValidazioneException(String message, Throwable cause) {
        super(message, cause);
    }

}