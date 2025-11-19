package com.example.meteo_gusto.eccezione;

/**
 * Eccezione lanciata quando la data di prenotazione
 * non rientra nel range massimo consentito per le previsioni meteo.
 */
public class PrevisioniMeteoFuoriRangeException extends Exception {

    public PrevisioniMeteoFuoriRangeException(String message) {
        super(message);
    }

    public PrevisioniMeteoFuoriRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
