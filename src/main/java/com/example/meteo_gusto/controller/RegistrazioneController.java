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

    public void registraRistoratore(RegistrazioneRistoratoreBean registrazioneRistoratoreBean) throws EccezioneDAO {
        try {
            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();
            DietaDAO dietaDAO = daoFactoryFacade.getDietaDAO();
            AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();

            Persona proprietarioRistorante = ConvertitorePersona.personaBeanInModel(registrazioneRistoratoreBean.getRistorante().getProprietario());
            Ristorante ristorante = ConvertitoreRistorante.ristoranteBeanInModel(registrazioneRistoratoreBean.getRistorante());

            ristorante.validaOrariPranzo();
            ristorante.validaOrariCena();

            personaDAO.registraPersona(proprietarioRistorante);
            ristoranteDAO.registraRistorante(ristorante);

            List<GiornoChiusura> listaGiornoChiusura = giorniChiusuraRistoranteInModel(registrazioneRistoratoreBean);
            if (listaGiornoChiusura != null && !listaGiornoChiusura.isEmpty()) {
                giornoChiusuraDAO.registraGiorniChiusuraRistorante(listaGiornoChiusura);
            }

            List<Dieta> listaDieta = dietaRistoranteInModel(registrazioneRistoratoreBean);
            if (listaDieta!=null && !listaDieta.isEmpty()) {
                dietaDAO.registraDieta(listaDieta);
            }

            List<Ambiente> listaAmbiente = ambienteRistoranteInModel(registrazioneRistoratoreBean);
            ambienteDAO.registraDisponibilita(listaAmbiente);

        } catch (ValidazioneException | EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristoratore", e);
        }
    }


    /* ---------------- METODI PRIVATI DI SUPPORTO ---------------- */

    private List<GiornoChiusura> giorniChiusuraRistoranteInModel(RegistrazioneRistoratoreBean registrazione) {
        if (registrazione.getGiorniChiusura() == null || registrazione.getGiorniChiusura().isEmpty()) {
            return Collections.emptyList();
        }

        Ristorante ristoranteModel = ConvertitoreRistorante.ristoranteBeanInModel(registrazione.getRistorante());

        return registrazione.getGiorniChiusura().stream()
                .map(giorno -> new GiornoChiusura(ristoranteModel, giorno))
                .toList();
    }



    private List<Dieta> dietaRistoranteInModel(RegistrazioneRistoratoreBean registrazione) {
        DietaBean bean = new DietaBean();
        bean.setRistorante(registrazione.getRistorante());
        bean.setTipoDieta(registrazione.getDieta());

        if (bean.getTipoDieta() == null || bean.getTipoDieta().isEmpty()) {
            return Collections.emptyList();
        }

        return bean.getTipoDieta().stream()
                .map(tipo -> {
                    DietaBean singoloBean = new DietaBean();
                    singoloBean.setRistorante(bean.getRistorante());
                    singoloBean.setTipoDieta(Set.of(tipo));
                    return ConvertitoreDieta.dietaBeanInModel(singoloBean);
                })
                .toList();
    }

    private List<Ambiente> ambienteRistoranteInModel(RegistrazioneRistoratoreBean registrazione) {
        if (registrazione.getAmbiente() == null || registrazione.getAmbiente().isEmpty()) {
            return Collections.emptyList();
        }

        List<Ambiente> listaAmbiente = new ArrayList<>();

        for (AmbienteBean ambienteBean : registrazione.getAmbiente()) {
            ambienteBean.setRistorante(registrazione.getRistorante());

            listaAmbiente.add(ConvertitoreAmbiente.ambienteBeanInModel(ambienteBean));
        }

        return listaAmbiente;
    }


}
