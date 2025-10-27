package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.RegistrazioneUtenteBean;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;
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
    private TipoPersona tipoPersona;

    public void setTipoRegistrazione(TipoPersona tipoPersona) { this.tipoPersona=tipoPersona;}


    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {
            RegistrazioneUtenteBean registrazioneUtenteBean = creaRegistrazioneUtenteBean();

            registrazioneController.registraUtente(registrazioneUtenteBean);

            GestoreScena.mostraAlertSenzaConferma("Successo", "La registrazione è andata a buon fine!");
            GestoreScena.cambiaScena("/Login.fxml", evento);

        } catch (ValidazioneException | EccezioneDAO e) {
            infoErrore.setText(e.getMessage());
            logger.error("Errore registrazione: ", e);
        }
    }

    private RegistrazioneUtenteBean creaRegistrazioneUtenteBean() throws ValidazioneException {
        PersonaBean personaBean = new PersonaBean();
        personaBean.setNome(campoNome.getText().trim());
        personaBean.setCognome(campoCognome.getText().trim());
        personaBean.setTelefono(campoTelefono.getText().trim());
        personaBean.setEmail(campoEmail.getText().trim());
        personaBean.setPassword(campoPassword.getText().trim());
        personaBean.setTipoPersona(tipoPersona);

        RegistrazioneUtenteBean registrazioneUtenteBean= new RegistrazioneUtenteBean();
        registrazioneUtenteBean.setPersona(personaBean);
        registrazioneUtenteBean.setMaggiorenne(checkBoxMaggiorenne.isSelected());
        registrazioneUtenteBean.setAccettaTermini(checkBoxTerminiPrivacy.isSelected());

        return registrazioneUtenteBean;
    }



    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta = GestoreScena.mostraAlertConConferma("Attenzione", "Sei sicuro di voler tornare al Login ?");
        if (risposta) {
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }
}
