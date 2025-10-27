package com.example.meteo_gusto.enumerazione;

public enum TipoAmbienteConExtra {
    ESTERNO_COPERTO("esterno_coperto");
    private final String id;
    TipoAmbienteConExtra(String id) {this.id = id;}

    public String getId() {return id;}
}
