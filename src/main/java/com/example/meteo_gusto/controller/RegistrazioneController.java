package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.RegistrazioneRistoratoreBean;
import com.example.meteo_gusto.bean.RegistrazioneUtenteBean;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;

public class RegistrazioneController {

    private static final DAOFactoryFacade daoFactoryFacade= DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO= daoFactoryFacade.getPersonaDAO();
    private static final RistoranteDAO ristoranteDAO= daoFactoryFacade.getRistoranteDAO();

    public void registraUtente(RegistrazioneUtenteBean registrazioneUtenteBean) throws EccezioneDAO{
        try {
            Persona utente= new Persona(registrazioneUtenteBean.getNome(),
                registrazioneUtenteBean.getCognome(),
                registrazioneUtenteBean.getTelefono(),
                registrazioneUtenteBean.getEmail(),
                registrazioneUtenteBean.getPassword(), TipoPersona.UTENTE
            );

            personaDAO.registraUtente(utente);

        }catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente",e);
        }
    }

    public void registraRistoratore(RegistrazioneRistoratoreBean registrazioneRistoratoreBean) throws EccezioneDAO{
        try {
            registraUtente(registrazioneRistoratoreBean.getDatiRistoratore());


            Orari orari = new Orari(registrazioneRistoratoreBean.getDatiRistorante().getOrari().getInizioPranzo(),registrazioneRistoratoreBean.getDatiRistorante().getOrari().getFinePranzo(),
                    registrazioneRistoratoreBean.getDatiRistorante().getOrari().getInizioCena(),registrazioneRistoratoreBean.getDatiRistorante().getOrari().getFineCena());

            Posizione posizione= new Posizione(registrazioneRistoratoreBean.getDatiRistorante().getPosizioneRistorante().getIndirizzoCompleto(),registrazioneRistoratoreBean.getDatiRistorante().getPosizioneRistorante().getCitta(),registrazioneRistoratoreBean.getDatiRistorante().getPosizioneRistorante().getCap());

            OffertaCulinaria offertaCulinaria= new OffertaCulinaria(registrazioneRistoratoreBean.getDatiRistorante().getOffertaCulinaria().getCucina(),registrazioneRistoratoreBean.getDatiRistorante().getOffertaCulinaria().getFasciaPrezzo());

            Ristorante ristorante= new Ristorante(registrazioneRistoratoreBean.getDatiRistorante().getPartitaIVA(),
                    registrazioneRistoratoreBean.getDatiRistorante().getNome(), registrazioneRistoratoreBean.getDatiRistorante().getTelefono(),
                    orari, offertaCulinaria, posizione);


            AmbienteECoperti ambienteECoperti= new AmbienteECoperti(registrazioneRistoratoreBean.getDatiRistorante().getAmbienteECoperti().getAmbienteECopertiRistorante(),registrazioneRistoratoreBean.getDatiRistorante().getAmbienteECoperti().getExtra());




            ristoranteDAO.registraRistorante(ristorante);

        }catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristoratore",e);
        }
    }
}
