package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.*;

import java.util.HashSet;

public class ConvertitoreBeanModel {

    private ConvertitoreBeanModel(){/* COSTRUTTORE SENZA PARAMETRI*/}
    public static Dieta dietaBeanInModel(DietaBean dieta, Persona proprietario) {
        if (dieta == null) return null;

        Ristorante ristorante = ristoranteBeanInModel(dieta.getRistorante(),proprietario);

        return new Dieta(
                ristorante,
                dieta.getDieta() != null ? new HashSet<>(dieta.getDieta()) : new HashSet<>()
        );
    }

    public static GiorniChiusura giorniChiusuraBeanInModel(GiorniChiusuraBean giorniChiusuraBean, Persona proprietario) {
        if (giorniChiusuraBean == null) return null;

        Ristorante ristorante = ristoranteBeanInModel(giorniChiusuraBean.getRistorante(),proprietario);

        return new GiorniChiusura(
                ristorante,
                giorniChiusuraBean.getGiorniChiusura() != null ? new HashSet<>(giorniChiusuraBean.getGiorniChiusura()) : new HashSet<>()
        );
    }

    public static OffertaCulinaria offertaCulinariaBeanInModel(OffertaCulinariaBean offertaCulinariaBean) {
        if (offertaCulinariaBean == null) return null;

        return new OffertaCulinaria(
                offertaCulinariaBean.getCucina(),
                offertaCulinariaBean.getFasciaPrezzo()
        );
    }

    public static Orari orariBeanInModel(OrariBean orariBean) {
        if (orariBean == null) return null;

        return new Orari(
                orariBean.getInizioPranzo(),
                orariBean.getFinePranzo(),
                orariBean.getInizioCena(),
                orariBean.getFineCena()
        );
    }

    public static Persona personaBeanInModel(PersonaBean personaBean, TipoPersona tipoPersona) {
        if (personaBean == null) return null;

        return new Persona(
                personaBean.getNome(),
                personaBean.getCognome(),
                personaBean.getTelefono(),
                personaBean.getEmail(),
                personaBean.getPassword(),
                tipoPersona
        );
    }

    public static Posizione posizioneBeanInModel(PosizioneBean posizioneBean) {
        if (posizioneBean == null) return null;

        return new Posizione(
                posizioneBean.getIndirizzoCompleto(),
                posizioneBean.getCitta(),
                posizioneBean.getCap()
        );
    }

    public static Ristorante ristoranteBeanInModel(RistoranteBean ristoranteBean, Persona proprietario) {
        if (ristoranteBean == null) return null;

        return new Ristorante(
                ristoranteBean.getPartitaIVA(),
                proprietario,
                ristoranteBean.getNome(),
                ristoranteBean.getTelefono(),
                orariBeanInModel(ristoranteBean.getOrari()),
                offertaCulinariaBeanInModel(ristoranteBean.getOffertaCulinaria()),
                posizioneBeanInModel(ristoranteBean.getPosizioneRistorante())
        );
    }


}
