package com.example.meteo_gusto.bean;

import java.math.BigDecimal;

public class RecensioneBean {
    private BigDecimal stelle;
    private String ristorante;

    public RecensioneBean() { /* COSTRUTTORE VUOTO */ }

    /* METODI GETTER */
    public BigDecimal getStelle() {return stelle;}
    public String getRistorante() {return ristorante;}


    /* METODI SETTER */
    public void setStelle(BigDecimal stelle) {
        if (stelle == null) {
            throw new IllegalArgumentException("Il numero di stelle non può essere null.");
        }
        this.stelle=stelle;

    }

    public void setRistorante(String ristorante) {
        if (ristorante == null) {
            throw new IllegalArgumentException("Il ristorante associato alla recensione non può essere null.");
        }
        this.ristorante = ristorante;
    }
}
