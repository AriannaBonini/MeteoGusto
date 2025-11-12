package com.example.meteo_gusto.eccezione;

public class EccezioneDAO extends Exception {
    public EccezioneDAO(String messaggio, Throwable causa) {super(messaggio, causa);}
    public EccezioneDAO(String messaggio) {
        super(messaggio);
    }
    public EccezioneDAO(Throwable cause) {
        super(cause);
    }
}

