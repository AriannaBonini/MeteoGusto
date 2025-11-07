package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.GiorniEOrariBean;
import com.example.meteo_gusto.bean.PosizioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Posizione;
import com.example.meteo_gusto.model.Ristorante;

import java.util.ArrayList;
import java.util.List;

public class ConvertitoreRistorante {

    private ConvertitoreRistorante(){ /* COSTRUTTORE VUOTO */ }


    public static RistoranteBean ristoranteModelInBean(Ristorante ristoranteModel)  {
        if (ristoranteModel == null) return null;
        return costruisciRistoranteBean(ristoranteModel);
    }

    private static RistoranteBean costruisciRistoranteBean(Ristorante ristoranteModel) {
        PosizioneBean posizioneBean = new PosizioneBean(
                ristoranteModel.getPosizione().getIndirizzoCompleto(),
                ristoranteModel.getPosizione().getCap(),
                ristoranteModel.getPosizione().getCitta()
        );


        GiorniEOrariBean giorniEOrariBean = new GiorniEOrariBean(
                ristoranteModel.getOrari().getInizioPranzo(),
                ristoranteModel.getOrari().getFinePranzo(),
                ristoranteModel.getOrari().getInizioCena(),
                ristoranteModel.getOrari().getFineCena(),
                ristoranteModel.getOrari().getGiorniChiusura()
        );



        List<AmbienteBean> ambienteBean = new ArrayList<>();
        for (Ambiente ambiente : ristoranteModel.getAmbienteRistorante()) {
            ambienteBean.add(ConvertitoreAmbiente.ambienteModelInBean(ambiente));
        }

        RistoranteBean ristoranteBean = new RistoranteBean(
                ristoranteModel.getPartitaIVA(),
                ristoranteModel.getNomeRistorante(),
                ristoranteModel.getTelefonoRistorante(),
                ristoranteModel.getCucina(),
                ristoranteModel.getFasciaPrezzo(),
                posizioneBean,
                giorniEOrariBean,
                ambienteBean,
                ConvertitorePersona.personaModelInBean(ristoranteModel.getProprietario())
        );


        ristoranteBean.setTipoDieta(ristoranteModel.getTipoDieta());

        return ristoranteBean;
    }


    public static Ristorante ristoranteBeanInModel(RistoranteBean ristoranteBean) {
        if (ristoranteBean == null) return null;

        Ristorante ristorante= new  Ristorante(ristoranteBean.getPartitaIVA(),
                ristoranteBean.getNomeRistorante(),
                ristoranteBean.getTelefonoRistorante(),
                ristoranteBean.getCucina(),
                ristoranteBean.getFasciaPrezzo(),
                new Posizione(ristoranteBean.getPosizione().getIndirizzoCompleto(), ristoranteBean.getPosizione().getCitta(), ristoranteBean.getPosizione().getCap()),
                new GiorniEOrari(ristoranteBean.getGiorniEOrari().getInizioPranzo(), ristoranteBean.getGiorniEOrari().getFinePranzo(), ristoranteBean.getGiorniEOrari().getInizioCena(), ristoranteBean.getGiorniEOrari().getFineCena(),ristoranteBean.getGiorniEOrari().getGiorniChiusura())
        );

        ristorante.setMediaStelle(ristoranteBean.getMediaStelle());
        ristorante.setProprietario(ConvertitorePersona.personaBeanInModel(ristoranteBean.getProprietario()));

        return ristorante;
    }


}
