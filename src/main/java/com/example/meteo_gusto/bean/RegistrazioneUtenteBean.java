package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class RegistrazioneUtenteBean {
    private PersonaBean persona;
    private final boolean maggiorenne;
    private final boolean accettaTermini;

    /* COSTRUTTORE CON PARAMETRI */
    public RegistrazioneUtenteBean(PersonaBean persona, boolean maggiorenne, boolean accettaTermini) throws ValidazioneException {
        validaCondizioni(maggiorenne, accettaTermini);

        this.persona = persona;
        this.maggiorenne = maggiorenne;
        this.accettaTermini = accettaTermini;
    }

    /* GETTER - SOLO LETTURA */
    public PersonaBean getPersona() {return persona;}
    public void setPersona(PersonaBean persona) {this.persona = persona;}


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