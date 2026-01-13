package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.convertitore.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class RegistrazioneController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO = daoFactoryFacade.getPersonaDAO();

    public void registraUtente(RegistrazionePersonaBean registrazionePersonaBean) throws EccezioneDAO {
        try {
            Persona utente = ConvertitorePersona.registrazioneUtenteInModel(registrazionePersonaBean.getPersona());
            personaDAO.registraPersona(utente);
        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente", e);
        }
    }

    public void registraRistoratore(RegistrazionePersonaBean registrazioneRistoratoreBean) throws EccezioneDAO {
        try {
            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();
            DietaDAO dietaDAO = daoFactoryFacade.getDietaDAO();
            AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();

            Persona proprietarioRistorante = ConvertitorePersona.registrazioneRistoranteInModel(registrazioneRistoratoreBean.getPersona());

            Ristorante ristorante= proprietarioRistorante.getRistorante();
            ristorante.setRistoratore(proprietarioRistorante.getEmail());


            controllaFasciaOrariaPranzo(ristorante.orariApertura());
            controllaFasciaOrariaCena(ristorante.orariApertura());

            personaDAO.registraPersona(proprietarioRistorante);
            ristoranteDAO.registraRistorante(ristorante);

            if (ristorante.orariApertura().giorniChiusura() != null && !ristorante.orariApertura().giorniChiusura().isEmpty()) {
                giornoChiusuraDAO.registraGiorniChiusuraRistorante(ristorante);
            }

            if (ristorante.getDiete()!=null && !ristorante.getDiete().isEmpty()) {
                dietaDAO.registraDieta(ristorante);
            }

            List<Ambiente> listaAmbiente = ambienteRistoranteInModel(registrazioneRistoratoreBean.getPersona());
            ambienteDAO.registraDisponibilita(listaAmbiente);

        } catch (ValidazioneException | EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristoratore", e);
        }
    }


    /* ---------------- METODI PRIVATI DI SUPPORTO ---------------- */

    private List<Ambiente> ambienteRistoranteInModel(PersonaBean personaBean) throws ValidazioneException {
        if (personaBean.getRistoranteBean().getAmbiente() == null || personaBean.getRistoranteBean().getAmbiente().isEmpty()) {
            return Collections.emptyList();
        }
        List<Ambiente> listaAmbiente = new ArrayList<>();
        try {
            for (AmbienteBean ambienteBean : personaBean.getRistoranteBean().getAmbiente()) {
                ambienteBean.setRistorante(personaBean.getRistoranteBean().getPartitaIVA());

                if(Objects.equals(ambienteBean.getTipoAmbiente(), TipoAmbiente.ESTERNO_COPERTO.getId())) {
                    listaAmbiente.add(ConvertitoreAmbiente.registrazioneAmbienteSpecialeInModel(ambienteBean));
                }else {
                    listaAmbiente.add(ConvertitoreAmbiente.registrazioneAmbienteInModel(ambienteBean));
                }
            }
        }catch (ValidazioneException e) {
            throw new ValidazioneException("Errore durante la creazione della lista degli ambienti per la registrazione",e);
        }

        return listaAmbiente;
    }


    private void controllaFasciaOrariaPranzo(GiorniEOrari orarioRistorante)
            throws ValidazioneException {

        if (orarioRistorante.getInizioPranzo() != null && orarioRistorante.getFinePranzo() != null) {

            LocalTime inizio = orarioRistorante.getInizioPranzo();
            LocalTime fine = orarioRistorante.getFinePranzo();

            if (!fine.isAfter(inizio)) {throw new ValidazioneException("L'orario di fine pranzo deve essere successivo a quello di inizio pranzo.");}

            if (fine.isAfter(LocalTime.of(18, 0))) {throw new ValidazioneException("L'orario di fine pranzo deve essere entro le 18:00.");}

            if (Duration.between(inizio, fine).toMinutes() < 60) {throw new ValidazioneException("La fascia oraria del pranzo deve durare almeno 1 ora.");}
        }
    }


    private void controllaFasciaOrariaCena(GiorniEOrari orarioRistorante)
            throws ValidazioneException {

        if (orarioRistorante.getInizioCena() != null && orarioRistorante.getFineCena() != null) {

            LocalTime inizio = orarioRistorante.getInizioCena();
            LocalTime fine = orarioRistorante.getFineCena();

            if (!fine.isAfter(inizio)) {throw new ValidazioneException("L'orario di fine cena deve essere successivo a quello di inizio cena.");}

            if (fine.isBefore(LocalTime.of(18, 0))) { throw new ValidazioneException("L'orario di fine cena deve essere successivo alle 18:00.");}

            if (Duration.between(inizio, fine).toMinutes() < 60) {throw new ValidazioneException("La fascia oraria della cena deve durare almeno 1 ora.");}
        }
    }



}
