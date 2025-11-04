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
                personaBean.getTipoPersona()
        );
    }

    public static PersonaBean personaModelInBean(Persona personaModel) throws ValidazioneException {
        if (personaModel == null) return null;

        PersonaBean personaBean= new PersonaBean();
        personaBean.setEmail(personaModel.getEmail());
        personaBean.setNome(personaModel.getNome());
        personaBean.setCognome(personaModel.getCognome());
        personaBean.setTelefono(personaModel.getTelefono());
        personaBean.setPassword(personaModel.getPassword());
        personaBean.setTipoPersona(personaModel.getTipoPersona());

        return personaBean;
    }

}
