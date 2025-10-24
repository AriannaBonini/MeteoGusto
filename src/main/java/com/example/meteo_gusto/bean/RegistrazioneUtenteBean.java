package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class RegistrazioneUtenteBean {
    private PersonaBean utente;
    private final boolean maggiorenne;
    private final boolean accettaTermini;

    /* COSTRUTTORE CON PARAMETRI */
    public RegistrazioneUtenteBean(PersonaBean utente, boolean maggiorenne, boolean accettaTermini) throws ValidazioneException {
        validaCondizioni(maggiorenne, accettaTermini);

        this.utente=utente;
        this.maggiorenne = maggiorenne;
        this.accettaTermini = accettaTermini;
    }

    /* GETTER - SOLO LETTURA */
    public PersonaBean getUtente() {return utente;}
    public void setUtente(PersonaBean utente) {this.utente = utente;}


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCondizioni(boolean maggiorenne, boolean accettaTermini) throws ValidazioneException {
        if (!maggiorenne) {
            throw new ValidazioneException("È necessario essere maggiorenni per registrarsi");
        }
        if (!accettaTermini) {
            throw new ValidazioneException("È necessario accettare i termini e condizioni");
        }
    }
}