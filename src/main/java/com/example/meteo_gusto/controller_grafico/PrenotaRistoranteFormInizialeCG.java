package com.example.meteo_gusto.controller_grafico;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PrenotaRistoranteFormInizialeCG {

    @FXML
    private DatePicker campoData;
    @FXML
    private TextField campoOra;
    @FXML
    private TextField campoNumeroPersone;
    @FXML
    private TextField campoCitta;

    @FXML
    private void clickEsci(MouseEvent evento) {
        boolean risposta = GestoreScena.mostraAlertConConferma("Conferma uscita", "Sei sicuro di voler uscire ?");
        if (risposta) {
            //inserire il termine della sessione.
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    private void clickListaPrenotazioni(MouseEvent evento) {GestoreScena.cambiaScena("/Login.fxml", evento);   /* inserire la scena corretta*/}



    @FXML
    private void clickProfiloPersonale(MouseEvent evento) {GestoreScena.cambiaScena("/Login.fxml", evento);   /* inserire la scena corretta*/}

}
