package com.example.meteo_gusto.enumerazione;

public enum TipoCucina {
    ITALIANA("Italiana"),
    SUSHI("Sushi"),
    FAST_FOOD("Fast Food"),
    GRECA("Greca"),
    CINESE("Cinese"),
    TURCA("Turca"),
    PIZZA("Pizza"),
    MESSICANA("Messicana");

    private final String id;

    TipoCucina(String id) {
        this.id = id;
    }
    public String getId() {return id;}
    public static TipoCucina fromId(String id) {
        for (TipoCucina tipo : values()) {
            if (tipo.getId().equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valore non valido per TipoCucina: " + id);
    }
}
