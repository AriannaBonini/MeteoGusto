package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.FasciaOraria;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrenotazioneBean {

    private LocalTime ora;
    private LocalDate data;
    private Integer numeroPersone;
    private PersonaBean utente;
    private AmbienteBean ambiente;
    private FasciaOraria fasciaOraria;

    public PrenotazioneBean() { /* COSTRUTTORE VUOTO */ }

    public PrenotazioneBean(LocalTime ora, LocalDate data, Integer numeroPersone, PersonaBean utente, AmbienteBean ambiente, FasciaOraria fasciaOraria) {
        this.ora = ora;
        this.data = data;
        this.numeroPersone = numeroPersone;
        this.utente = utente;
        this.ambiente = ambiente;
        this.fasciaOraria = fasciaOraria;
    }



    /* METODI SETTER E GETTER */
    public LocalTime getOra() {return ora;}
    public void setOra(LocalTime ora) {this.ora = ora;}
    public LocalDate getData() {return data;}
    public void setData(LocalDate data) {this.data = data;}
    public Integer getNumeroPersone() {return numeroPersone;}
    public void setNumeroPersone(Integer numeroPersone) {this.numeroPersone = numeroPersone;}
    public PersonaBean getUtente() {return utente;}
    public void setUtente(PersonaBean utente) {this.utente = utente;}
    public AmbienteBean getAmbiente() {return ambiente;}
    public void setAmbiente(AmbienteBean ambiente) {this.ambiente = ambiente;}
    public FasciaOraria getFasciaOraria() {return fasciaOraria;}
    public void setFasciaOraria(FasciaOraria fasciaOraria) {this.fasciaOraria = fasciaOraria;}
}

