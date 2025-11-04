package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Ambiente;

public class ConvertitoreAmbiente {

    private ConvertitoreAmbiente(){ /* COSTRUTTORE VUOTO */ }

    public static Ambiente ambienteBeanInModel(AmbienteBean ambienteBean) {
        if (ambienteBean == null) return null;

        return new Ambiente(
                ambienteBean.getAmbiente(),
                ConvertitoreRistorante.ristoranteBeanInModel(ambienteBean.getRistorante()),
                ambienteBean.getNumeroCoperti(),
                ambienteBean.getExtra());

    }


    public static AmbienteBean ambienteModelInBean(Ambiente ambienteModel) throws ValidazioneException, NumberFormatException{
        if (ambienteModel == null) return null;

        return new AmbienteBean(ambienteModel.getTipoAmbiente(),
                ambienteModel.getNumeroCoperti(),
                ambienteModel.getExtra(),
                ConvertitoreRistorante.ristoranteModelInBean(ambienteModel.getRistorante()));

    }

}
