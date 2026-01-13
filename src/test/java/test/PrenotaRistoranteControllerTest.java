package test;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrenotazioneEsistenteException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.*;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;
import com.example.meteo_gusto.sessione.Sessione;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/* Testa il metodo PrenotaRistoranteController.prenotaRistorante(...) */
class PrenotaRistoranteControllerTest {

    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private final PrenotazioneDAO prenotazioneDAO= daoFactoryFacade.getPrenotazioneDAO();
    private final AmbienteDAO ambienteDAO=daoFactoryFacade.getAmbienteDAO();
    private static Persona persona;

    @BeforeAll
    static void init() throws IllegalStateException, EccezioneDAO {
        daoFactoryFacade.initTipoPersistenza(TipoPersistenza.MEMORIA);

        PersonaDAO personaDAO = daoFactoryFacade.getPersonaDAO();
        persona = new Persona("Mario", "Rossi", "32087451258", "MarioRossi@gmail.com", "TestLogin");
        personaDAO.registraPersona(persona);

        Sessione.getInstance().login(persona);

        Posizione posizione= new Posizione("Via dell'ermellino, 179", "Roma","00149");
        Set<GiorniSettimana> giorniChiusura= new HashSet<>();
        giorniChiusura.add(GiorniSettimana.LUNEDI);

        GiorniEOrari giorniEOrari= new GiorniEOrari(LocalTime.parse("12:00"),LocalTime.parse("14:00"),LocalTime.parse("19:30"),LocalTime.parse("22:30"),giorniChiusura);

        RistoranteDAO ristoranteDAO= daoFactoryFacade.getRistoranteDAO();
        Ristorante ristorante= new Ristorante("01111111111","La Cucinotta","3208150021", TipoCucina.GRECA, FasciaPrezzoRistorante.ECONOMICO,posizione,giorniEOrari);
        Persona ristoratore = new Persona("Carlo", "Meli", "32087451000", "CarloMeli@gmail.com", "TestLogin");
        ristorante.setRistoratore(ristoratore.getEmail());
        ristoranteDAO.registraRistorante(ristorante);



        AmbienteDAO ambienteDAO=daoFactoryFacade.getAmbienteDAO();
        List<Ambiente> ambientiRistorante= new ArrayList<>();
        Ambiente ambiente= new Ambiente(TipoAmbiente.INTERNO,"01111111111",10 );
        ambientiRistorante.add(ambiente);

        ambienteDAO.registraDisponibilita(ambientiRistorante);

    }

    @BeforeEach
    void setUp() throws EccezioneDAO{
        Ambiente ambiente= new Ambiente(TipoAmbiente.INTERNO,"01111111111",10 );
        ambiente.setIdAmbiente((ambienteDAO.cercaIdAmbiente(ambiente)).getIdAmbiente());

        Prenotazione prenotazione = new Prenotazione(LocalDate.parse("2026-07-20"), LocalTime.parse("20:00"),
                4, ambiente, persona.getEmail(), FasciaOraria.CENA);

        prenotazioneDAO.inserisciPrenotazione(prenotazione);
    }

    @Test
    void prenotaRistorante_successo() throws EccezioneDAO, PrenotazioneEsistenteException, ValidazioneException {

        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

        prenotazioneBean.setOra(LocalTime.parse("12:30"));
        prenotazioneBean.setData(LocalDate.parse("2026-07-20"));
        prenotazioneBean.setNumeroPersone(4);
        prenotazioneBean.setAmbiente(Collections.singletonList(TipoAmbiente.INTERNO.getId()));
        prenotazioneBean.setNote("Assenti");

        RistoranteBean ristoranteBean= new RistoranteBean();
        ristoranteBean.setPartitaIVA("01111111111");

        prenotazioneBean.setRistorante(ristoranteBean);

        boolean result = prenotaRistoranteController.prenotaRistorante(prenotazioneBean);
        assertTrue(result, "La prenotazione dovrebbe essere inserita correttamente");
    }

    @Test
    void prenotaRistorante_duplicazione() throws ValidazioneException {

        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

        prenotazioneBean.setOra(LocalTime.parse("20:00"));
        prenotazioneBean.setData(LocalDate.parse("2026-07-20"));
        prenotazioneBean.setNumeroPersone(4);
        prenotazioneBean.setAmbiente(Collections.singletonList(TipoAmbiente.INTERNO.getId()));
        prenotazioneBean.setNote("Assenti");

        RistoranteBean ristoranteBean= new RistoranteBean();
        ristoranteBean.setPartitaIVA("01111111111");

        prenotazioneBean.setRistorante(ristoranteBean);

        PrenotazioneEsistenteException exception = assertThrows(PrenotazioneEsistenteException.class, () ->
                prenotaRistoranteController.prenotaRistorante(prenotazioneBean)
        );

        assertEquals("Esiste già una prenotazione per la data e l'ora scelta", exception.getMessage());
    }
}
