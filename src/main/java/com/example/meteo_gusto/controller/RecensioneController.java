package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.RecensioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRecensione;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRistorante;

import java.time.LocalDate;

public class RecensioneController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

    public void recensisciRistorante(RecensioneBean recensioneBean) throws EccezioneDAO {
        RecensioneDAO recensioneDAO= daoFactoryFacade.getRecensioneDAO();

        LocalDate dataRecensione = LocalDate.now();
        Persona utente= Sessione.getInstance().getPersona();

        Recensione recensione= ConvertitoreRecensione.recensioneBeanInModel(recensioneBean,utente,dataRecensione);
        recensioneDAO.nuovaRecensione(recensione);
    }

    public RistoranteBean nuovaMediaRecensione(RistoranteBean ristoranteBean) throws EccezioneDAO {
        RistoranteDAO ristoranteDAO= daoFactoryFacade.getRistoranteDAO();

        Ristorante ristorante= ristoranteDAO.mediaStelleRistorante(ConvertitoreRistorante.ristoranteBeanInModel(ristoranteBean));
        return ConvertitoreRistorante.ristoranteModelInBean(ristorante);

    }





}
