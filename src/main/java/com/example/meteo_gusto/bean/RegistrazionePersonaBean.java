package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class RegistrazionePersonaBean {

    private PersonaBean persona;
    private boolean maggiorenne;
    private boolean accettaTermini;

    public RegistrazionePersonaBean() {  /* COSTRUTTORE VUOTO */ }



    /* METODI SETTER CON VALIDAZIONE */
    public void setPersona(PersonaBean persona) throws ValidazioneException {
        if (persona == null) {
            throw new ValidazioneException("I dati personali sono obbligatori per la registrazione.");
        }
        this.persona = persona;
    }

    public void setMaggiorenne(boolean maggiorenne) throws ValidazioneException {
        if (!maggiorenne) {
            throw new ValidazioneException("È necessario essere maggiorenni per registrarsi.");
        }
        this.maggiorenne = maggiorenne;
    }

    public void setAccettaTermini(boolean accettaTermini) throws ValidazioneException {
        if (!accettaTermini) {
            throw new ValidazioneException("È necessario accettare i termini e le condizioni.");
        }
        this.accettaTermini = accettaTermini;
    }

    /* METODI GETTER */
    public PersonaBean getPersona() { return persona; }
}
