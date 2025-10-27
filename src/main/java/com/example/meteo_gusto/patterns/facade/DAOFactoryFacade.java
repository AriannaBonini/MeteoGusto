package com.example.meteo_gusto.patterns.facade;


import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.patterns.factory.*;

public class DAOFactoryFacade {
    private static DAOFactoryFacade istanza;
    private TipoPersistenza tipoPersistenza;
    private PersonaDAO personaDAO;
    private RistoranteDAO ristoranteDAO;
    private GiorniChiusuraDAO giorniChiusuraDAO;
    private DietaDAO dietaDAO;
    private DisponibilitaDAO disponibilitaDAO;


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

    public GiorniChiusuraDAO getGiorniChiusuraDAO() {
        if(giorniChiusuraDAO ==null) {
            GiorniChiusuraDAOFactory giorniChiusuraDAOFactory = new GiorniChiusuraDAOFactory();
            giorniChiusuraDAO = giorniChiusuraDAOFactory.getGiorniChiusuraDAO(tipoPersistenza);
        }
        return giorniChiusuraDAO;
    }

    public DietaDAO getDietaDAO() {
        if(dietaDAO ==null) {
            DietaDAOFactory dietaDAOFactory = new DietaDAOFactory();
            dietaDAO =dietaDAOFactory.getDietaDAO(tipoPersistenza);
        }
        return dietaDAO;
    }

    public DisponibilitaDAO getDisponibilitaDAO() {
        if(disponibilitaDAO ==null) {
            DisponibilitaDAOFactory disponibilitaDAOFactory = new DisponibilitaDAOFactory();
            disponibilitaDAO =disponibilitaDAOFactory.getDisponibilitaDAO(tipoPersistenza);
        }
        return disponibilitaDAO;
    }


}
