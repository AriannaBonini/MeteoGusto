package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.model.Filtro;

public class ConvertitoreFiltri {

    private ConvertitoreFiltri(){ /* COSTRUTTORE VUOTO */ }

    public static Filtro filtriBeanInModel(FiltriBean filtroBean) {
        return new Filtro(filtroBean.getData(),
                filtroBean.getOra(),
                filtroBean.getCitta(),
                filtroBean.getNumeroPersone());
    }

    public static FiltriBean filtriModelInBean(Filtro filtroModel)  {
        FiltriBean bean = new FiltriBean();
        bean.setData(filtroModel.getData());
        bean.setOra(filtroModel.getOra());
        bean.setCitta(filtroModel.getCitta());
        bean.setNumeroPersone(filtroModel.getNumeroPersone());
        return bean;
    }
}
