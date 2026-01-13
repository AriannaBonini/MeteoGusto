package com.example.meteo_gusto.enumerazione;

public enum FasciaPrezzoRistorante {
    ECONOMICO("economico"),
    MODERATO("moderato"),
    COSTOSO("costoso"),
    LUSSO("lusso");

    private final String id;

    FasciaPrezzoRistorante(String id) {
        this.id = id;
    }
    public String getId() {return id;}

    public static FasciaPrezzoRistorante fasciaPrezzoDaId(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        for (FasciaPrezzoRistorante tipo : values()) {
            if (tipo.getId().equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valore non valido per FasciaPrezzoRistorante: " + id);
    }
}
