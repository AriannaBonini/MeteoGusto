package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.GiornoChiusuraBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.GiornoChiusura;
import com.example.meteo_gusto.model.Ristorante;

public class ConvertitoreGiorniChiusura {

    private ConvertitoreGiorniChiusura(){ /* COSTRUTTORE VUOTO */ }


    public static GiornoChiusura giorniChiusuraBeanInModel(GiornoChiusuraBean bean)  {
        if (bean == null) return null;

        Ristorante ristoranteModel = ConvertitoreRistorante.ristoranteBeanInModel(bean.getRistorante());

        return new GiornoChiusura(ristoranteModel, bean.getGiornoChiusura());
    }

    public static GiornoChiusuraBean giorniChiusuraModelInBean(GiornoChiusura giornoChiusuraModel) throws ValidazioneException {
        if (giornoChiusuraModel == null) return null;

        GiornoChiusuraBean giornoChiusuraBean = new GiornoChiusuraBean();
        giornoChiusuraBean.setRistorante(ConvertitoreRistorante.ristoranteModelInBean(giornoChiusuraModel.getRistorante()));
        giornoChiusuraBean.setGiornoChiusura(giornoChiusuraModel.getNomeGiornoChiusura());

        return giornoChiusuraBean;
    }
}
