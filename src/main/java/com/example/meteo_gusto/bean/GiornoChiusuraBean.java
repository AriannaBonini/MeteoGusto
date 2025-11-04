package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;

public class GiornoChiusuraBean {
    private RistoranteBean ristorante;
    private GiorniSettimana giornoChiusura;

    public GiornoChiusuraBean() { /* COSTRUTTORE VUOTO */ }

    /* METODI GETTER E SETTER */
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public GiorniSettimana getGiornoChiusura() {return giornoChiusura;}
    public void setGiornoChiusura(GiorniSettimana giornoChiusura) {this.giornoChiusura = giornoChiusura;}

}
