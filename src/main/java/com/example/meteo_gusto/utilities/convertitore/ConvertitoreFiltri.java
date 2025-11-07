package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.model.Filtro;

public class ConvertitoreFiltri {

    private ConvertitoreFiltri(){ /* COSTRUTTORE VUOTO */ }

    public static Filtro filtriBeanInModel(FiltriBean filtroBean) {
        return new Filtro(filtroBean.getData(),
                filtroBean.getOra(),
                filtroBean.getCitta(),
                filtroBean.getNumeroPersone(),
                filtroBean.getFasciaPrezzoRistorante(),
                filtroBean.getTipoCucina(),
                filtroBean.getTipoDieta(),
                filtroBean.getMeteo());

    }

    public static FiltriBean filtriModelInBean(Filtro filtroModel) {
        return new FiltriBean(filtroModel.getData(), filtroModel.getOra(), filtroModel.getCitta(),filtroModel.getNumeroPersone());
    }
}
