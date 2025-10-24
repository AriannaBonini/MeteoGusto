package com.example.meteo_gusto.model;

import java.time.LocalTime;

public class Orari {
    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;

    /* COSTRUTTORE CON PARAMETRI */
    public Orari(LocalTime inizioPranzo, LocalTime finePranzo, LocalTime inizioCena, LocalTime fineCena) {
        this.inizioPranzo=inizioPranzo;
        this.finePranzo= finePranzo;
        this.inizioCena=inizioCena;
        this.fineCena=fineCena;
    }

    /* METODI GETTER E SETTER */
    public LocalTime getInizioPranzo() {return inizioPranzo;}
    public void setInizioPranzo(LocalTime inizioPranzo) {this.inizioPranzo = inizioPranzo;}
    public LocalTime getFinePranzo() {return finePranzo;}
    public void setFinePranzo(LocalTime finePranzo) {this.finePranzo = finePranzo;}
    public LocalTime getInizioCena() {return inizioCena;}
    public void setInizioCena(LocalTime inizioCena) {this.inizioCena = inizioCena;}
    public LocalTime getFineCena() {return fineCena;}
    public void setFineCena(LocalTime fineCena) {this.fineCena = fineCena;}

}
