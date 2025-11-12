package com.example.meteo_gusto.controller_grafico.gui;


import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.controller_grafico.gui.ristoratore.RegistrazioneRistoratoreCG;
import com.example.meteo_gusto.controller_grafico.gui.utente.RegistrazioneUtenteCG;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class SceltaRegistrazioneCG {

    @FXML
    private void clickUtente(ActionEvent evento) {
        GestoreScena.cambiaScenaConParametri("/RegistrazioneUtente.fxml", evento,(RegistrazioneUtenteCG controller) -> controller.setTipoRegistrazione(TipoPersona.UTENTE));}

    @FXML
    private void clickRistoratore(ActionEvent evento) {GestoreScena.cambiaScenaConParametri("/RegistrazioneRistoratore.fxml", evento, (RegistrazioneRistoratoreCG controller) -> controller.setTipoRegistrazione(TipoPersona.RISTORATORE));}

    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta=GestoreScena.mostraAlertConConferma("Attenzione","Sei sicuro di voler tornare al Login ?");
        if(risposta) {GestoreScena.cambiaScena("/Login.fxml", evento);}
    }
}
