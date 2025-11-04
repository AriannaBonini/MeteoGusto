package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.TipoDieta;

public class Dieta {
    private Ristorante ristorante;
    private TipoDieta tipoDieta;

    /* COSTRUTTORI CON PARAMETRI */
    public Dieta(Ristorante ristorante, TipoDieta tipoDieta) {
        this.ristorante=ristorante;
        this.tipoDieta = tipoDieta;
    }

    /* METODI GETTER E SETTER */
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public TipoDieta getTipoDieta() {return tipoDieta;}
    public void setTipoDieta(TipoDieta tipoDieta) {this.tipoDieta = tipoDieta;}
}
