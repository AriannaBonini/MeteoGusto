package com.example.meteo_gusto.patterns.facade;


import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.patterns.factory.*;

public class DAOFactoryFacade {
    private static DAOFactoryFacade istanza;
    private TipoPersistenza tipoPersistenza;
    private PersonaDAO personaDAO;
    private RistoranteDAO ristoranteDAO;
    private GiornoChiusuraDAO giornoChiusuraDAO;
    private DietaDAO dietaDAO;
    private AmbienteDAO ambienteDAO;
    private PrenotazioneDAO prenotazioneDAO;
    private RecensioneDAO recensioneDAO;


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

    public GiornoChiusuraDAO getGiornoChiusuraDAO() {
        if(giornoChiusuraDAO ==null) {
            GiornoChiusuraDAOFactory giornoChiusuraDAOFactory = new GiornoChiusuraDAOFactory();
            giornoChiusuraDAO = giornoChiusuraDAOFactory.getGiornoChiusuraDAO(tipoPersistenza);
        }
        return giornoChiusuraDAO;
    }

    public DietaDAO getDietaDAO() {
        if(dietaDAO ==null) {
            DietaDAOFactory dietaDAOFactory = new DietaDAOFactory();
            dietaDAO =dietaDAOFactory.getDietaDAO(tipoPersistenza);
        }
        return dietaDAO;
    }

    public AmbienteDAO getAmbienteDAO() {
        if(ambienteDAO ==null) {
            AmbienteDAOFactory ambienteDAOFactory = new AmbienteDAOFactory();
            ambienteDAO = ambienteDAOFactory.getAmbienteDAO(tipoPersistenza);
        }
        return ambienteDAO;
    }


    public PrenotazioneDAO getPrenotazioneDAO() {
        if(prenotazioneDAO ==null) {
            PrenotazioneDAOFactory prenotazioneDAOFactory= new PrenotazioneDAOFactory();
            prenotazioneDAO = prenotazioneDAOFactory.getPrenotazioneDAO(tipoPersistenza);
        }
        return prenotazioneDAO;
    }

    public RecensioneDAO getRecensioneDAO() {
        if(recensioneDAO ==null) {
            RecensioneDAOFactory recensioneDAOFactory= new RecensioneDAOFactory();
            recensioneDAO  = recensioneDAOFactory.getRecensioneDAO(tipoPersistenza);
        }
        return recensioneDAO;
    }



}
