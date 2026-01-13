package com.example.meteo_gusto.enumerazione;

public enum TipoAmbiente {
    INTERNO("interno"),
    ESTERNO("esterno"),
    ESTERNO_COPERTO("esterno coperto");

    private final String id;
    TipoAmbiente(String id) {this.id = id;}
    public String getId() {return id;}

    public static TipoAmbiente tipoAmbienteDaId(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        for (TipoAmbiente tipo : values()) {
            if (tipo.getId().equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valore non valido per TipoAmbiente: " + id);
    }
}
