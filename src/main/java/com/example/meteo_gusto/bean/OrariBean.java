package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

import java.time.LocalTime;

import java.time.LocalTime;

public class OrariBean {

    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;

    public OrariBean() { /* COSTRUTTORE VUOTO */ }

    /* SETTER CON VALIDAZIONE */
    public void setInizioPranzo(LocalTime inizioPranzo) throws ValidazioneException {
        if (inizioPranzo == null) {
            throw new ValidazioneException("L'orario di inizio pranzo non può essere nullo.");
        }
        if (finePranzo != null && !inizioPranzo.isBefore(finePranzo)) {
            throw new ValidazioneException("L'orario di inizio pranzo deve essere prima dell'orario di fine pranzo.");
        }
        this.inizioPranzo = inizioPranzo;
    }

    public void setFinePranzo(LocalTime finePranzo) throws ValidazioneException {
        if (finePranzo == null) {
            throw new ValidazioneException("L'orario di fine pranzo non può essere nullo.");
        }
        if (inizioPranzo != null && !inizioPranzo.isBefore(finePranzo)) {
            throw new ValidazioneException("L'orario di fine pranzo deve essere dopo l'inizio pranzo.");
        }
        if (inizioCena != null && !finePranzo.isBefore(inizioCena)) {
            throw new ValidazioneException("La fine del pranzo deve essere prima dell'inizio della cena.");
        }
        this.finePranzo = finePranzo;
    }

    public void setInizioCena(LocalTime inizioCena) throws ValidazioneException {
        if (inizioCena == null) {
            throw new ValidazioneException("L'orario di inizio cena non può essere nullo.");
        }
        if (finePranzo != null && !finePranzo.isBefore(inizioCena)) {
            throw new ValidazioneException("L'inizio della cena deve essere dopo la fine del pranzo.");
        }
        if (fineCena != null && !inizioCena.isBefore(fineCena)) {
            throw new ValidazioneException("L'orario di inizio cena deve essere prima dell'orario di fine cena.");
        }
        this.inizioCena = inizioCena;
    }

    public void setFineCena(LocalTime fineCena) throws ValidazioneException {
        if (fineCena == null) {
            throw new ValidazioneException("L'orario di fine cena non può essere nullo.");
        }
        if (inizioCena != null && !inizioCena.isBefore(fineCena)) {
            throw new ValidazioneException("L'orario di fine cena deve essere dopo l'inizio cena.");
        }
        this.fineCena = fineCena;
    }

    /* GETTER */
    public LocalTime getInizioPranzo() { return inizioPranzo; }
    public LocalTime getFinePranzo() { return finePranzo; }
    public LocalTime getInizioCena() { return inizioCena; }
    public LocalTime getFineCena() { return fineCena; }
}
