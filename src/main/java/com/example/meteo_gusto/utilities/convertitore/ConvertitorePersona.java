package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Persona;

public class ConvertitorePersona {


    private ConvertitorePersona(){ /* COSTRUTTORE VUOTO */ }

    public static Persona personaBeanInModel(PersonaBean personaBean) {
        if (personaBean == null) return null;
        return new Persona(
                personaBean.getNome(),
                personaBean.getCognome(),
                personaBean.getTelefono(),
                personaBean.getEmail(),
                personaBean.getPassword(),
                personaBean.getTipoPersona(),
                ConvertitoreRistorante.ristoranteBeanInModel(personaBean.getRistoranteBean())
        );
    }

    public static PersonaBean personaModelInBean(Persona personaModel) {
        if (personaModel == null) return null;
        return new PersonaBean(personaModel.getNome(),
                personaModel.getCognome(),
                personaModel.getTelefono(),
                personaModel.getEmail(),
                personaModel.getPassword(),
                personaModel.getTipoPersona(),
                ConvertitoreRistorante.ristoranteModelInBean(personaModel.getRistorante())
        );
    }

}
