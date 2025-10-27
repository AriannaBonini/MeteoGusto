package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.*;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

public class ConvertitoreBeanModel {

    private ConvertitoreBeanModel(){/* COSTRUTTORE SENZA PARAMETRI*/}
    public static Dieta dietaBeanInModel(DietaBean bean,Persona proprietario) {
        if (bean == null) return null;

        Ristorante ristorante = ristoranteBeanInModel(bean.getRistorante(), proprietario);

        return new Dieta(
                ristorante,
                bean.getDieta() != null ? new HashSet<>(bean.getDieta()) : new HashSet<>()
        );
    }


    public static GiorniChiusura giorniChiusuraBeanInModel(GiorniChiusuraBean bean, Persona proprietario) {
        if (bean == null) return null;

        Ristorante ristorante = ristoranteBeanInModel(bean.getRistorante(), proprietario);

        return new GiorniChiusura(
                ristorante,
                bean.getGiorniChiusura() != null ? new HashSet<>(bean.getGiorniChiusura()) : new HashSet<>()
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

    public static Persona personaBeanInModel(PersonaBean personaBean) {
        if (personaBean == null) return null;

        return new Persona(
                personaBean.getNome(),
                personaBean.getCognome(),
                personaBean.getTelefono(),
                personaBean.getEmail(),
                personaBean.getPassword(),
                personaBean.getTipoPersona()
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

    public static AmbienteDisponibile disponibilitaBeanInModel(AmbienteDisponibileBean ambienteDisponibileBean, Persona proprietario) {
        if (ambienteDisponibileBean == null) return null;

        Ristorante ristorante = ristoranteBeanInModel(ambienteDisponibileBean.getRistorante(), proprietario);

        AmbienteSpecialeDisponibile ambienteSpeciale = ambienteDisponibileBean.getAmbienteSpecialeDisponibile() != null
                ? ambienteSpecialeBeanInModel(ambienteDisponibileBean.getAmbienteSpecialeDisponibile())
                : null;

        EnumMap<TipoAmbiente, Integer> ambienteDisponibile = new EnumMap<>(TipoAmbiente.class);
        Map<TipoAmbiente, Integer> sorgente = ambienteDisponibileBean.getAmbienteDisponibile();

        if (sorgente != null) {
            ambienteDisponibile.putAll(sorgente);
        }

        return new AmbienteDisponibile(
                ambienteDisponibile,
                ristorante,
                ambienteSpeciale
        );
    }


    public static AmbienteSpecialeDisponibile ambienteSpecialeBeanInModel(AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibileBean) {
        if (ambienteSpecialeDisponibileBean == null) return null;

        return new AmbienteSpecialeDisponibile(
                ambienteSpecialeDisponibileBean.getExtra() != null ? new HashSet<>(ambienteSpecialeDisponibileBean.getExtra()) : new HashSet<>(),
                ambienteSpecialeDisponibileBean.getTipoAmbienteConExtra(),
                ambienteSpecialeDisponibileBean.getNumeroCoperti()
        );
    }

}
