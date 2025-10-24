package com.example.meteo_gusto.enumerazione;

public enum GiorniSettimana {
    LUNEDI("lunedì"),
    MARTEDI("martedì"),
    MERCOLEDI("mercoledì"),
    GIOVEDI("giovedì"),
    VENERDI("venerdì"),
    SABATO("sabato"),
    DOMENICA("domenica");

    private final String id;

    GiorniSettimana(String id) {this.id = id;}
    public String getId() {return id;}
}
