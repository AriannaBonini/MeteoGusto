package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FiltriBean {
    private LocalDate data;
    private LocalTime ora;
    private String citta;
    private Integer numeroPersone;
    private String fasciaPrezzo;
    private List<String> cucine;
    private List<String> diete;
    private boolean meteo;

    public FiltriBean() { /* COSTRUTTORE VUOTO */ }


    /* METODI GETTER E SETTER */
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getCitta() { return citta; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public List<String> getCucine() { return cucine; }
    public void setCucine(List<String> cucine) {this.cucine = cucine;}
    public String getFasciaPrezzo() { return fasciaPrezzo; }
    public void setFasciaPrezzo(String fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}
    public boolean getMeteo() { return meteo; }
    public void setMeteo(boolean meteo) {this.meteo = meteo;}
    public List<String> getDiete() { return diete; }
    public void setDiete(List<String> diete) {this.diete = diete;}



    /* METODI SETTER CON VALIDAZIONE SINTATTICA */
    public void setData(LocalDate data) throws ValidazioneException {
        if (data == null) {
            throw new ValidazioneException("Data non valida. Usa il formato GG/MM/AAAA");
        }
        this.data = data;
    }

    public void setOra(LocalTime ora) throws ValidazioneException {
        if (ora == null) {
            throw new ValidazioneException("L'orario della prenotazione non può essere nullo.");
        }
        this.ora = ora;
    }

    public void setCitta(String citta) throws ValidazioneException {
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ' ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
        this.citta = citta;
    }

    public void setNumeroPersone(Integer numeroPersone) throws ValidazioneException {
        if (numeroPersone == null || numeroPersone <= 0) {
            throw new ValidazioneException("Il numero di persone deve essere maggiore di zero.");
        }
        this.numeroPersone = numeroPersone;
    }

}

