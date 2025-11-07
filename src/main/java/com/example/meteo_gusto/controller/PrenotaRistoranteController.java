package com.example.meteo_gusto.controller;


import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.MeteoBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.FasciaOraria;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.mockapi.BoundaryMeteoMockAPI;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.GiorniSettimanaHelper;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreFiltri;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRistorante;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class PrenotaRistoranteController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private static final DietaDAO dietaDAO= daoFactoryFacade.getDietaDAO();
    private final List<Ristorante> ristorantiPrenotabili = new ArrayList<>();
    private final List<Ambiente> ambientiCompatibiliConIlMeteo = new ArrayList<>();
    AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();

    /**
     * Cerca i ristoranti disponibili secondo i filtri inseriti dall'utente
     */
    public List<RistoranteBean> cercaRistorantiDisponibili(FiltriBean filtriInseriti) throws EccezioneDAO, ValidazioneException {
        List<RistoranteBean> listaRistorantiPrenotabili = new ArrayList<>();
        ristorantiPrenotabili.clear();

        RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
        PrenotazioneDAO prenotazioneDAO = daoFactoryFacade.getPrenotazioneDAO();
        GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();

        Filtro filtri = ConvertitoreFiltri.filtriBeanInModel(filtriInseriti);
        filtri.validaNumeroPersone();
        filtri.validaData();

        GiorniSettimana giornoPrenotazione = GiorniSettimanaHelper.dataInGiornoSettimana(filtriInseriti.getData());

        for (Ristorante ristorante : ristoranteDAO.filtraRistorantiPerCitta(filtri)) {

            if (!orarioDiPrenotazioneValido(ristorante, filtriInseriti.getOra()) ||
                    !giornoDiPrenotazioneValido(giornoChiusuraDAO.giorniChiusuraRistorante(ristorante), giornoPrenotazione)) {
                continue;
            }

            boolean ristoranteAggiunto = false;

            ristorante.setAmbienteRistorante(ambienteDAO.cercaAmbientiDelRistorante(ristorante));  /* Aggiunge gli ambienti appartenenti al ristorante */
            for (Ambiente ambiente : ristorante.getAmbienteRistorante()) {


                Prenotazione prenotazione = new Prenotazione(
                        filtriInseriti.getData(),
                        filtriInseriti.getOra(),
                        filtriInseriti.getNumeroPersone(),
                        ambiente,
                        null,
                        trovaFasciaOraria(ristorante, filtriInseriti.getOra())
                );

                int postiDisponibili = ambiente.getNumeroCoperti() -
                        prenotazioneDAO.postiOccupatiPerDataEFasciaOraria(prenotazione).getNumeroPersone();


                if (postiDisponibili >= filtriInseriti.getNumeroPersone()) {

                    ristorantiPrenotabili.add(ristorante);

                    if (!ristoranteAggiunto) {
                        listaRistorantiPrenotabili.add(ConvertitoreRistorante.ristoranteModelInBean(ristorante));
                        ristoranteAggiunto = true;
                    }
                }
            }
        }
        return listaRistorantiPrenotabili;
    }


    /**
     * Controlla se l'orario di prenotazione è valido rispetto agli orari di apertura del ristorante
     */
    private boolean orarioDiPrenotazioneValido(Ristorante ristorante, LocalTime oraPrenotazione) {

        boolean pranzoValido = ristorante.getOrari().getInizioPranzo() != null &&
                ristorante.getOrari().getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getOrari().getFinePranzo());
        boolean cenaValida = ristorante.getOrari().getInizioCena() != null &&

                ristorante.getOrari().getFineCena() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioCena()) && //
                !oraPrenotazione.isAfter(ristorante.getOrari().getFineCena());

        return pranzoValido || cenaValida;
    }

    /**
     * Controlla se il giorno della prenotazione è valido rispetto ai giorni di chiusura del ristorante
     */
    private boolean giornoDiPrenotazioneValido(GiorniEOrari giorniEOrari, GiorniSettimana giornoPrenotazione) {
        return !giorniEOrari.getGiorniChiusura().contains(giornoPrenotazione);
    }


    /**
     * Determina la fascia oraria della prenotazione in base all'orario
     */
    private FasciaOraria trovaFasciaOraria(Ristorante ristorante, LocalTime oraPrenotazione) {
        if (ristorante.getOrari().getInizioPranzo() != null && ristorante.getOrari().getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getOrari().getFinePranzo())) {

            return FasciaOraria.PRANZO;
        }

        return FasciaOraria.CENA;
    }

    public List<RistoranteBean> filtraRistorantiDisponibili(FiltriBean filtriBeanInseriti, MeteoBean meteoBean) throws EccezioneDAO {

        List<RistoranteBean> listaRistorantiFiltrati = new ArrayList<>();
        Set<String> ristorantiAggiunti = new HashSet<>();

        Filtro filtriModel = ConvertitoreFiltri.filtriBeanInModel(filtriBeanInseriti);

        for (Ristorante ristorante : ristorantiPrenotabili) {

            String partitaIVA = ristorante.getPartitaIVA();

            if (ristorantiAggiunti.contains(partitaIVA)) {
                continue;
            }

            if (rispettaFiltri(ristorante, filtriModel, meteoBean)) {
                RistoranteBean ristoranteBean= ConvertitoreRistorante.ristoranteModelInBean(ristorante);
                listaRistorantiFiltrati.add(ristoranteBean);
                ristorantiAggiunti.add(partitaIVA);
            }
        }

        return listaRistorantiFiltrati;
    }

    private boolean rispettaFiltri(Ristorante ristorante, Filtro filtri, MeteoBean previsioniMeteo) throws EccezioneDAO {

        if (filtri.getFasciaPrezzoRistorante() != null &&
                !filtri.getFasciaPrezzoRistorante().equals(ristorante.getFasciaPrezzo())) {
            return false;
        }


        if (filtri.getTipoCucina() != null &&
                !filtri.getTipoCucina().isEmpty() &&
                !filtri.getTipoCucina().contains(ristorante.getCucina())) {
            return false;
        }



        if (filtri.getTipoDieta() != null && !filtri.getTipoDieta().isEmpty()) {

            Ristorante ristoranteDaControllare= new Ristorante(ristorante.getPartitaIVA());
            ristoranteDaControllare.setTipoDieta(filtri.getTipoDieta());
            Ristorante dieteRistoranteCompatibili = dietaDAO.controllaDieteDelRistorante(ristoranteDaControllare);


            if (dieteRistoranteCompatibili==null || dieteRistoranteCompatibili.getTipoDieta() == null || dieteRistoranteCompatibili.getTipoDieta().isEmpty()) {
                return false;
            }

        }


        if (previsioniMeteo!=null) {
            List<Ambiente> ambientiCompatibili = generaAmbientiCompatibiliDaMeteo(previsioniMeteo);
            return ristoranteCompatibile(ristorante, ambientiCompatibili);

        }

        return true;
    }


    /**
     * Restituisce la lista degli ambienti compatibili con le condizioni metereologiche fornite.
     * Gli ambienti vengono determinati in base al tipo di meteo (sole, pioggia, nuvoloso)
     * e alla temperatura, includendo eventuali extra necessari come riscaldamento o raffreddamento.
     *
     * @param meteo oggetto {@link MeteoBean} che rappresenta le condizioni metereologiche attuali.
     * @return lista di oggetti {@link Ambiente} compatibili con il meteo specificato.
     * <p>
     *
     * Restituisce ambienti compatibili con il meteo:
     * - Sole:
     *   - Temperatura normale → ESTERNO
     *   - Freddo/caldo → ESTERNO_COPERTO con riscaldamento/raffreddamento
     * <p>
     * - Pioggia:
     *   - Sempre → INTERNO
     *   - ESTERNO_COPERTO con riscaldamento/raffreddamento se freddo/caldo
     * <p>
     * - Nuvoloso:
     *   - Temperatura normale → ESTERNO + ESTERNO_COPERTO
     *   - Freddo/caldo → INTERNO + ESTERNO_COPERTO con riscaldamento/raffreddamento
     *
     * - Altro → INTERNO
     */
    public List<Ambiente> generaAmbientiCompatibiliDaMeteo(MeteoBean meteo) {
        List<Ambiente> ambientiCompatibili = new ArrayList<>();
        int temperatura = meteo.getTemperatura();
        String condizione = meteo.getTempo().toLowerCase();

        boolean temperaturaNormale = temperatura >= 15 && temperatura <= 25;
        boolean temperaturaFredda = temperatura < 15;

        Set<Extra> extraClimatici = temperaturaNormale ? Set.of() : getExtraClimatici(temperaturaFredda);

        switch (condizione) {
            case "sole" -> {
                if (temperaturaNormale) {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO, null, null, Set.of()));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici));
                }
            }

            case "pioggia" -> {
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of()));
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici));
            }

            case "nuvoloso" -> {
                if (temperaturaNormale) {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO, null, null, Set.of()));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, Set.of()));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of()));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici));
                }
            }

            default -> ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of()));
        }

        return ambientiCompatibili;
    }



    /**
     * Restituisce l'extra necessario in base alla temperatura.
     * - Se fa freddo → RISCALDAMENTO
     * - Se fa caldo → RAFFREDDAMENTO
     * - Se la temperatura è normale → nessun extra
     *
     * @param temperaturaFredda true se la temperatura è sotto i 15°C
     * @return Set di ExtraClimatico richiesto
     */
    private Set<Extra> getExtraClimatici(boolean temperaturaFredda) {
        return temperaturaFredda
                ? Set.of(Extra.RISCALDAMENTO)
                : Set.of(Extra.RAFFREDDAMENTO);
    }


    public boolean ristoranteCompatibile(Ristorante ristorante, List<Ambiente> ambientiCompatibili) throws EccezioneDAO {
        List<Ristorante> occorrenzeRistorante = ristorantiPrenotabili.stream()
                .filter(r -> r.getPartitaIVA().equalsIgnoreCase(ristorante.getPartitaIVA()))
                .toList();

        for (Ristorante r : occorrenzeRistorante) {
            for (Ambiente ambienteRistorante : r.getAmbienteRistorante()) {
                if (isAmbienteCompatibile(ambienteRistorante, ambientiCompatibili, r.getPartitaIVA())) {
                    ambientiCompatibiliConIlMeteo.add(ambienteRistorante);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isAmbienteCompatibile(Ambiente ambienteRistorante, List<Ambiente> ambientiCompatibili, String partitaIVA) throws EccezioneDAO {
        TipoAmbiente tipo = ambienteRistorante.getTipoAmbiente();

        Ambiente ambienteCompatibile = ambientiCompatibili.stream()
                .filter(a -> a.getTipoAmbiente() == tipo)
                .findFirst()
                .orElse(null);

        if (ambienteCompatibile == null) return false;

        Set<Extra> extraRichiesti = ambienteCompatibile.getExtra();
        if (extraRichiesti == null || extraRichiesti.isEmpty()) {
            ambienteRistorante.setRistorante(partitaIVA);
            return true;
        }

        ambienteRistorante.setRistorante(partitaIVA);
        Ambiente ambienteConExtra = ambienteDAO.cercaExtraPerAmbiente(ambienteRistorante);
        Set<Extra> extraDisponibili = ambienteConExtra != null ? ambienteConExtra.getExtra() : null;

        return extraDisponibili != null && extraDisponibili.containsAll(extraRichiesti);
    }



    public MeteoBean previsioneMetereologiche(FiltriBean filtri) throws IOException {
        try {
            if(filtri.getMeteo()) {
                return BoundaryMeteoMockAPI.getMeteoDaMockAPI();
            }
        } catch (IOException e) {
            throw new IOException("Errore di comunicazione con il servizio meteo: " + e.getMessage());
        }
        return null;
    }
}
