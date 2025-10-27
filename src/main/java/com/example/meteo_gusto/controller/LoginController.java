package com.example.meteo_gusto.controller;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.CredenzialiBean;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.convertitore.ConvertitoreModelBean;

public class LoginController {
    private static final DAOFactoryFacade daoFactoryFacade= DAOFactoryFacade.getInstance();
    private static final PersonaDAO personaDAO= daoFactoryFacade.getPersonaDAO();

    public PersonaBean accedi(CredenzialiBean credenzialiBean) throws EccezioneDAO, ValidazioneException {
        try {
            Persona credenzialiPersona= new Persona(
                    credenzialiBean.getEmail(),
                    credenzialiBean.getPassword()
            );

            Persona persona = personaDAO.login(credenzialiPersona);

            if (persona == null) {
                throw new ValidazioneException("Credenziali errate");
            }

            Sessione.getInstance().login(persona);

            return ConvertitoreModelBean.personaModelInBean(persona);

        } catch (EccezioneDAO | ValidazioneException e) {
            throw new EccezioneDAO("Errore durante il login dell'utente", e);
        }
    }



}
