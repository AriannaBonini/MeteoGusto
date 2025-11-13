package com.example.meteo_gusto.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Recensione {
    private Persona utente;
    private Ristorante ristorante;
    private BigDecimal stelle;
    private LocalDate data;


    /* COSTRUTTORE */
    public Recensione(Persona utente, Ristorante ristorante, BigDecimal stelle, LocalDate data) {
        this.utente=utente;
        this.ristorante=ristorante;
        this.stelle=stelle;
        this.data=data;
    }

    /* METODI GETTER E SETTER */
    public Persona getUtente() {return utente;}
    public void setUtente(Persona utente) {this.utente = utente;}
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public BigDecimal getStelle() {return stelle;}
    public LocalDate getData() {return data;}
    public void setData(LocalDate data) {this.data = data;}

    public void aggiungiStelle(BigDecimal stelle) throws IllegalArgumentException {
        if (stelle == null || stelle.compareTo(BigDecimal.ONE) < 0 || stelle.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("Il numero di stelle deve essere compreso tra 1 e 5.");
        }
        this.stelle = stelle;
    }

}
