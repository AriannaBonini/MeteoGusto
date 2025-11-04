package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.TipoDieta;

public class DietaBean {
    private RistoranteBean ristorante;
    private TipoDieta dieta;

    public DietaBean() { /* COSTRUTTORE VUOTO */}

    /* METODI GETTER E SETTER */
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public TipoDieta getDieta() {return dieta;}
    public void setDieta(TipoDieta dieta) {this.dieta = dieta;}
}
