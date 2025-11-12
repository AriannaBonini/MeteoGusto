package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.convertitore.ConvertitorePersona;

public class LoginController {
    private static final DAOFactoryFacade daoFactoryFacade= DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO= daoFactoryFacade.getPersonaDAO();
    private static final RistoranteDAO ristoranteDAO= daoFactoryFacade.getRistoranteDAO();
    private static final AmbienteDAO ambienteDAO= daoFactoryFacade.getAmbienteDAO();

    public PersonaBean accedi(PersonaBean credenzialiBean) throws EccezioneDAO {
        try {
            Persona credenzialiPersona= ConvertitorePersona.personaBeanInModel(credenzialiBean);

            Persona persona = personaDAO.login(credenzialiPersona);
            if(persona==null) {
                return null;
            }

            if(persona.getTipoPersona().equals(TipoPersona.RISTORATORE)) {
                persona.ristoranteDiProprieta(ristoranteDAO.selezionaRistorantePerProprietario(persona));
                persona.getRistorante().setAmbienteRistorante(ambienteDAO.cercaAmbientiDelRistorante(persona.getRistorante()));


            }

            Sessione.getInstance().login(persona);

            return ConvertitorePersona.personaModelInBean(persona);

        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante il login dell'utente", e);
        }
    }



}
