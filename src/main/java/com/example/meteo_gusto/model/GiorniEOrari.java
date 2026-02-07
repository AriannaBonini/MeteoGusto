package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import java.time.LocalTime;
import java.util.Set;

public class GiorniEOrari {

    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;
    private Set<GiorniSettimana> giorniChiusura;

    /* COSTRUTTORE */
    public GiorniEOrari(LocalTime inizioPranzo, LocalTime finePranzo, LocalTime inizioCena, LocalTime fineCena, Set<GiorniSettimana> giorniChiusura) {
        this.inizioPranzo=inizioPranzo;
        this.finePranzo=finePranzo;
        this.inizioCena=inizioCena;
        this.fineCena=fineCena;
        this.giorniChiusura=giorniChiusura;
    }

    public GiorniEOrari() { /* COSTRUTTORE VUOTO */ }


    /* METODI GETTER E SETTER */
    public LocalTime orarioInizioPranzo() {return inizioPranzo;}
    public void setInizioPranzo(LocalTime inizioPranzo) {this.inizioPranzo = inizioPranzo;}
    public LocalTime orarioFinePranzo() {return finePranzo;}
    public void setFinePranzo(LocalTime finePranzo) {this.finePranzo = finePranzo;}
    public LocalTime orarioInizioCena() {return inizioCena;}
    public void setInizioCena(LocalTime inzioCena) {this.inizioCena = inzioCena;}
    public LocalTime orarioFineCena() {return fineCena;}
    public void setFineCena(LocalTime fineCena) {this.fineCena = fineCena;}
    public Set<GiorniSettimana> giorniChiusura() {return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}
}
