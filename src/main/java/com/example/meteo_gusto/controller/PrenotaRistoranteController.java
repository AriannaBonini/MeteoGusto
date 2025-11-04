package com.example.meteo_gusto.controller;


import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
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
import java.util.List;

public class PrenotaRistoranteController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

    /**
     * Cerca i ristoranti disponibili secondo i filtri inseriti dall'utente
     */
    public List<PrenotazioneBean> cercaRistorantiDisponibili(FiltriBean filtriInseriti) throws EccezioneDAO {
        List<PrenotazioneBean> listaRistorantiPrenotabili = new ArrayList<>();

        try {
            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();
            PrenotazioneDAO prenotazioneDAO = daoFactoryFacade.getPrenotazioneDAO();
            GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();

            Filtro filtri = ConvertitoreFiltri.filtriBeanInModel(filtriInseriti);

            List<Ristorante> ristorantiFiltrati = ristoranteDAO.filtraRistorantiPerCitta(filtri);

            GiorniSettimana giornoPrenotazione = GiorniSettimanaHelper.dataInGiornoSettimana(filtriInseriti.getData());

            for (Ristorante ristorante : ristorantiFiltrati) {

                List<GiornoChiusura> giorniChiusuraRistorante = giornoChiusuraDAO.giorniChiusuraRistorante(ristorante);

                if (!orarioDiPrenotazioneValido(ristorante, filtriInseriti.getOra()) ||
                        !giornoDiPrenotazioneValido(giorniChiusuraRistorante, giornoPrenotazione)) {
                    continue; // Ristorante chiuso in quell'orario o giorno
                }

                List<Ambiente> ambientiRistorante = ambienteDAO.cercaAmbientiDelRistorante(ristorante);

                for (Ambiente ambiente : ambientiRistorante) {
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
                        PrenotazioneBean prenotazioneBean = ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione);
                        listaRistorantiPrenotabili.add(prenotazioneBean);
                    }
                }
            }

        } catch (ValidazioneException | EccezioneDAO e) {
            throw new EccezioneDAO(
                    "Errore durante la ricerca di ristoranti disponibili secondo i filtri: città, data, ora, numero persone",
                    e
            );
        }

        return listaRistorantiPrenotabili;
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
}
