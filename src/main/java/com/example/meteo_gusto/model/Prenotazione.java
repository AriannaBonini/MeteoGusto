package com.example.meteo_gusto.model;


import com.example.meteo_gusto.enumerazione.FasciaOraria;
import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
    private LocalDate data;
    private LocalTime ora;
    private Integer numeroPersone;
    private String prenotante;
    private FasciaOraria fasciaOraria;
    private Integer numeroNotifiche;
    private String note;
    private Ambiente ambiente;

    public Prenotazione(LocalDate data, LocalTime ora, Integer numeroPersone, Ambiente ambiente, String prenotante, FasciaOraria fasciaOraria) {
        this.data=data;
        this.ora=ora;
        this.numeroPersone=numeroPersone;
        this.prenotante = prenotante;
        this.ambiente = ambiente;
        this.fasciaOraria=fasciaOraria;
    }

    public Prenotazione(LocalDate data, LocalTime ora, Integer numeroPersone, Ambiente ambiente, String note) {
        this.data=data;
        this.ora=ora;
        this.numeroPersone=numeroPersone;
        this.ambiente = ambiente;
        this.note=note;
    }

    public Prenotazione() { /* COSTRUTTORE VUOTO */ }


    /* METODI SETTER E GETTER */
    public LocalTime oraPrenotazione() { return ora; }
    public void setOra(LocalTime ora) {this.ora = ora;}
    public LocalDate dataPrenotazione() { return data; }
    public void setData(LocalDate data) {this.data = data;}
    public Integer numeroPersone() { return numeroPersone; }
    public void setNumeroPersone(Integer numeroPersone) {this.numeroPersone = numeroPersone;}
    public Ambiente getAmbiente() {return ambiente;}
    public void aggiungiAmbiente(Ambiente ambiente) {this.ambiente = ambiente;}
    public String prenotante() {return prenotante;}
    public void setPrenotante(String prenotante) {this.prenotante = prenotante;}
    public FasciaOraria getFasciaOraria() {return fasciaOraria;}
    public void setFasciaOraria(FasciaOraria fasciaOraria) {this.fasciaOraria = fasciaOraria;}
    public Integer getNumeroNotifiche() {return numeroNotifiche;}
    public void setNumeroNotifiche(Integer numeroNotifiche) {this.numeroNotifiche = numeroNotifiche;}
    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}
}

