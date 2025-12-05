import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaOraria;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;
import com.example.meteo_gusto.utilities.convertitore.ConvertitorePersona;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/* Testa il metodo PrenotaRistoranteController.prenotaRistorante(...) */
class PrenotaRistoranteControllerTest {

    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private final PrenotazioneDAO prenotazioneDAO= daoFactoryFacade.getPrenotazioneDAO();
    private static Persona persona;
    private static AmbienteBean ambienteBean;
    private static PersonaBean personaBean;
    private static RistoranteBean ristoranteBean;

    @BeforeAll
    static void init() throws IllegalStateException, EccezioneDAO, ValidazioneException {
        daoFactoryFacade.initTipoPersistenza(TipoPersistenza.MEMORIA);

        PersonaDAO personaDAO = DAOFactoryFacade.getInstance().getPersonaDAO();
        persona = new Persona("Mario", "Verdi", "32087451258", "MarioRossi@gmail.com", "TestLogin", TipoPersona.UTENTE, null);
        personaDAO.registraPersona(persona);

        personaBean= ConvertitorePersona.personaModelInBean(persona);
        List<AmbienteBean> listaAmbienti= new ArrayList<>();
        ambienteBean= new AmbienteBean();
        ambienteBean.setAmbienteId(1);
        ambienteBean.setAmbiente(TipoAmbiente.INTERNO);
        listaAmbienti.add(ambienteBean);

        ristoranteBean= new RistoranteBean();
        GiorniEOrariBean giorniEOrariBean= new GiorniEOrariBean(LocalTime.parse("12:00"),LocalTime.parse("14:00"),
                LocalTime.parse("19:30"), LocalTime.parse("23:30"),null);
        ristoranteBean.setGiorniEOrari(giorniEOrariBean);
        ristoranteBean.setAmbiente(listaAmbienti);
    }

    @BeforeEach
    void setUp() throws EccezioneDAO{
        Ambiente ambiente = new Ambiente();
        ambiente.setTipoAmbiente(TipoAmbiente.INTERNO);
        ambiente.setIdAmbiente(1);

        Prenotazione prenotazione = new Prenotazione(LocalDate.parse("2026-07-20"), LocalTime.parse("20:00"),
                4, ambiente, persona, FasciaOraria.CENA);

        prenotazioneDAO.inserisciPrenotazione(prenotazione);
    }

    @Test
    void prenotaRistorante_successo() throws EccezioneDAO {
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean(LocalTime.parse("12:30"), LocalDate.parse("2026-07-20"),
                4, personaBean, ambienteBean, FasciaOraria.PRANZO);
        boolean result = prenotaRistoranteController.prenotaRistorante(prenotazioneBean, ristoranteBean);
        assertTrue(result, "La prenotazione dovrebbe essere inserita correttamente");
    }

    @Test
    void prenotaRistorante_duplicazione() {
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean(LocalTime.parse("20:00"), LocalDate.parse("2026-07-20"),
                4, personaBean, ambienteBean, FasciaOraria.CENA);

        EccezioneDAO exception = assertThrows(EccezioneDAO.class, () ->
                prenotaRistoranteController.prenotaRistorante(prenotazioneBean, ristoranteBean)
        );

        assertEquals("Esiste già una prenotazione per la data e l'ora scelta", exception.getMessage());
    }
}
