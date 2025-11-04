package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.DietaBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Dieta;
import com.example.meteo_gusto.model.Ristorante;

public class ConvertitoreDieta {

    private ConvertitoreDieta(){ /* COSTRUTTORE VUOTO */ }

    public static Dieta dietaBeanInModel(DietaBean dietaBean)  {
        if (dietaBean == null) return null;

        Ristorante ristoranteModel = ConvertitoreRistorante.ristoranteBeanInModel(dietaBean.getRistorante());

        return new Dieta(ristoranteModel, dietaBean.getDieta());
    }

    public static DietaBean dietaModelInBean(Dieta dietaModel) throws ValidazioneException {
        if (dietaModel == null) return null;

        DietaBean dietaBean= new DietaBean();
        dietaBean.setDieta(dietaModel.getTipoDieta());
        dietaBean.setRistorante(ConvertitoreRistorante.ristoranteModelInBean(dietaModel.getRistorante()));

        return dietaBean;
    }



}
