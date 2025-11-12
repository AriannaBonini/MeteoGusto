package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.controller.LoginController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class LoginCliCG implements InterfacciaCLI{

    private final LoginController loginController= new LoginController();

    @Override
    public void start() {
        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> login();
                    case 2 -> GestoreScenaCLI.vaiAllaSceltaRegistrazione();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException | EccezioneDAO e) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        System.exit(0);
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("BENVENUTO SU METEOGUSTO");

        GestoreOutput.mostraGraficaMenu("Login", "Registrazione","Esci");
        return opzioneScelta(1,3);
    }


    private void login() throws ValidazioneException, EccezioneDAO {
        PersonaBean credenzialiPersona= new PersonaBean();

        credenzialiPersona.setEmail(GestoreInput.leggiStringaDaInput("Inserisci la tua email : "));
        credenzialiPersona.setPassword(GestoreInput.leggiStringaDaInput("Inserisci la tua password : "));

        PersonaBean personaBean = loginController.accedi(credenzialiPersona);

        if(personaBean==null){
            GestoreOutput.mostraAvvertenza("Errore","Credenziali non valide");
            return;
        }

        if(personaBean.getTipoPersona().equals(TipoPersona.UTENTE)) {
            GestoreOutput.stampaMessaggio("Caricamento Home... ");
            GestoreScenaCLI.viaAllaHomeUtente();

        }else{

            GestoreOutput.stampaMessaggio("Caricamento Home... ");
            GestoreScenaCLI.vaiAllaHomeRistoratore();
        }

    }


}

