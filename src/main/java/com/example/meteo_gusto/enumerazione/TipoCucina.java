package com.example.meteo_gusto.enumerazione;

public enum TipoCucina {
    ITALIANA("italiana"),
    SUSHI("sushi"),
    FAST_FOOD("fast_food"),
    GRECA("greca"),
    CINESE("cinese"),
    TURCA("turca"),
    PIZZA("pizza"),
    MESSICANA("messicana");

    private final String id;

    TipoCucina(String id) {
        this.id = id;
    }
    public String getId() {return id;}
}
