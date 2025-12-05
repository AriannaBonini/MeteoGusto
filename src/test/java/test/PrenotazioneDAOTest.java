package test;

import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.FasciaOraria;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;


/* Testa il metodo PrenotazioneDAOMySql.contaNotificheAttiveUtente(...) */
class PrenotazioneDAOTest {

    private static final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private final PrenotazioneDAO prenotazioneDAO= daoFactoryFacade.getPrenotazioneDAO();
    private static Persona persona;

    @BeforeAll
    static void init() throws IllegalStateException, EccezioneDAO{

        daoFactoryFacade.initTipoPersistenza(TipoPersistenza.MEMORIA);
        PersonaDAO personaDAO = DAOFactoryFacade.getInstance().getPersonaDAO();

        persona = new Persona("Mario", "Verdi", "32087451258", "MarioRossi@gmail.com", "TestLogin", TipoPersona.UTENTE, null);
        personaDAO.registraPersona(persona);
    }

    @BeforeEach
    void setUp() throws EccezioneDAO {
        Ambiente ambiente= new Ambiente();
        ambiente.setIdAmbiente(1);
        Prenotazione prenotazione1=new Prenotazione(LocalDate.parse("2026-07-20"), LocalTime.parse("20:00"), 4,ambiente,persona, FasciaOraria.CENA);

        ambiente.setIdAmbiente(2);
        Prenotazione prenotazione2=new Prenotazione(LocalDate.parse("2026-07-10"), LocalTime.parse("13:00"), 4,ambiente,persona, FasciaOraria.PRANZO);

        ambiente.setIdAmbiente(2);
        Prenotazione prenotazione3=new Prenotazione(LocalDate.parse("2026-06-21"), LocalTime.parse("21:30"), 4,ambiente,persona, FasciaOraria.CENA);

        prenotazioneDAO.inserisciPrenotazione(prenotazione1);
        prenotazioneDAO.inserisciPrenotazione(prenotazione2);
        prenotazioneDAO.inserisciPrenotazione(prenotazione3);

    }

    @Test
    void contaNotificheAttiveUtente_controllaNumeroNotifiche() throws EccezioneDAO {
        Prenotazione prenotazione = prenotazioneDAO.contaNotificheAttiveUtente(persona);
        assertEquals(3, prenotazione.getNumeroNotifiche());
    }

}
