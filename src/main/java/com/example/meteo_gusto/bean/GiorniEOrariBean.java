package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;

import java.time.LocalTime;
import java.util.Set;

public class GiorniEOrariBean {

    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;
    private Set<GiorniSettimana> giorniChiusura;

    public GiorniEOrariBean() { /* COSTRUTTORE VUOTO */ }

    public GiorniEOrariBean(LocalTime inizioPranzo, LocalTime finePranzo, LocalTime inizioCena, LocalTime fineCena, Set<GiorniSettimana> giorniChiusura) {
        this.inizioPranzo = inizioPranzo;
        this.finePranzo = finePranzo;
        this.inizioCena = inizioCena;
        this.fineCena = fineCena;
        this.giorniChiusura = giorniChiusura;
    }

    /* METODI GETTER E SETTER */
    public LocalTime getInizioPranzo() { return inizioPranzo; }
    public LocalTime getFinePranzo() { return finePranzo; }
    public LocalTime getInizioCena() { return inizioCena; }
    public LocalTime getFineCena() { return fineCena; }
    public Set<GiorniSettimana> getGiorniChiusura() { return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}

    /* METODI SETTER CON VALIDAZIONE */
    public void setInizioPranzo(LocalTime inizioPranzo) throws ValidazioneException {
        if (inizioPranzo == null) {
            throw new ValidazioneException("L'orario di inizio pranzo non può essere nullo.");
        }
        this.inizioPranzo = inizioPranzo;
    }

    public void setFinePranzo(LocalTime finePranzo) throws ValidazioneException {
        if (finePranzo == null) {
            throw new ValidazioneException("L'orario di fine pranzo non può essere nullo.");
        }
        this.finePranzo = finePranzo;
    }

    public void setInizioCena(LocalTime inizioCena) throws ValidazioneException {
        if (inizioCena == null) {
            throw new ValidazioneException("L'orario di inizio cena non può essere nullo.");
        }
        this.inizioCena = inizioCena;
    }

    public void setFineCena(LocalTime fineCena) throws ValidazioneException {
        if (fineCena == null) {
            throw new ValidazioneException("L'orario di fine cena non può essere nullo.");
        }
        this.fineCena = fineCena;
    }

}
