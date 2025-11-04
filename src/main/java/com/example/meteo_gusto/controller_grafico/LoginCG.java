package com.example.meteo_gusto.controller_grafico;


import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.controller.LoginController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCG {
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoPassword;
    @FXML
    private Label infoErrore;

    LoginController loginController= new LoginController();
    private static final Logger logger = LoggerFactory.getLogger(LoginCG.class.getName());

    @FXML
    private void clickAccedi(ActionEvent evento) {
        try {
            PersonaBean credenzialiPersonaBean = new PersonaBean(campoEmail.getText(),
                    campoPassword.getText());

            PersonaBean personaBean = loginController.accedi(credenzialiPersonaBean);

            if(personaBean==null){
                infoErrore.setText("Credenziali Errate");
            }

            assert personaBean != null;
            if(personaBean.getTipoPersona().equals(TipoPersona.UTENTE)) {
                GestoreScena.cambiaScena("/PrenotaRistoranteFormIniziale.fxml", evento);
            }else{
                infoErrore.setText("Ristoratore presente");
                // carichiamo quella del ristoratore.
            }


        }catch (ValidazioneException | EccezioneDAO e) {
            infoErrore.setText(e.getMessage());
            logger.error("Errore di accesso: ", e);
        }

    }

     @FXML
    private void clickRegistrati(ActionEvent evento) { GestoreScena.cambiaScena("/SceltaTipoRegistrazione.fxml", evento); }

    @FXML
    private void clickPasswordDimenticata() { infoErrore.setText("Procedura del recupero password non implementata"); }

}
