package com.example.meteo_gusto.enumerazione;

public enum Extra {
    RISCALDAMENTO("riscaldamento"),
    RAFFREDDAMENTO("raffreddamento");
    private final String id;
    Extra(String id) {this.id = id;}

    public String getId() {return id;}
}
