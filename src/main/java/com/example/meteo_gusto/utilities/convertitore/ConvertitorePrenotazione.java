package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

public class ConvertitorePrenotazione {

    private ConvertitorePrenotazione(){ /* COSTRUTTORE VUOTO */ }

    public static Prenotazione prenotazioneBeanInModel(PrenotazioneBean prenotazioneBean) {
        if (prenotazioneBean == null) return null;

        Persona utenteModel = ConvertitorePersona.personaBeanInModel(prenotazioneBean.getUtente());
        Ambiente ambienteModel = ConvertitoreAmbiente.ambienteBeanInModel(prenotazioneBean.getAmbiente());

        Prenotazione prenotazioneModel= new Prenotazione(
                prenotazioneBean.getData(),
                prenotazioneBean.getOra(),
                prenotazioneBean.getNumeroPersone(),
                ambienteModel,
                utenteModel,
                prenotazioneBean.getFasciaOraria()
        );

        prenotazioneModel.setNote(prenotazioneBean.getNote());
        return prenotazioneModel;
    }


    public static PrenotazioneBean prenotazioneModelInBean(Prenotazione prenotazioneModel) {
        if (prenotazioneModel == null) return null;

        PersonaBean utenteBean = ConvertitorePersona.personaModelInBean(prenotazioneModel.getUtente());
        AmbienteBean ambienteBean = ConvertitoreAmbiente.ambienteModelInBean(prenotazioneModel.getAmbiente());

        PrenotazioneBean prenotazioneBean= new PrenotazioneBean(
                prenotazioneModel.getOra(),
                prenotazioneModel.getData(),
                prenotazioneModel.getNumeroPersone(),
                utenteBean,
                ambienteBean,
                prenotazioneModel.getFasciaOraria()
        );

        prenotazioneBean.setNumeroNotifiche(prenotazioneModel.getNumeroNotifiche());
        prenotazioneBean.setNote(prenotazioneModel.getNote());
        return prenotazioneBean;
    }



}
