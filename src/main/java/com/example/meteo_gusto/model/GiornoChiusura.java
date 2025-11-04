package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;

public class GiornoChiusura {

    private Ristorante ristorante;
    private GiorniSettimana nomeGiornoChiusura;

    /* COSTRUTTORE CON PARAMETRI*/
    public GiornoChiusura(Ristorante ristorante, GiorniSettimana nomeGiornoChiusura) {
        this.ristorante=ristorante;
        this.nomeGiornoChiusura = nomeGiornoChiusura;
    }

    /* METODI GETTER E SETTER */
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public GiorniSettimana getNomeGiornoChiusura() {return nomeGiornoChiusura;}
    public void setNomeGiornoChiusura(GiorniSettimana nomeGiornoChiusura) {this.nomeGiornoChiusura = nomeGiornoChiusura;}


}
