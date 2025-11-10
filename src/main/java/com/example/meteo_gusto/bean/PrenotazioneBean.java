package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
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
    private Integer numeroNotifiche;

    public PrenotazioneBean() { /* COSTRUTTORE VUOTO */ }

    public PrenotazioneBean(LocalTime ora, LocalDate data, Integer numeroPersone, PersonaBean utente, AmbienteBean ambiente, FasciaOraria fasciaOraria) {
        this.ora = ora;
        this.data = data;
        this.numeroPersone = numeroPersone;
        this.utente = utente;
        this.ambiente = ambiente;
        this.fasciaOraria = fasciaOraria;
    }


    /* METODI GETTER */
    public LocalTime getOra() { return ora; }
    public LocalDate getData() { return data; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public PersonaBean getUtente() { return utente; }
    public AmbienteBean getAmbiente() { return ambiente; }
    public FasciaOraria getFasciaOraria() { return fasciaOraria; }

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

    public void setUtente(PersonaBean utente) throws ValidazioneException {
        if (utente == null) {
            throw new ValidazioneException("L'utente associato alla prenotazione è obbligatorio.");
        }
        this.utente = utente;
    }

    public void setAmbiente(AmbienteBean ambiente) throws ValidazioneException {
        if (ambiente == null) {
            throw new ValidazioneException("L'ambiente selezionato è obbligatorio.");
        }
        this.ambiente = ambiente;
    }

    public void setFasciaOraria(FasciaOraria fasciaOraria){ this.fasciaOraria=fasciaOraria;}
    public Integer getNumeroNotifiche() {return numeroNotifiche;}
    public void setNumeroNotifiche(Integer numeroNotifiche) {this.numeroNotifiche = numeroNotifiche;}
}

