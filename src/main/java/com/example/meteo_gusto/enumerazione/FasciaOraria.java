package com.example.meteo_gusto.enumerazione;

public enum FasciaOraria {
    PRANZO("pranzo"),
    CENA("cena");
    private final String id;
    FasciaOraria(String id) {this.id = id;}
    public String getId() {return id;}
}
