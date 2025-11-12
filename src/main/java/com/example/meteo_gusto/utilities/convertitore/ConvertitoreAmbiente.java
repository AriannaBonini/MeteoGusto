package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.model.Ambiente;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConvertitoreAmbiente {

    private ConvertitoreAmbiente(){ /* COSTRUTTORE VUOTO */ }


    public static List<Ambiente> listaAmbienteBeanInModel(List<AmbienteBean> listaAmbienteBean) {
        if (listaAmbienteBean == null) return new ArrayList<>();
        return listaAmbienteBean.stream()
                .filter(Objects::nonNull)
                .map(ConvertitoreAmbiente::ambienteBeanInModel)
                .toList();
    }


    public static Ambiente ambienteBeanInModel(AmbienteBean ambienteBean) {
        if (ambienteBean == null) return null;

        Ambiente ambiente = new Ambiente(
                ambienteBean.getAmbiente(),
                ambienteBean.getRistorante(),
                ambienteBean.getNumeroCoperti(),
                ambienteBean.getExtra());

        ambiente.setIdAmbiente(ambienteBean.getAmbienteId());
        return ambiente;
    }



    public static List<AmbienteBean> listaAmbienteModelInBean(List<Ambiente> listaAmbienteModel) {
        if (listaAmbienteModel == null) return new ArrayList<>();
        return listaAmbienteModel.stream()
                .filter(Objects::nonNull)
                .map(ConvertitoreAmbiente::ambienteModelInBean)
                .toList();
    }


    public static AmbienteBean ambienteModelInBean(Ambiente ambienteModel) throws NumberFormatException{
        if (ambienteModel == null) return null;

        AmbienteBean ambienteBean= new AmbienteBean(
                ambienteModel.getTipoAmbiente(),
                ambienteModel.getNumeroCoperti(),
                ambienteModel.getExtra(),
                ambienteModel.getRistorante());

        ambienteBean.setAmbienteId(ambienteModel.getIdAmbiente());
        return ambienteBean;
    }

}
