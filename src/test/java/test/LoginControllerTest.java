package test;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.controller.LoginController;
import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/* Testa il metodo LoginController.accedi(...) */
class LoginControllerTest {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private LoginController controller;

    @BeforeAll
    static void init() throws IllegalStateException, EccezioneDAO{

        daoFactoryFacade.initTipoPersistenza(TipoPersistenza.MEMORIA);
        PersonaDAO personaDAO = DAOFactoryFacade.getInstance().getPersonaDAO();

        Persona persona = new Persona("Mario", "Verdi", "32087451258", "MarioRossi@gmail.com", "TestLogin");
        personaDAO.registraPersona(persona);
    }

    @BeforeEach
    void setUp() {
        controller = new LoginController();
    }

    @Test
    void accedi_Successo() throws Exception {
        PersonaBean credenziali = new PersonaBean();
        credenziali.setEmail("MarioRossi@gmail.com");
        credenziali.setPassword("TestLogin");

        PersonaBean risultato = controller.accedi(credenziali);
        assertNotNull(risultato);
        assertEquals(TipoPersona.UTENTE.getId(), risultato.getTipoPersona().getId());
    }

    @Test
    void accedi_PasswordErrata() throws Exception {
        PersonaBean credenziali = new PersonaBean();
        credenziali.setEmail("MarioRossi@gmail.com");
        credenziali.setPassword("PasswordNonValida");

        PersonaBean risultato = controller.accedi(credenziali);
        assertNull(risultato);
    }

    @Test
    void accedi_EmailErrata() throws Exception {
        PersonaBean credenziali = new PersonaBean();
        credenziali.setPassword("TestLogin");
        credenziali.setEmail("utenteNonPresente@gmail.com");

        PersonaBean risultato = controller.accedi(credenziali);
        assertNull(risultato);
    }
}
