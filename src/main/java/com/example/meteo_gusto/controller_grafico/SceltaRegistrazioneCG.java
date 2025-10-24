package com.example.meteo_gusto.controller_grafico;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class SceltaRegistrazioneCG {

    @FXML
    private void clickUtente(ActionEvent evento) {
        GestoreScena.cambiaScena("/RegistrazioneUtente.fxml", evento);
    }

    @FXML
    private void clickRistoratore(ActionEvent evento) {GestoreScena.cambiaScena("/RegistrazioneRistoratore.fxml", evento);}

    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta=GestoreScena.mostraAlertConConferma("Attenzione","Sei sicuro di voler tornare al Login ?");
        if(risposta) {GestoreScena.cambiaScena("/Login.fxml", evento);}
    }
}
