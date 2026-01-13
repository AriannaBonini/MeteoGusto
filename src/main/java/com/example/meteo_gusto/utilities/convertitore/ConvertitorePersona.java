package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Ristorante;

public class ConvertitorePersona {

    private ConvertitorePersona(){ /* COSTRUTTORE VUOTO */ }

    /**
     * Attributi gestiti da questo convertitore : nome, cognome, telefono
     */
    public static PersonaBean dettagliUtentePrenotazioneInBean(Persona persona) throws ValidazioneException {
        PersonaBean personaBean= new PersonaBean();

        personaBean.setNome(persona.getNome());
        personaBean.setCognome(persona.getCognome());
        personaBean.setTelefono(persona.numeroTelefonico());

        return personaBean;
    }


    /**
     * Attributi gestiti da questo convertitore : nome, cognome, telefono, email, password
     */
    public static Persona registrazioneUtenteInModel(PersonaBean personaBean) {
        return new Persona(
                personaBean.getNome(),
                personaBean.getCognome(),
                personaBean.getTelefono(),
                personaBean.getEmail(),
                personaBean.getPassword()
        );
    }

    /**
     * Attributi gestiti da questo convertitore : nome, cognome, telefono, email, password, ristorante
     */
    public static Persona registrazioneRistoranteInModel(PersonaBean personaBean) {
        Ristorante ristorante= ConvertitoreRistorante.registrazioneRistoranteInModel(personaBean.getRistoranteBean());

        return new Persona(
                personaBean.getNome(),
                personaBean.getCognome(),
                personaBean.getTelefono(),
                personaBean.getEmail(),
                personaBean.getPassword(),
                ristorante
        );
    }

    /**
     * Attributi gestiti da questo convertitore : email, password
     */
    public static Persona loginInModel(PersonaBean personaBean) {
        Persona persona= new Persona();

        persona.setEmail(personaBean.getEmail());
        persona.setPassword(personaBean.getPassword());

        return persona;
    }


}
