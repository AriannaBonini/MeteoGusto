package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.model.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ConvertitoreRistorante {

    private ConvertitoreRistorante() { /* COSTRUTTORE VUOTO */ }

    public static List<RistoranteBean> listaRistoranteModelInBean(List<Ristorante> listaRistorantiModel) {
        if (listaRistorantiModel == null) return List.of();
        return listaRistorantiModel.stream()
                .filter(Objects::nonNull)
                .map(ConvertitoreRistorante::ristoranteModelInBean)
                .toList();
    }


    public static RistoranteBean ristoranteModelInBean(Ristorante ristoranteModel) {
        if (ristoranteModel == null) return null;
        return costruisciRistoranteBean(ristoranteModel);
    }

    private static RistoranteBean costruisciRistoranteBean(Ristorante ristoranteModel) {

        Posizione posizione = ristoranteModel.getPosizione();
        PosizioneBean posizioneBean = (posizione != null)
                ? new PosizioneBean(
                posizione.getIndirizzoCompleto(),
                posizione.getCap(),
                posizione.getCitta()
        )
                : new PosizioneBean("Indirizzo non disponibile", "", "");

        GiorniEOrari orari = ristoranteModel.getOrari();
        GiorniEOrariBean giorniEOrariBean = (orari != null)
                ? new GiorniEOrariBean(
                orari.getInizioPranzo(),
                orari.getFinePranzo(),
                orari.getInizioCena(),
                orari.getFineCena(),
                orari.getGiorniChiusura()
        )
                : new GiorniEOrariBean(null, null, null, null, new HashSet<>());


        List<AmbienteBean> ambienteBean= ConvertitoreAmbiente.listaAmbienteModelInBean(ristoranteModel.getAmbienteRistorante());


        RistoranteBean ristoranteBean = new RistoranteBean(
                ristoranteModel.getPartitaIVA(),
                ristoranteModel.getNomeRistorante(),
                ristoranteModel.getTelefonoRistorante(),
                ristoranteModel.getCucina(),
                ristoranteModel.getFasciaPrezzo(),
                posizioneBean,
                giorniEOrariBean,
                ambienteBean
        );

        ristoranteBean.setTipoDieta(ristoranteModel.getTipoDieta());
        ristoranteBean.setMediaStelle(ristoranteModel.getMediaStelle());

        return ristoranteBean;
    }

    public static Ristorante ristoranteBeanInModel(RistoranteBean ristoranteBean) {
        if (ristoranteBean == null) return null;

        Posizione posizione = convertPosizioneBean(ristoranteBean.getPosizione());
        GiorniEOrari orari = convertOrariBean(ristoranteBean.getGiorniEOrari());

        return costruiamoBaseRistorante(ristoranteBean, posizione, orari);
    }

    private static Ristorante costruiamoBaseRistorante(RistoranteBean bean, Posizione posizione, GiorniEOrari orari) {
        Ristorante ristorante = new Ristorante(
                bean.getPartitaIVA(),
                bean.getNomeRistorante(),
                bean.getTelefonoRistorante(),
                bean.getCucina(),
                bean.getFasciaPrezzo(),
                posizione,
                orari
        );
        ristorante.setMediaStelle(bean.getMediaStelle());
        return ristorante;
    }

    private static Posizione convertPosizioneBean(PosizioneBean posizioneBean) {
        return (posizioneBean != null)
                ? new Posizione(
                posizioneBean.getIndirizzoCompleto(),
                posizioneBean.getCitta(),
                posizioneBean.getCap()
        )
                : new Posizione("Indirizzo non disponibile", "", "");
    }

    private static GiorniEOrari convertOrariBean(GiorniEOrariBean orariBean) {
        return (orariBean != null)
                ? new GiorniEOrari(
                orariBean.getInizioPranzo(),
                orariBean.getFinePranzo(),
                orariBean.getInizioCena(),
                orariBean.getFineCena(),
                orariBean.getGiorniChiusura()
        )
                : new GiorniEOrari(null, null, null, null, new HashSet<>());
    }

}
