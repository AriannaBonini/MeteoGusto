package com.example.meteo_gusto.bean;

import java.math.BigDecimal;

public class RecensioneBean {
    private BigDecimal stelle;
    private RistoranteBean ristorante;

    public RecensioneBean() { /* COSTRUTTORE VUOTO */ }

    public RecensioneBean(BigDecimal stelle, RistoranteBean ristorante) {
        setStelle(stelle);
        setRistorante(ristorante);
    }

    /* METODI GETTER */
    public BigDecimal getStelle() {return stelle;}
    public RistoranteBean getRistorante() {return ristorante;}


    /* METODI SETTER */
    public void setStelle(BigDecimal stelle) {
        if (stelle == null) {
            throw new IllegalArgumentException("Il numero di stelle non può essere null.");
        }
        if (stelle.scale() > 1) {
            throw new IllegalArgumentException("Il numero di stelle può avere al massimo una cifra decimale.");
        }
        this.stelle = stelle;
    }



    public void setRistorante(RistoranteBean ristorante) {
        if (ristorante == null) {
            throw new IllegalArgumentException("Il ristorante associato alla recensione non può essere null.");
        }
        this.ristorante = ristorante;
    }
}
