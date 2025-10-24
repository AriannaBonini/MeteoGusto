package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import java.util.Set;

public class GiorniChiusuraBean {
    private RistoranteBean ristorante;
    private Set<GiorniSettimana> giorniChiusura;

    /* COSTRUTTORE CON PARAMETRI*/
    public GiorniChiusuraBean(RistoranteBean ristorante, Set<GiorniSettimana> giorniChiusura) {
        this.ristorante=ristorante;
        this.giorniChiusura=giorniChiusura;
    }

    /* METODI GETTER E SETTER */
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public Set<GiorniSettimana> getGiorniChiusura() {return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}

}
