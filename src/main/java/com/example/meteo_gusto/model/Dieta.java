package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.util.Set;

public class Dieta {

    private Ristorante ristorante;
    private Set<TipoDieta> dieta;

    /* COSTRUTTORI CON PARAMETRI */
    public Dieta(Ristorante ristorante, Set<TipoDieta> dieta) {
        this.ristorante=ristorante;
        this.dieta=dieta;
    }

    /* METODI GETTER E SETTER */
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public Set<TipoDieta> getDieta() {return dieta;}
    public void setDieta(Set<TipoDieta> dieta) {this.dieta = dieta;}
}
