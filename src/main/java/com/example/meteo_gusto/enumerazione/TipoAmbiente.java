package com.example.meteo_gusto.enumerazione;

public enum TipoAmbiente {
    INTERNO("interno"),
    ESTERNO("esterno");

    private final String id;
    TipoAmbiente(String id) {this.id = id;}
    public String getId() {return id;}
}
