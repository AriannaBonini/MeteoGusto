package com.example.meteo_gusto.model;


import com.example.meteo_gusto.eccezione.ValidazioneException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Filtro {
    private LocalDate data;
    private LocalTime ora;
    private String citta;
    private Integer numeroPersone;

    /* COSTRUTTORE SEMPLICE */
    public Filtro(LocalDate data, LocalTime ora, String citta, Integer numeroPersone) {
        this.data = data;
        this.ora = ora;
        this.citta = citta;
        this.numeroPersone = numeroPersone;
    }

    /* METODI GETTER E SETTER */
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getCitta() { return citta; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public void setData(LocalDate data) { this.data = data; }
    public void setOra(LocalTime ora) { this.ora = ora; }
    public void setCitta(String citta) { this.citta = citta; }
    public void setNumeroPersone(Integer numeroPersone) { this.numeroPersone = numeroPersone; }


    /* METODI PER LA LOGICA DI DOMINIO */
    public void aggiungiData(LocalDate data) throws ValidazioneException {
        if (data == null || data.isBefore(LocalDate.now())) {
            throw new ValidazioneException("La data della prenotazione non può essere nulla o nel passato.");
        }
        this.data = data;
    }

    public void aggiungiNumeroPersone(Integer numeroPersone) throws IllegalArgumentException {
        if (numeroPersone == null) return;
        if (numeroPersone > 30) {
            throw new IllegalArgumentException("Il numero massimo di persone per una prenotazione è 30.");
        }
        this.numeroPersone = numeroPersone;
    }
}
