package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.TipoDieta;

import java.util.Set;

public class DietaBean {
    private RistoranteBean ristorante;
    private Set<TipoDieta> tipoDieta;

    public DietaBean() { /* COSTRUTTORE VUOTO */}

    /* METODI GETTER E SETTER */
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public Set<TipoDieta> getTipoDieta() {return tipoDieta;}
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
}
