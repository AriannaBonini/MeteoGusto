package com.example.meteo_gusto.model;


public class Posizione {

    private String indirizzoCompleto;
    private String citta;
    private String cap;

    public Posizione() { /* COSTRUTTORE VUOTO */ }


    public Posizione(String indirizzoCompleto, String citta, String cap) {
        this.indirizzoCompleto=indirizzoCompleto;
        this.citta=citta;
        this.cap=cap;

    }

    public String getIndirizzoCompleto() {return indirizzoCompleto;}
    public String getCitta() {return citta;}
    public void setCitta(String citta) {this.citta = citta;}
    public String getCap() {return cap;}
    public void setCap(String cap) {this.cap = cap;}


    /* METODI DI SUPPORTO */
    public String getVia() {
        if (indirizzoCompleto == null || !indirizzoCompleto.contains(",")) return indirizzoCompleto;
        return indirizzoCompleto.substring(0, indirizzoCompleto.indexOf(",")).trim();
    }

    public String getCivico() {
        if (indirizzoCompleto == null || !indirizzoCompleto.contains(",")) return "";
        return indirizzoCompleto.substring(indirizzoCompleto.indexOf(",") + 1).trim();
    }

    public void concatenaIndirizzoCompleto(String via, String civico) {
        if (via == null || via.isBlank()) {
            this.indirizzoCompleto = "";
        } else if (civico == null || civico.isBlank()) {
            this.indirizzoCompleto = via.trim();
        } else {
            this.indirizzoCompleto = via.trim() + ", " + civico.trim();
        }
    }




}
