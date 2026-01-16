package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.RegistrazionePersonaBean;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoCheckBoxCss;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RegistrazioneUtenteCG {
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCognome;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoPassword;
    @FXML
    private CheckBox checkBoxMaggiorenne;
    @FXML
    private CheckBox checkBoxTerminiPrivacy;
    @FXML
    private Label infoErrore;

    private final RegistrazioneController registrazioneController= new RegistrazioneController();
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneUtenteCG.class.getName());

    public void initialize() {
        SupportoCheckBoxCss.inizializzaCheckBoxMultipli(List.of(checkBoxMaggiorenne,checkBoxTerminiPrivacy));
    }

    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {
            RegistrazionePersonaBean registrazionePersonaBean = creaRegistrazioneUtenteBean();

            registrazioneController.registraUtente(registrazionePersonaBean);

            GestoreScena.mostraAlertSenzaConferma("Successo", "La registrazione è andata a buon fine!");
            GestoreScena.cambiaScena("/Login.fxml", evento);

        } catch (EccezioneDAO | ValidazioneException e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
            logger.error("Errore registrazione: ", e);
        }
    }

    private RegistrazionePersonaBean creaRegistrazioneUtenteBean() throws ValidazioneException{
        RegistrazionePersonaBean registrazionePersonaBean = new RegistrazionePersonaBean();
        PersonaBean personaBean = new PersonaBean();
        personaBean.setCognome(campoCognome.getText().trim());
        personaBean.setNome(campoNome.getText().trim());
        personaBean.setTelefono(campoTelefono.getText().trim());
        personaBean.setEmail(campoEmail.getText().trim());
        personaBean.setPassword(campoPassword.getText().trim());

        registrazionePersonaBean.setPersona(personaBean);
        registrazionePersonaBean.setMaggiorenne(checkBoxMaggiorenne.isSelected());
        registrazionePersonaBean.setAccettaTermini(checkBoxTerminiPrivacy.isSelected());

        return registrazionePersonaBean;
    }




    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta = GestoreScena.mostraAlertConConferma("Attenzione", "Sei sicuro di voler tornare al Login ?");
        if (risposta) {
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }
}
