package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.model.Filtro;

public class ConvertitoreFiltri {

    private ConvertitoreFiltri(){ /* COSTRUTTORE VUOTO */ }

    public static Filtro filtriBeanInModel(FiltriBean filtroBean)  {
        Filtro filtro= new  Filtro(
                filtroBean.getOra(),
                filtroBean.getCitta(),
                filtroBean.getFasciaPrezzoRistorante(),
                filtroBean.getTipoCucina(),
                filtroBean.getTipoDieta(),
                filtroBean.getMeteo());

        filtro.setData(filtroBean.getData());
        filtro.setNumeroPersone(filtroBean.getNumeroPersone());

        return filtro;
    }

}
