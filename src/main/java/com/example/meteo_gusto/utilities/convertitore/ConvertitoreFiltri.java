package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Filtro;

public class ConvertitoreFiltri {

    private ConvertitoreFiltri(){ /* COSTRUTTORE VUOTO */ }

    public static Filtro filtriBeanInModel(FiltriBean filtroBean) throws ValidazioneException {
        Filtro filtro= new  Filtro(
                filtroBean.getOra(),
                filtroBean.getCitta(),
                filtroBean.getFasciaPrezzoRistorante(),
                filtroBean.getTipoCucina(),
                filtroBean.getTipoDieta(),
                filtroBean.getMeteo());

        filtro.aggiungiData(filtroBean.getData());
        filtro.aggiungiNumeroPersone(filtroBean.getNumeroPersone());

        return filtro;
    }

}
