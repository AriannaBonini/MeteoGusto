package com.example.meteo_gusto.eccezione;

/**
 * Eccezione lanciata quando esiste già una prenotazione
 * per una deterimata data e ora.
 */

public class PrenotazioneEsistenteException extends Exception{
    public PrenotazioneEsistenteException(String message) {super(message);}
}
