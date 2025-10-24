package com.example.meteo_gusto.model;


public class Posizione {
    private String indirizzoCompleto;
    private String citta;
    private String cap;

    /* COSTRUTTORE CON PARAMETRI */
    public Posizione(String indirizzoCompleto, String citta, String cap){

        this.indirizzoCompleto = indirizzoCompleto;
        this.citta = citta;
        this.cap = cap;
    }

    /* METODI GETTER E SETTER */
    public String getIndirizzoCompleto() {return indirizzoCompleto;}
    public void setIndirizzoCompleto(String indirizzoCompleto) {this.indirizzoCompleto = indirizzoCompleto;}
    public String getCitta() {return citta;}
    public void setCitta(String citta) {this.citta = citta;}
    public String getCap() {return cap;}
    public void setCap(String cap) {this.cap = cap;}
}
