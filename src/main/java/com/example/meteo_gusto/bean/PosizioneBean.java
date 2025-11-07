package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class PosizioneBean {

    private String indirizzoCompleto;
    private String cap;
    private String citta;

    public PosizioneBean() { /* COSTRUTTORE VUOTO */ }
    public PosizioneBean(String indirizzoCompleto, String cap, String citta) {
        this.indirizzoCompleto = indirizzoCompleto;
        this.cap = cap;
        this.citta = citta;
    }


    public String getIndirizzoCompleto() { return indirizzoCompleto; }
    public String getCap() { return cap; }
    public String getCitta() { return citta; }

    public void setIndirizzoCompleto(String indirizzoCompleto) throws ValidazioneException {
        if (indirizzoCompleto == null || indirizzoCompleto.trim().isEmpty()) {
            throw new ValidazioneException("Il campo indirizzo è obbligatorio.");
        }
        if (!indirizzoCompleto.matches(".+,\\s*\\d+")) {
            throw new ValidazioneException("L'indirizzo deve contenere una virgola seguita dal civico (es. Via Roma, 12).");
        }
        this.indirizzoCompleto = indirizzoCompleto.trim();
    }

    public void setCap(String cap) throws ValidazioneException {
        if (cap == null || cap.trim().isEmpty()) {
            throw new ValidazioneException("Il CAP non può essere vuoto.");
        }
        if (!cap.matches("^\\d{5}$")) {
            throw new ValidazioneException("Il CAP deve essere un numero di 5 cifre.");
        }
        this.cap = cap.trim();
    }

    public void setCitta(String citta) throws ValidazioneException {
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
        this.citta = citta.trim();
    }
}

