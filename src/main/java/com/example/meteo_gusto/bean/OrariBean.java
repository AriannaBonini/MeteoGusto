package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

import java.time.LocalTime;

public class OrariBean {

    private LocalTime inizioPranzo;
    private LocalTime finePranzo;
    private LocalTime inizioCena;
    private LocalTime fineCena;

    /* COSTRUTTORE CON PARAMETRI */
    public OrariBean(LocalTime inizioPranzo, LocalTime finePranzo, LocalTime inizioCena, LocalTime fineCena) throws ValidazioneException  {
        validaOrario(inizioPranzo, finePranzo, "pranzo");
        validaOrario(inizioCena, fineCena, "cena");

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


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaOrario(LocalTime inizio, LocalTime fine, String tipo) throws ValidazioneException {
        if (inizio == null || fine == null) {
            throw new ValidazioneException("Gli orari di " + tipo + " non possono essere null");
        }
        if (!inizio.isBefore(fine)) {
            throw new ValidazioneException("L'orario di inizio " + tipo + " deve essere prima dell'orario di fine");
        }
    }




}
