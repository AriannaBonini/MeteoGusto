package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.RecensioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreRecensione;
import java.time.LocalDate;

public class RecensioneController {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

    public void recensisciRistorante(RecensioneBean recensioneBean) throws EccezioneDAO {

        LocalDate dataRecensione = LocalDate.now();
        Persona utente= Sessione.getInstance().getPersona();

        Recensione recensione= ConvertitoreRecensione.recensioneInModel(recensioneBean,utente,dataRecensione);
        salvaRecensione(recensione);
        aggiornaMediaRistorante(recensione);
    }

    public RistoranteBean nuovaMediaRecensione(RistoranteBean ristoranteBean) throws EccezioneDAO {
        Ristorante ristorante= daoFactoryFacade.getRistoranteDAO().mediaStelleRistorante(new Ristorante(ristoranteBean.getPartitaIVA()));

        RistoranteBean ristoranteBeanMediaStelle= new RistoranteBean();
        ristoranteBeanMediaStelle.setMediaStelle(ristorante.getMediaStelle());

        return ristoranteBeanMediaStelle;

    }

    private void salvaRecensione(Recensione recensione) throws EccezioneDAO {
        if (daoFactoryFacade.getRecensioneDAO().esisteRecensione(recensione)) {
            daoFactoryFacade.getRecensioneDAO().aggiornaRecensione(recensione);
        } else {
            daoFactoryFacade.getRecensioneDAO().nuovaRecensione(recensione);
        }
    }


    private void aggiornaMediaRistorante(Recensione recensione) throws EccezioneDAO {
        Ristorante mediaRecensioniRistorante= daoFactoryFacade.getRecensioneDAO().calcolaNuovaMedia(recensione);
        daoFactoryFacade.getRistoranteDAO().aggiornaMediaStelle(mediaRecensioniRistorante);
    }



}
