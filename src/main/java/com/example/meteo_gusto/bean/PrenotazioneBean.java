package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PrenotazioneBean {

    private LocalDate data;
    private LocalTime ora;
    private Integer numeroPersone;
    private Integer numeroNotifiche;
    private String note;
    private RistoranteBean ristorante;
    private List<String> ambiente;
    private String nomePrenotante;
    private String cognomePrenotante;
    private String telefonoPrenotante;


    public PrenotazioneBean() {/* COSTRUTTORE VUOTO */ }


    /* METODI GETTER E SETTER */
    public LocalTime getOra() { return ora; }
    public LocalDate getData() { return data; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public List<String> getAmbiente() { return ambiente; }
    public String getNote() {return note;}
    public Integer getNumeroNotifiche() {return numeroNotifiche;}
    public void setNumeroNotifiche(Integer numeroNotifiche) {this.numeroNotifiche = numeroNotifiche;}
    public void setNote(String note) {this.note = note;}
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public String getNomePrenotante() {return nomePrenotante;}
    public void setNomePrenotante(String nomePrenotante) {this.nomePrenotante = nomePrenotante;}
    public String getCognomePrenotante() {return cognomePrenotante;}
    public void setCognomePrenotante(String cognomePrenotante) {this.cognomePrenotante = cognomePrenotante;}
    public String getTelefonoPrenotante() {return telefonoPrenotante;}
    public void setTelefonoPrenotante(String telefonoPrenotante) {this.telefonoPrenotante = telefonoPrenotante;}


    /* SETTER CON VALIDAZIONE */
    public void setOra(LocalTime ora) throws ValidazioneException {
        if (ora == null) {
            throw new ValidazioneException("L'orario della prenotazione è obbligatorio.");
        }
        this.ora = ora;
    }

    public void setData(LocalDate data) throws ValidazioneException {
        if (data == null) {
            throw new ValidazioneException("La data della prenotazione è obbligatoria.");
        }
        this.data = data;
    }

    public void setNumeroPersone(Integer numeroPersone) throws ValidazioneException {
        if (numeroPersone == null || numeroPersone <= 0) {
            throw new ValidazioneException("Il numero di persone deve essere maggiore di zero.");
        }
        this.numeroPersone = numeroPersone;
    }

    public void setAmbiente(List<String> ambiente) throws ValidazioneException {
        if (ambiente == null) {
            throw new ValidazioneException("L'ambiente selezionato è obbligatorio.");
        }
        this.ambiente = ambiente;
    }

}

