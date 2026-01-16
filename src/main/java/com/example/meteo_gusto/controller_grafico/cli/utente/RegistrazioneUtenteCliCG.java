package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.RegistrazionePersonaBean;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreInput;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class RegistrazioneUtenteCliCG implements InterfacciaCLI {
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneUtenteCliCG.class.getName());
    private final RegistrazioneController registrazioneController= new RegistrazioneController();

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> registrazione();
                    case 2 -> GestoreScenaCLI.vaiAllaSceltaRegistrazione();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Errore ",e.getMessage());
            }
        }
        GestoreScenaCLI.login();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("REGISTRAZIONE UTENTE ");

        GestoreOutput.mostraGraficaMenu("Prosegui con la registrazione", "Torna alla scelta del tipo di registrazione " ,"Torna al login");
        return opzioneScelta(1,3);
    }

    private void registrazione() {
        try {
            registrazioneController.registraUtente(Objects.requireNonNull(prendiDatiRegistrazione()));
            GestoreOutput.mostraAvvertenza("Successo", "La registrazione è andata a buon fine!");

        } catch (EccezioneDAO | ValidazioneException e) {
            logger.error("Errore registrazione: ", e);
        }
    }

    private RegistrazionePersonaBean prendiDatiRegistrazione() throws ValidazioneException{
        RegistrazionePersonaBean registrazionePersonaBean = new RegistrazionePersonaBean();
        PersonaBean personaBean = new PersonaBean();

        GestoreOutput.stampaTitolo("INFORMAZIONI PERSONALI");

        personaBean.setNome(GestoreInput.leggiStringaDaInput("Inserisci il nome :"));
        personaBean.setCognome(GestoreInput.leggiStringaDaInput("Inserisci il cognome :"));
        personaBean.setTelefono(GestoreInput.leggiStringaDaInput("Inserisci il telefono :"));
        personaBean.setEmail(GestoreInput.leggiStringaDaInput("Inserisci la email : "));
        personaBean.setPassword(GestoreInput.leggiStringaDaInput("Inserisci la password : "));
        personaBean.setTipoPersona(TipoPersona.UTENTE);

        registrazionePersonaBean.setPersona(personaBean);

        GestoreOutput.stampaMessaggio("Confermi di essere maggiorenne ? ");
        GestoreOutput.stampaMessaggio("1) Si " + "2) No");
        if(opzioneScelta(1,2)==2) {
            return null;
        }

        GestoreOutput.stampaMessaggio("Accetti i termini e la privacy ? ");
        GestoreOutput.stampaMessaggio("1) Si " + "2) No");
        if(opzioneScelta(1,2)==2) {
            return null;
        }

        return registrazionePersonaBean;

    }

}


