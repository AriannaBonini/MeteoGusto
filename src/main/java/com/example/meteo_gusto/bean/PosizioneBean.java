package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class PosizioneBean {
    private String indirizzoCompleto;
    private String citta;
    private String cap;

    /* COSTRUTTORE CON PARAMETRI */
    public PosizioneBean(String indirizzoCompleto, String citta, String cap) throws ValidazioneException {
        validaCampiNonVuoti(indirizzoCompleto, citta, cap);
        validaCitta(citta);
        validaCap(cap);
        validaIndirizzoCompleto(indirizzoCompleto);

        this.indirizzoCompleto = indirizzoCompleto;
        this.citta = citta;
        this.cap = cap;
    }

    /* METODI GETTER E SETTER */
    public String getIndirizzoCompleto() {return indirizzoCompleto;}
    public void setIndirizzoCompleto(String indirizzoCompleto) {this.indirizzoCompleto = indirizzoCompleto;}
    public String getCitta() {return citta;}
    public void setCitta(String citta) {this.citta = citta;}
    public String getCap() {return cap;}
    public void setCap(String cap) {this.cap = cap;}


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampiNonVuoti (String indirizzoCompleto, String citta, String cap) throws ValidazioneException {
        if (indirizzoCompleto == null || indirizzoCompleto.trim().isEmpty() ||
                citta == null || citta.trim().isEmpty() ||
                cap == null || cap.trim().isEmpty()) {
            throw new ValidazioneException("Tutti i campi dell'indirizzo sono obbligatori");
        }
    }

    private void validaCitta (String citta) throws ValidazioneException {
        if (!citta.matches("^[a-zA-Zàèéìòùçñ ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi");
        }
    }

    private void validaCap (String cap) throws ValidazioneException {
        if (!cap.matches("^\\d{5}$")) {
            throw new ValidazioneException("Il CAP deve essere un numero di 5 cifre");
        }
    }
    private void validaIndirizzoCompleto (String indirizzo) throws ValidazioneException {
        if (indirizzo == null || indirizzo.trim().isEmpty()) {
            throw new ValidazioneException("Il campo indirizzo è obbligatorio");
        }
        if (!indirizzo.matches(".+,\\s*\\d+")) {
            throw new ValidazioneException("L'indirizzo deve contenere una virgola seguita dal civico (es. Via Roma, 12)");
        }
    }
}

