package com.example.meteo_gusto.enumerazione;

public enum FasciaPrezzoRistorante {
    ECONOMICO("economico"),
    MODERATO("moderato"),
    COSTOSO("costoso"),
    LUSSO("lusso");

    private final String id;

    FasciaPrezzoRistorante(String id) {
        this.id = id;
    }
    public String getId() {return id;}
}
