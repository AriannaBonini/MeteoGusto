package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.FasciaOraria;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
    private LocalDate data;
    private LocalTime ora;
    private Integer numeroPersone;
    private Persona utente;
    private Ambiente ambiente;
    private FasciaOraria fasciaOraria;

    public Prenotazione(LocalDate data, LocalTime ora, Integer numeroPersone, Ambiente ambiente, Persona utente, FasciaOraria fasciaOraria) {
        this.data=data;
        this.ora=ora;
        this.numeroPersone=numeroPersone;
        this.utente=utente;
        this.ambiente = ambiente;
        this.fasciaOraria=fasciaOraria;
    }

    public Prenotazione() { /* COSTRUTTORE VUOTO */ }


    /* METODI SETTER E GETTER */
    public LocalTime getOra() { return ora; }
    public void setOra(LocalTime ora) {this.ora = ora;}
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) {this.data = data;}
    public Integer getNumeroPersone() { return numeroPersone; }
    public void setNumeroPersone(Integer numeroPersone) {this.numeroPersone = numeroPersone;}
    public Ambiente getAmbiente() {return ambiente;}
    public void setAmbiente(Ambiente ambiente) {this.ambiente = ambiente;}
    public Persona getUtente() {return utente;}
    public void setUtente(Persona utente) {this.utente = utente;}
    public FasciaOraria getFasciaOraria() {return fasciaOraria;}
    public void setFasciaOraria(FasciaOraria fasciaOraria) {this.fasciaOraria = fasciaOraria;}
}

