package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.*;

import java.util.HashSet;

public class ConvertitoreModelBean {

    private ConvertitoreModelBean() {/* COSTRUTTORE SENZA PARAMETRI*/}

    public static DietaBean dietaModelInBean(Dieta dieta) throws ValidazioneException {
        if (dieta == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(dieta.getRistorante());

        return new DietaBean(
                ristoranteBean,
                dieta.getDieta() != null ? new HashSet<>(dieta.getDieta()) : new HashSet<>()
        );
    }

    public static GiorniChiusuraBean giorniChiusuraModelInBean(GiorniChiusura giorniChiusura) throws ValidazioneException {
        if (giorniChiusura == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(giorniChiusura.getRistorante());

        return new GiorniChiusuraBean(
                ristoranteBean,
                giorniChiusura.getGiorniChiusura() != null ? new HashSet<>(giorniChiusura.getGiorniChiusura()) : new HashSet<>()
        );
    }

    public static OffertaCulinariaBean offertaCulinariaModelInBean(OffertaCulinaria offertaCulinaria) throws ValidazioneException {
        if (offertaCulinaria == null) return null;

        return new OffertaCulinariaBean(
                offertaCulinaria.getCucina(),
                offertaCulinaria.getFasciaPrezzo()
        );
    }

    public static OrariBean orariModelInBean(Orari orari) throws ValidazioneException {
        if (orari == null) return null;

        return new OrariBean(
                orari.getInizioPranzo(),
                orari.getFinePranzo(),
                orari.getInizioCena(),
                orari.getFineCena()
        );
    }

    public static PersonaBean personaModelInBean(Persona persona) throws ValidazioneException {
        if (persona == null) return null;

        return new PersonaBean(
                persona.getNome(),
                persona.getCognome(),
                persona.getTelefono(),
                persona.getEmail(),
                persona.getPassword()
        );
    }

    public static PosizioneBean posizioneModelInBean(Posizione posizione) throws ValidazioneException {
        if (posizione == null) return null;

        return new PosizioneBean(
                posizione.getIndirizzoCompleto(),
                posizione.getCitta(),
                posizione.getCap()
        );
    }

    public static RistoranteBean ristoranteModelInBean(Ristorante ristorante) throws ValidazioneException {
        if (ristorante == null) return null;

        return new RistoranteBean(
                ristorante.getPartitaIVA(),
                ristorante.getNome(),
                ristorante.getTelefono(),
                orariModelInBean(ristorante.getOrari()),
                offertaCulinariaModelInBean(ristorante.getOffertaCulinaria()),
                posizioneModelInBean(ristorante.getPosizione())
        );
    }
}

