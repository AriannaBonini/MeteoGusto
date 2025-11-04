package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

import java.time.LocalDate;
import java.time.LocalTime;

public class FiltriBean {
    private LocalDate data;
    private LocalTime ora;
    private String citta;
    private Integer numeroPersone;

    public FiltriBean() { /* COSTRUTTORE VUOTO */ }

    public FiltriBean(LocalDate data, LocalTime ora, String citta, Integer numeroPersone) throws ValidazioneException {
        validaCitta(citta);
        validaOra(ora);
        validaData(data);
        validaNumeroPersone(numeroPersone);

        this.data=data;
        this.ora=ora;
        this.citta=citta;
        this.numeroPersone=numeroPersone;
    }



    /* METODI DI VALIDAZIONE SINTATTICA */
    private void validaOra(LocalTime ora) throws ValidazioneException{
        if (ora == null) {
            throw new ValidazioneException("L'orario della prenotazione non può essere nullo.");
        }
    }

    private void validaData(LocalDate data) throws ValidazioneException{
        if (data == null) {
            throw new ValidazioneException("Data non valida. Usa il formato GG/MM/AAAA");
        }
    }

    private void validaNumeroPersone(Integer numeroPersone) throws ValidazioneException{
        if (numeroPersone == null || numeroPersone<=0) {
            throw new ValidazioneException("Il numero di persone deve essere maggiore di zero.");
        }
    }
    private void validaCitta(String citta) throws ValidazioneException {
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ' ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
    }



    /* METODI GETTER E SETTER */
    public LocalDate getData() {return data;}
    public LocalTime getOra() {return ora;}
    public String getCitta() {return citta;}
    public Integer getNumeroPersone() {return numeroPersone;}
    public void setCitta(String citta) {this.citta = citta;}
    public void setOra(LocalTime ora) {this.ora = ora;}
    public void setData(LocalDate data) {this.data = data;}
    public void setNumeroPersone(Integer numeroPersone) {this.numeroPersone = numeroPersone;}
}
