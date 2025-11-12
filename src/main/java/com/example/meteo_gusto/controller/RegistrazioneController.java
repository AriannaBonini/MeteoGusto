package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.convertitore.*;
import java.util.*;

public class RegistrazioneController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO = daoFactoryFacade.getPersonaDAO();

    public void registraUtente(RegistrazioneUtenteBean registrazioneUtenteBean) throws EccezioneDAO {
        try {
            Persona utente = ConvertitorePersona.personaBeanInModel(registrazioneUtenteBean.getPersona());
            personaDAO.registraPersona(utente);
        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente", e);
        }
    }

    public void registraRistoratore(RegistrazioneUtenteBean registrazioneRistoratoreBean) throws EccezioneDAO {
        try {
            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();
            DietaDAO dietaDAO = daoFactoryFacade.getDietaDAO();
            AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();

            Persona proprietarioRistorante = ConvertitorePersona.personaBeanInModel(registrazioneRistoratoreBean.getPersona());
            Ristorante ristorante= proprietarioRistorante.getRistorante();
            ristorante.setRistoratore(proprietarioRistorante.getEmail());


            controllaFasciaOrariaPranzo(ristorante.getOrari());
            controllaFasciaOrariaCena(ristorante.getOrari());

            personaDAO.registraPersona(proprietarioRistorante);
            ristoranteDAO.registraRistorante(ristorante);

            if (ristorante.getOrari().getGiorniChiusura() != null && !ristorante.getOrari().getGiorniChiusura().isEmpty()) {
                giornoChiusuraDAO.registraGiorniChiusuraRistorante(ristorante);
            }

            if (ristorante.getTipoDieta()!=null && !ristorante.getTipoDieta().isEmpty()) {
                System.out.println("Sto per registrare la dieta " + ristorante.getTipoDieta());
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

                listaAmbiente.add(ConvertitoreAmbiente.ambienteBeanInModel(ambienteBean));
            }
        }catch (ValidazioneException e) {
            throw new ValidazioneException("Errore durante la creazione della lista degli ambienti per la registrazione",e);
        }

        return listaAmbiente;
    }


    private void controllaFasciaOrariaPranzo(GiorniEOrari orarioRistorante) throws ValidazioneException {
        if (orarioRistorante.getInizioPranzo() != null && orarioRistorante.getFinePranzo() != null && !orarioRistorante.getFinePranzo().isAfter(orarioRistorante.getInizioPranzo())) {
            throw new ValidazioneException("L'orario di fine pranzo deve essere successivo a quello di inizio pranzo.");
        }
    }

    private void controllaFasciaOrariaCena(GiorniEOrari orarioRistorante) throws ValidazioneException {
        if (orarioRistorante.getInizioCena() != null && orarioRistorante.getFineCena() != null && ! orarioRistorante.getFineCena().isAfter(orarioRistorante.getInizioCena())) {
            throw new ValidazioneException("L'orario di fine cena deve essere successivo a quello di inizio cena.");
        }
    }


}
