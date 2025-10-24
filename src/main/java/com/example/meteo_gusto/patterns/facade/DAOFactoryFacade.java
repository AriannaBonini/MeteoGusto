package com.example.meteo_gusto.patterns.facade;


import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.patterns.factory.PersonaDAOFactory;
import com.example.meteo_gusto.patterns.factory.RistoranteDAOFactory;

public class DAOFactoryFacade {
    private static DAOFactoryFacade istanza;
    private TipoPersistenza tipoPersistenza;
    private PersonaDAO personaDAO;
    private RistoranteDAO ristoranteDAO;

    private DAOFactoryFacade() {/* Costruttore privato per impedire la creazione di istanze */}

    public static synchronized DAOFactoryFacade getInstance() {
        if (istanza == null) {
            istanza = new DAOFactoryFacade();
        }
        return istanza;
    }

    public void setTipoPersistenza(TipoPersistenza tipoPersistenza) {
        this.tipoPersistenza=tipoPersistenza;
    }

    public PersonaDAO getPersonaDAO() {
        if(personaDAO ==null) {
            PersonaDAOFactory personaDAOFactory = new PersonaDAOFactory();
            personaDAO = personaDAOFactory.getPersonaDAO(tipoPersistenza);
        }
        return personaDAO;
    }

    public RistoranteDAO getRistoranteDAO() {
        if(ristoranteDAO ==null) {
            RistoranteDAOFactory ristoranteDAOFactory = new RistoranteDAOFactory();
            ristoranteDAO = ristoranteDAOFactory.getRistoranteDAO(tipoPersistenza);
        }
        return ristoranteDAO;
    }


}
