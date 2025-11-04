package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Ristorante;

public class ConvertitoreRistorante {

    private ConvertitoreRistorante(){ /* COSTRUTTORE VUOTO */ }


    public static RistoranteBean ristoranteModelInBean(Ristorante ristoranteModel) throws ValidazioneException {
        if (ristoranteModel == null) return null;

        RistoranteBean ristoranteBean = new RistoranteBean();
        ristoranteBean.setPartitaIVA(ristoranteModel.getPartitaIVA());

        ristoranteBean.setNomeRistorante(ristoranteModel.getNomeRistorante());
        ristoranteBean.setTelefonoRistorante(ristoranteModel.getTelefonoRistorante());
        ristoranteBean.setCucina(ristoranteModel.getCucina());
        ristoranteBean.setFasciaPrezzo(ristoranteModel.getFasciaPrezzo());
        ristoranteBean.setMediaStelle(ristoranteModel.getMediaStelle());

        ristoranteBean.setProprietario(ConvertitorePersona.personaModelInBean(ristoranteModel.getProprietario()));
        ristoranteBean.setIndirizzoCompleto(ristoranteModel.getIndirizzoCompleto());
        ristoranteBean.setCitta(ristoranteModel.getCitta());
        ristoranteBean.setCap(ristoranteModel.getCap());
        ristoranteBean.setInizioPranzo(ristoranteModel.getInizioPranzo());
        ristoranteBean.setFinePranzo(ristoranteModel.getFinePranzo());
        ristoranteBean.setInizioCena(ristoranteModel.getInizioCena());
        ristoranteBean.setFineCena(ristoranteModel.getFineCena());

        return ristoranteBean;
    }

    public static Ristorante ristoranteBeanInModel(RistoranteBean ristoranteBean) {
        if (ristoranteBean == null) return null;

        Persona proprietarioModel = ConvertitorePersona.personaBeanInModel(ristoranteBean.getProprietario());
        Ristorante ristorante= new  Ristorante(ristoranteBean.getPartitaIVA(),
                proprietarioModel,
                ristoranteBean.getNomeRistorante(),
                ristoranteBean.getTelefonoRistorante(),
                ristoranteBean.getCucina(),
                ristoranteBean.getFasciaPrezzo(),
                ristoranteBean.getIndirizzoCompleto(),
                ristoranteBean.getCitta(),
                ristoranteBean.getCap(),
                ristoranteBean.getMediaStelle()
        );

        ristorante.setInizioPranzo(ristoranteBean.getInizioPranzo());
        ristorante.setFinePranzo(ristoranteBean.getFinePranzo());
        ristorante.setInizioCena(ristoranteBean.getInizioCena());
        ristorante.setFineCena(ristoranteBean.getFineCena());

        return ristorante;
    }


}
