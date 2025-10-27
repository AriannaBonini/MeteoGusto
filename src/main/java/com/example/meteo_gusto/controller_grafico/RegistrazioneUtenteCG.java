package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.RegistrazioneUtenteBean;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    RegistrazioneController registrazioneController= new RegistrazioneController();
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneUtenteCG.class.getName());



    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {
            PersonaBean utenteBean= new PersonaBean(campoNome.getText().trim(),campoCognome.getText().trim(),campoTelefono.getText().trim(), campoEmail.getText().trim(), campoPassword.getText().trim());
            RegistrazioneUtenteBean registrazioneUtenteBean= new RegistrazioneUtenteBean(utenteBean,checkBoxMaggiorenne.isSelected(),checkBoxTerminiPrivacy.isSelected());

            registrazioneController.registraUtente(registrazioneUtenteBean);

            GestoreScena.mostraAlertSenzaConferma("Successo","La registrazione è andata a buon fine ! ");
            GestoreScena.cambiaScena("/Login.fxml", evento);

        }catch (ValidazioneException | EccezioneDAO e) {
            infoErrore.setText(e.getMessage());
            logger.error("Errore registrazione: ", e);
        }
    }


    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta = GestoreScena.mostraAlertConConferma("Attenzione", "Sei sicuro di voler tornare al Login ?");
        if (risposta) {
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }
}
