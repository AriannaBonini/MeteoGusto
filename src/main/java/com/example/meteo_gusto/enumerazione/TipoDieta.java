package com.example.meteo_gusto.enumerazione;

public enum TipoDieta {
    HALAL("halal"),
    VEGANO("vegano"),
    SENZA_LATTOSIO("senza lattosio"),
    PESCETARIANO("pescetariano"),
    VEGETARIANO("vegetariano"),
    CELIACO("celiaco"),
    KOSHER("kosher");

    private final String id;

    TipoDieta(String id) {
        this.id = id;
    }
    public String getId() {return id;}
}
