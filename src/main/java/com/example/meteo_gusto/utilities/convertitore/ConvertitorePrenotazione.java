package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

public class ConvertitorePrenotazione {

    private ConvertitorePrenotazione(){ /* COSTRUTTORE VUOTO */ }

    public static Prenotazione prenotazioneBeanInModel(PrenotazioneBean prenotazioneBean) {
        if (prenotazioneBean == null) return null;

        Persona utenteModel = ConvertitorePersona.personaBeanInModel(prenotazioneBean.getUtente());
        Ambiente ambienteModel = ConvertitoreAmbiente.ambienteBeanInModel(prenotazioneBean.getAmbiente());

        return new Prenotazione(
                prenotazioneBean.getData(),
                prenotazioneBean.getOra(),
                prenotazioneBean.getNumeroPersone(),
                ambienteModel,
                utenteModel,
                prenotazioneBean.getFasciaOraria()
        );
    }


    public static PrenotazioneBean prenotazioneModelInBean(Prenotazione prenotazioneModel) throws ValidazioneException{
        if (prenotazioneModel == null) return null;

        PersonaBean utenteBean = ConvertitorePersona.personaModelInBean(prenotazioneModel.getUtente());
        AmbienteBean ambienteBean = ConvertitoreAmbiente.ambienteModelInBean(prenotazioneModel.getAmbiente());

        return new PrenotazioneBean(
                prenotazioneModel.getOra(),
                prenotazioneModel.getData(),
                prenotazioneModel.getNumeroPersone(),
                utenteBean,
                ambienteBean,
                prenotazioneModel.getFasciaOraria()
        );
    }



}
