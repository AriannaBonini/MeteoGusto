package com.example.meteo_gusto.eccezione;

public class ValidazioneException extends Exception {
    private static final long serialVersionUID = 1L;

    public ValidazioneException() {
        super("Tutti i campi sono obbligatori");
    }

    public ValidazioneException(Throwable cause) {
        super(cause);
    }

    public ValidazioneException(String message) {
        super(message);
    }
    public ValidazioneException(String message, Throwable cause) {
        super(message, cause);
    }
}