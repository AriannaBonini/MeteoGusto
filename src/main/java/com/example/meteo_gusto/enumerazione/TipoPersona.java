package com.example.meteo_gusto.enumerazione;

public enum TipoPersona {
    UTENTE("utente"),
    RISTORATORE("ristoratore");

    private final String id;

    TipoPersona(String id) {
        this.id = id;
    }
    public String getId() {return id;}
}
