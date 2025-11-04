package com.example.meteo_gusto.enumerazione;

public enum TipoDieta {
    HALAL("halal"),
    VEGANO("vegano"),
    SENZA_LATTOSIO("senza_lattosio"),
    PESCETARIANO("pescetariano"),
    VEGETARIANO("vegetariano"),
    CELIACO("celiaco"),
    KOSHER("kosher");

    private final String id;

    TipoDieta(String id) {
        this.id = id;
    }
    public String getId() {return id;}
    public static TipoDieta tipoDietaDaId(String id) {
        for (TipoDieta tipo : values()) {
            if (tipo.getId().equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valore non valido per TipoDieta: " + id);
    }
}
