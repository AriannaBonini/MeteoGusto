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

        DietaBean dietaBean= new DietaBean();
        dietaBean.setRistorante(ristoranteBean);
        dietaBean.setDieta(model.getDieta() != null ? new HashSet<>(model.getDieta()) : new HashSet<>());

       return dietaBean;
    }


    public static GiorniChiusuraBean giorniChiusuraModelInBean(GiorniChiusura model) throws ValidazioneException {
        if (model == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(model.getRistorante());

        GiorniChiusuraBean giorniChiusuraBean= new GiorniChiusuraBean();
        giorniChiusuraBean.setRistorante(ristoranteBean);
        giorniChiusuraBean.setGiorniChiusura(model.getGiorniChiusura() != null ? new HashSet<>(model.getGiorniChiusura()) : new HashSet<>());

        return giorniChiusuraBean;
    }

    public static OffertaCulinariaBean offertaCulinariaModelInBean(OffertaCulinaria offertaCulinaria) throws ValidazioneException {
        if (offertaCulinaria == null) return null;

        OffertaCulinariaBean offertaCulinariaBean= new OffertaCulinariaBean();
        offertaCulinariaBean.setCucina(offertaCulinaria.getCucina());
        offertaCulinariaBean.setFasciaPrezzo(offertaCulinaria.getFasciaPrezzo());

        return offertaCulinariaBean;

    }

    public static OrariBean orariModelInBean(Orari orari) throws ValidazioneException {
        if (orari == null) return null;

        OrariBean orariBean= new OrariBean();
        orariBean.setInizioPranzo(orari.getInizioPranzo());
        orariBean.setFinePranzo(orari.getFinePranzo());
        orariBean.setInizioCena(orari.getInizioCena());
        orariBean.setFineCena(orari.getFineCena());

        return orariBean;
    }

    public static PersonaBean personaModelInBean(Persona persona) throws ValidazioneException {
        if (persona == null) return null;

        PersonaBean personaBean= new PersonaBean();
        personaBean.setNome(persona.getNome());
        personaBean.setCognome(persona.getCognome());
        personaBean.setTelefono(persona.getTelefono());
        personaBean.setEmail(persona.getEmail());
        personaBean.setPassword(persona.getPassword());
        personaBean.setTipoPersona(persona.getTipoPersona());

        return personaBean;
    }

    public static PosizioneBean posizioneModelInBean(Posizione posizione) throws ValidazioneException {
        if (posizione == null) return null;

        PosizioneBean posizioneBean= new PosizioneBean();
        posizioneBean.setIndirizzoCompleto(posizioneBean.getIndirizzoCompleto());
        posizioneBean.setCitta(posizione.getCitta());
        posizioneBean.setCap(posizione.getCap());

        return posizioneBean;
    }

    public static RistoranteBean ristoranteModelInBean(Ristorante ristorante) throws ValidazioneException {
        if (ristorante == null) return null;

        RistoranteBean ristoranteBean= new RistoranteBean();
        ristoranteBean.setPartitaIVA(ristorante.getPartitaIVA());
        ristoranteBean.setNome(ristorante.getNome());
        ristoranteBean.setTelefono(ristorante.getTelefono());
        ristoranteBean.setOrari(orariModelInBean(ristorante.getOrari()));
        ristoranteBean.setOffertaCulinaria(offertaCulinariaModelInBean(ristorante.getOffertaCulinaria()));
        ristoranteBean.setPosizioneRistorante(posizioneModelInBean(ristorante.getPosizione()));

        return ristoranteBean;
    }

    public static AmbienteDisponibileBean disponibilitaModelInBean(AmbienteDisponibile ambienteDisponibile) throws ValidazioneException {
        if (ambienteDisponibile == null) return null;

        RistoranteBean ristoranteBean = ristoranteModelInBean(ambienteDisponibile.getRistorante());
        Map<TipoAmbiente, Integer> ambienteDisponibileMap = ambienteDisponibile.getAmbienteECoperti() != null
                ? new HashMap<>(ambienteDisponibile.getAmbienteECoperti())
                : new HashMap<>();
        AmbienteSpecialeDisponibileBean ambienteSpecialeBean = ambienteDisponibile.getAmbienteSpecialeDisponibile() != null
                ? ambienteSpecialeModelInBean(ambienteDisponibile.getAmbienteSpecialeDisponibile())
                : null;

        AmbienteDisponibileBean ambienteDisponibileBean= new AmbienteDisponibileBean();
        ambienteDisponibileBean.setRistorante(ristoranteBean);
        ambienteDisponibileBean.setAmbienteDisponibile(ambienteDisponibileMap);
        ambienteDisponibileBean.setAmbienteSpecialeDisponibile(ambienteSpecialeBean);

        return ambienteDisponibileBean;

    }


    public static AmbienteSpecialeDisponibileBean ambienteSpecialeModelInBean(AmbienteSpecialeDisponibile model) throws ValidazioneException {
        if (model == null) return null;

        Set<Extra> extra = model.getExtra() != null ? new HashSet<>(model.getExtra()) : new HashSet<>();
        TipoAmbienteConExtra tipoAmbienteConExtra = model.getTipoAmbienteConExtra();
        Integer numeroCoperti = model.getNumeroCoperti();

        AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibileBean= new AmbienteSpecialeDisponibileBean();
        ambienteSpecialeDisponibileBean.setExtra(extra);
        ambienteSpecialeDisponibileBean.setTipoAmbienteConExtra(tipoAmbienteConExtra);
        ambienteSpecialeDisponibileBean.setNumeroCoperti(numeroCoperti);

        return ambienteSpecialeDisponibileBean;
    }

}

