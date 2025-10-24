package com.example.meteo_gusto.controller_grafico;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginCG {
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoPassword;
    @FXML
    private Label infoErrore;


     @FXML
    private void clickAccedi(ActionEvent evento) {}

     @FXML
    private void clickRegistrati(ActionEvent evento) { GestoreScena.cambiaScena("/SceltaTipoRegistrazione.fxml", evento); }

    @FXML
    private void clickPasswordDimenticata() { infoErrore.setText("Procedura del recupero password non implementata"); }

    
}
