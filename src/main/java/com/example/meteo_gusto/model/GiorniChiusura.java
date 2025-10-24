package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import java.util.Set;

public class GiorniChiusura {

    private Ristorante ristorante;
    private Set<GiorniSettimana> giorniChiusura;

    /* COSTRUTTORE CON PARAMETRI*/
    public GiorniChiusura(Ristorante ristorante, Set<GiorniSettimana> giorniChiusura) {
        this.ristorante=ristorante;
        this.giorniChiusura=giorniChiusura;
    }

    /* METODI GETTER E SETTER */
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public Set<GiorniSettimana> getGiorniChiusura() {return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}

}
