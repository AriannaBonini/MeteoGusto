package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ConvertitoreRistorante {

    private ConvertitoreRistorante() { /* COSTRUTTORE VUOTO */ }

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

        List<AmbienteBean> ambienteBean = new ArrayList<>();
        List<Ambiente> ambienti = ristoranteModel.getAmbienteRistorante();
        if (ambienti != null) {
            for (Ambiente ambiente : ambienti) {
                if (ambiente != null) {
                    ambienteBean.add(ConvertitoreAmbiente.ambienteModelInBean(ambiente));
                }
            }
        }

        Persona proprietario = ristoranteModel.getProprietario();
        PersonaBean proprietarioBean = (proprietario != null)
                ? ConvertitorePersona.personaModelInBean(proprietario)
                : null;

        RistoranteBean ristoranteBean = new RistoranteBean(
                ristoranteModel.getPartitaIVA(),
                ristoranteModel.getNomeRistorante(),
                ristoranteModel.getTelefonoRistorante(),
                ristoranteModel.getCucina(),
                ristoranteModel.getFasciaPrezzo(),
                posizioneBean,
                giorniEOrariBean,
                ambienteBean,
                proprietarioBean
        );

        ristoranteBean.setTipoDieta(ristoranteModel.getTipoDieta());
        ristoranteBean.setMediaStelle(ristoranteModel.getMediaStelle());

        return ristoranteBean;
    }

    public static Ristorante ristoranteBeanInModel(RistoranteBean ristoranteBean) {
        if (ristoranteBean == null) return null;

        Posizione posizione = convertPosizioneBean(ristoranteBean.getPosizione());
        GiorniEOrari orari = convertOrariBean(ristoranteBean.getGiorniEOrari());

        Ristorante ristorante = costruiamoBaseRistorante(ristoranteBean, posizione, orari);

        PersonaBean proprietarioBean = ristoranteBean.getProprietario();
        if (proprietarioBean != null) {
            ristorante.setProprietario(ConvertitorePersona.personaBeanInModel(proprietarioBean));
        }

        return ristorante;
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
