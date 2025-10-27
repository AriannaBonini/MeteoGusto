package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoAmbienteConExtra;
import com.example.meteo_gusto.model.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConvertitoreModelBean {

    private ConvertitoreModelBean() {/* COSTRUTTORE SENZA PARAMETRI*/}

    public static DietaBean dietaModelInBean(Dieta model) throws ValidazioneException {
        if (model == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(model.getRistorante());

        return new DietaBean(
                ristoranteBean,
                model.getDieta() != null ? new HashSet<>(model.getDieta()) : new HashSet<>()
        );
    }


    public static GiorniChiusuraBean giorniChiusuraModelInBean(GiorniChiusura model) throws ValidazioneException {
        if (model == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(model.getRistorante());

        return new GiorniChiusuraBean(
                ristoranteBean,
                model.getGiorniChiusura() != null ? new HashSet<>(model.getGiorniChiusura()) : new HashSet<>()
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

    public static AmbienteDisponibileBean disponibilitaModelInBean(AmbienteDisponibile ambienteDisponibile) throws ValidazioneException {
        if (ambienteDisponibile == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(ambienteDisponibile.getRistorante());
        Map<TipoAmbiente, Integer> ambienteDisponibileMap = ambienteDisponibile.getAmbienteDisponibile() != null
                ? new HashMap<>(ambienteDisponibile.getAmbienteDisponibile())
                : new HashMap<>();
        AmbienteSpecialeDisponibileBean ambienteSpecialeBean = ambienteDisponibile.getAmbienteSpecialeDisponibile() != null
                ? ambienteSpecialeModelInBean(ambienteDisponibile.getAmbienteSpecialeDisponibile())
                : null;

        return new AmbienteDisponibileBean(
                ristoranteBean,
                ambienteDisponibileMap,
                ambienteSpecialeBean
        );
    }


    public static AmbienteSpecialeDisponibileBean ambienteSpecialeModelInBean(AmbienteSpecialeDisponibile model) throws ValidazioneException {
        if (model == null) return null;

        Set<Extra> extra = model.getExtra() != null ? new HashSet<>(model.getExtra()) : new HashSet<>();
        TipoAmbienteConExtra tipoAmbienteConExtra = model.getTipoAmbienteConExtra();
        Integer numeroCoperti = model.getNumeroCoperti();

        return new AmbienteSpecialeDisponibileBean(extra, tipoAmbienteConExtra, numeroCoperti);
    }


}

