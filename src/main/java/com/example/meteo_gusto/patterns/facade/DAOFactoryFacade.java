package com.example.meteo_gusto.patterns.facade;


import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.patterns.factory.*;

public class DAOFactoryFacade {
    private static DAOFactoryFacade istanza= null;
    private TipoPersistenza tipoPersistenza=null;
    private PersonaDAO personaDAO;
    private RistoranteDAO ristoranteDAO;
    private GiornoChiusuraDAO giornoChiusuraDAO;
    private DietaDAO dietaDAO;
    private AmbienteDAO ambienteDAO;
    private PrenotazioneDAO prenotazioneDAO;
    private RecensioneDAO recensioneDAO;

    private DAOFactoryFacade() {}
    public static synchronized DAOFactoryFacade getInstance() {
        if (istanza== null) {
            istanza = new DAOFactoryFacade();
        }
        return istanza;
    }

    public synchronized void initTipoPersistenza(TipoPersistenza tipoPersistenza) {
          if (tipoPersistenza == null) {
            throw new IllegalArgumentException("Il tipo di persistenza non può essere null." );
           }
        if (this.tipoPersistenza != null) {
            throw new IllegalStateException("Tipologia di persistenza già impostata");
        }
        this.tipoPersistenza = tipoPersistenza;
    }

    private void verificaTipoPersistenza() {
    if (tipoPersistenza == null) {
        throw new IllegalStateException(
            "Tipo di persistenza non inizializzato. "
            + "Invocare initTipoPersistenza() prima di richiedere un DAO.");
        }
     }

    public PersonaDAO getPersonaDAO() {
         verificaTipoPersistenza();

        if(personaDAO ==null) {
            PersonaDAOFactory personaDAOFactory = new PersonaDAOFactory();
            personaDAO = personaDAOFactory.getPersonaDAO(tipoPersistenza);
        }
        return personaDAO;
    }

    public RistoranteDAO getRistoranteDAO() {
         verificaTipoPersistenza();

        if(ristoranteDAO ==null) {
            RistoranteDAOFactory ristoranteDAOFactory = new RistoranteDAOFactory();
            ristoranteDAO = ristoranteDAOFactory.getRistoranteDAO(tipoPersistenza);
        }
        return ristoranteDAO;
    }

    public GiornoChiusuraDAO getGiornoChiusuraDAO() {
         verificaTipoPersistenza();

        if(giornoChiusuraDAO ==null) {
            GiornoChiusuraDAOFactory giornoChiusuraDAOFactory = new GiornoChiusuraDAOFactory();
            giornoChiusuraDAO = giornoChiusuraDAOFactory.getGiornoChiusuraDAO(tipoPersistenza);
        }
        return giornoChiusuraDAO;
    }

    public DietaDAO getDietaDAO() {
         verificaTipoPersistenza();

        if(dietaDAO ==null) {
            DietaDAOFactory dietaDAOFactory = new DietaDAOFactory();
            dietaDAO =dietaDAOFactory.getDietaDAO(tipoPersistenza);
        }
        return dietaDAO;
    }

    public AmbienteDAO getAmbienteDAO() {
         verificaTipoPersistenza();

        if(ambienteDAO ==null) {
            AmbienteDAOFactory ambienteDAOFactory = new AmbienteDAOFactory();
            ambienteDAO = ambienteDAOFactory.getAmbienteDAO(tipoPersistenza);
        }
        return ambienteDAO;
    }


    public PrenotazioneDAO getPrenotazioneDAO() {
         verificaTipoPersistenza();

        if(prenotazioneDAO ==null) {
            PrenotazioneDAOFactory prenotazioneDAOFactory= new PrenotazioneDAOFactory();
            prenotazioneDAO = prenotazioneDAOFactory.getPrenotazioneDAO(tipoPersistenza);
        }
        return prenotazioneDAO;
    }

    public RecensioneDAO getRecensioneDAO() {
         verificaTipoPersistenza();

        if(recensioneDAO ==null) {
            RecensioneDAOFactory recensioneDAOFactory= new RecensioneDAOFactory();
            recensioneDAO  = recensioneDAOFactory.getRecensioneDAO(tipoPersistenza);
        }
        return recensioneDAO;
    }

}
