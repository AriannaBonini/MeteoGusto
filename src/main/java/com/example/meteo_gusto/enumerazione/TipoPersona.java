package com.example.meteo_gusto.enumerazione;

public enum TipoPersona {
    UTENTE("utente"),
    RISTORATORE("ristoratore");

    private final String id;

    TipoPersona(String id) {
        this.id = id;
    }
    public String getId() {return id;}
    public static TipoPersona fromId(String id) {
        for (TipoPersona tipo : values()) {
            if (tipo.getId().equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valore non valido: " + id);
    }
}
