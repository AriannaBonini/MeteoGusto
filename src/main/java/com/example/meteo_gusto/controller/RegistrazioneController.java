package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreBeanModel;

public class RegistrazioneController {

    private static final DAOFactoryFacade daoFactoryFacade= DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO= daoFactoryFacade.getPersonaDAO();


    public void registraUtente(RegistrazioneUtenteBean registrazioneUtenteBean) throws EccezioneDAO{
        try {
            Persona utente = ConvertitoreBeanModel.personaBeanInModel(registrazioneUtenteBean.getPersona(), TipoPersona.UTENTE);

            personaDAO.registraPersona(utente);

        }catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente",e);
        }
    }

    public void registraRistoratore(RegistrazioneRistoratoreBean registrazioneRistoratoreBean) throws EccezioneDAO, ValidazioneException {
        try {

            RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
            GiorniChiusuraDAO giorniChiusuraDAO = daoFactoryFacade.getGiorniChiusuraDAO();
            DietaDAO dietaDAO = daoFactoryFacade.getDietaDAO();
            DisponibilitaDAO disponibilitaDAO = daoFactoryFacade.getDisponibilitaDAO();


            Persona proprietarioRistorante = ConvertitoreBeanModel.personaBeanInModel(
                    registrazioneRistoratoreBean.getProprietarioRistorante().getPersona(),
                    TipoPersona.RISTORATORE
            );

            Ristorante ristorante = ConvertitoreBeanModel.ristoranteBeanInModel(
                    registrazioneRistoratoreBean.getRistorante(),
                    proprietarioRistorante
            );

            GiorniChiusura giorniChiusura = ConvertitoreBeanModel.giorniChiusuraBeanInModel(
                    new GiorniChiusuraBean(
                            registrazioneRistoratoreBean.getRistorante(),
                            registrazioneRistoratoreBean.getGiorniChiusura()
                    ),
                    proprietarioRistorante
            );

            Dieta dieta = ConvertitoreBeanModel.dietaBeanInModel(
                    new DietaBean(
                            registrazioneRistoratoreBean.getRistorante(),
                            registrazioneRistoratoreBean.getDieta()
                    ),
                    proprietarioRistorante
            );

            AmbienteDisponibile ambienteDisponibile = creaAmbienteDisponibile(
                    registrazioneRistoratoreBean,
                    proprietarioRistorante
            );


            personaDAO.registraPersona(proprietarioRistorante);
            ristoranteDAO.registraRistorante(ristorante);
            giorniChiusuraDAO.registraGiorniChiusuraRistorante(giorniChiusura);
            dietaDAO.registraDieta(dieta);
            disponibilitaDAO.registraDisponibilita(ambienteDisponibile);

        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristoratore", e);
        }
    }


    private AmbienteDisponibile creaAmbienteDisponibile(RegistrazioneRistoratoreBean registrazioneRistoratoreBean, Persona proprietarioRistorante) throws ValidazioneException {
        AmbienteSpecialeDisponibileBean ambienteSpecialeBean = creaAmbienteSpecialeBean(
                registrazioneRistoratoreBean.getAmbienteSpecialeDisponibileBean()
        );

        AmbienteDisponibileBean ambienteDisponibileBean = new AmbienteDisponibileBean(
                registrazioneRistoratoreBean.getRistorante(),
                registrazioneRistoratoreBean.getDisponibilita(),
                ambienteSpecialeBean
        );

        return ConvertitoreBeanModel.disponibilitaBeanInModel(
                ambienteDisponibileBean,
                proprietarioRistorante
        );
    }

    private AmbienteSpecialeDisponibileBean creaAmbienteSpecialeBean(AmbienteSpecialeDisponibileBean bean) throws ValidazioneException {
        if (bean == null) return null;

        return new AmbienteSpecialeDisponibileBean(
                bean.getExtra(),
                bean.getTipoAmbienteConExtra(),
                bean.getNumeroCoperti()
        );
    }



}
