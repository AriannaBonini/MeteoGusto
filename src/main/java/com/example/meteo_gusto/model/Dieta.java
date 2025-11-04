package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.TipoDieta;

import java.util.Set;

public class Dieta {
    private Ristorante ristorante;
    private Set<TipoDieta> tipoDieta;

    /* COSTRUTTORI CON PARAMETRI */
    public Dieta(Ristorante ristorante, Set<TipoDieta> tipoDieta) {
        this.ristorante=ristorante;
        this.tipoDieta = tipoDieta;
    }

    /* METODI GETTER E SETTER */
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public Set<TipoDieta> getTipoDieta() {return tipoDieta;}
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
}
