package com.example.meteo_gusto.controller;


import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaOraria;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.GiorniSettimanaHelper;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreFiltri;
import com.example.meteo_gusto.utilities.convertitore.ConvertitorePrenotazione;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrenotaRistoranteController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private final List<Prenotazione> ristorantiPrenotabili= new ArrayList<>();

    /**
     * Cerca i ristoranti disponibili secondo i filtri inseriti dall'utente
     */
    public List<PrenotazioneBean> cercaRistorantiDisponibili(FiltriBean filtriInseriti) throws EccezioneDAO, ValidazioneException {
        List<PrenotazioneBean> listaRistorantiPrenotabili = new ArrayList<>();
        ristorantiPrenotabili.clear();

        try {
            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();
            PrenotazioneDAO prenotazioneDAO = daoFactoryFacade.getPrenotazioneDAO();
            GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();

            Filtro filtri = ConvertitoreFiltri.filtriBeanInModel(filtriInseriti);
            GiorniSettimana giornoPrenotazione = GiorniSettimanaHelper.dataInGiornoSettimana(filtriInseriti.getData());

            for (Ristorante ristorante : ristoranteDAO.filtraRistorantiPerCitta(filtri)) {
                if (!orarioDiPrenotazioneValido(ristorante, filtriInseriti.getOra()) ||
                        !giornoDiPrenotazioneValido(giornoChiusuraDAO.giorniChiusuraRistorante(ristorante), giornoPrenotazione)) {
                    continue;
                }

                boolean ristoranteAggiunto = false;

                for (Ambiente ambiente : ambienteDAO.cercaAmbientiDelRistorante(ristorante)) {
                    ambiente.setRistorante(ristorante);

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
                        ristorantiPrenotabili.add(prenotazione);

                        if (!ristoranteAggiunto) {
                            listaRistorantiPrenotabili.add(ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione));
                            ristoranteAggiunto = true;
                        }
                    }
                }
            }

            return listaRistorantiPrenotabili;

        } catch (ValidazioneException | EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la ricerca di ristoranti disponibili secondo i filtri: città, data, ora, numero persone", e);
        }
    }


    /**
     * Controlla se l'orario di prenotazione è valido rispetto agli orari di apertura del ristorante
     */
    private boolean orarioDiPrenotazioneValido(Ristorante ristorante, LocalTime oraPrenotazione) {

        boolean pranzoValido = ristorante.getInizioPranzo() != null &&
                ristorante.getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getFinePranzo());
        boolean cenaValida = ristorante.getInizioCena() != null &&

                ristorante.getFineCena() != null &&
                !oraPrenotazione.isBefore(ristorante.getInizioCena()) && //
                !oraPrenotazione.isAfter(ristorante.getFineCena());

        return pranzoValido || cenaValida;
    }

    /**
     * Controlla se il giorno della prenotazione è valido rispetto ai giorni di chiusura del ristorante
     */
    private boolean giornoDiPrenotazioneValido(List<GiornoChiusura> giorniChiusura, GiorniSettimana giornoPrenotazione) {
        return giorniChiusura.stream()
                .noneMatch(gc -> gc.getNomeGiornoChiusura().equals(giornoPrenotazione));
    }

    /**
     * Determina la fascia oraria della prenotazione in base all'orario
     */
    private FasciaOraria trovaFasciaOraria(Ristorante ristorante, LocalTime oraPrenotazione) {
        if (ristorante.getInizioPranzo() != null && ristorante.getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getFinePranzo())) {
            return FasciaOraria.PRANZO;
        }
        return FasciaOraria.CENA;
    }

    public List<PrenotazioneBean> filtraRistorantiDisponibili(FiltriBean filtriBeanInseriti)
            throws EccezioneDAO, ValidazioneException {

        DietaDAO dietaDAO = daoFactoryFacade.getDietaDAO();
        List<PrenotazioneBean> listaRistorantiFiltrati = new ArrayList<>();
        Set<String> ristorantiAggiunti = new HashSet<>();

        Filtro filtriModel = ConvertitoreFiltri.filtriBeanInModel(filtriBeanInseriti);

        for (Prenotazione prenotazione : ristorantiPrenotabili) {
            Ristorante ristorante = prenotazione.getAmbiente().getRistorante();
            String partitaIVA = ristorante.getPartitaIVA();

            if (ristorantiAggiunti.contains(partitaIVA)) {
                continue;
            }

            if (rispettaFiltri(ristorante, filtriModel, dietaDAO)) {
                PrenotazioneBean bean = ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione);
                listaRistorantiFiltrati.add(bean);
                ristorantiAggiunti.add(partitaIVA);
            }
        }

        return listaRistorantiFiltrati;
    }



    private boolean rispettaFiltri(Ristorante ristorante, Filtro filtri, DietaDAO dietaDAO) throws EccezioneDAO {

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
            Dieta dietaCompatibile = dietaDAO.controllaDieteDelRistorante(
                    new Dieta(ristorante, filtri.getTipoDieta())
            );

            if (dietaCompatibile == null || dietaCompatibile.getTipoDieta() == null || dietaCompatibile.getTipoDieta().isEmpty()) {
                return false;
            }
        }


        if (filtri.getMeteo()) {
            // return applicaFiltroMeteo(ristorante);
        }

        return true;
    }


}
