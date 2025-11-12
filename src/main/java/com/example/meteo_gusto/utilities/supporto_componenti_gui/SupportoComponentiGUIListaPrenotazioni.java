package com.example.meteo_gusto.utilities.supporto_componenti_gui;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SupportoComponentiGUIListaPrenotazioni {

    private SupportoComponentiGUIListaPrenotazioni(){ /* COSTRUTTORE VUOTO */}

    // --- VBOX ----------------------------------------------------

    public static VBox creaVBoxPrenotazione(VBox vBox) {
        VBox nuovaPrenotazione = new VBox();
        nuovaPrenotazione.setAlignment(vBox.getAlignment() != null ? vBox.getAlignment() : Pos.CENTER_LEFT);
        nuovaPrenotazione.setSpacing(vBox.getSpacing());
        nuovaPrenotazione.setPadding(vBox.getPadding());
        nuovaPrenotazione.setStyle(vBox.getStyle());
        nuovaPrenotazione.setPrefHeight(vBox.getPrefHeight());
        nuovaPrenotazione.setPrefWidth(vBox.getPrefWidth());
        VBox.setMargin(nuovaPrenotazione, VBox.getMargin(vBox));
        return nuovaPrenotazione;
    }

    // --- HBOX ----------------------------------------------------

    public static HBox copiaHBox(HBox modello) {
        HBox nuovoHBox = new HBox();
        nuovoHBox.setAlignment(modello.getAlignment() != null ? modello.getAlignment() : Pos.CENTER_LEFT);
        nuovoHBox.setSpacing(modello.getSpacing());
        nuovoHBox.setPadding(modello.getPadding());
        nuovoHBox.setStyle(modello.getStyle());
        VBox.setMargin(nuovoHBox, VBox.getMargin(modello));
        return nuovoHBox;
    }


    // --- LABEL ----------------------------------------------------

    public static Label creaLabel(Label modello, String testo) {
        Label nuovaLabel = new Label();
        nuovaLabel.setText(testo);
        nuovaLabel.setFont(modello.getFont());
        nuovaLabel.setTextFill(modello.getTextFill());
        nuovaLabel.setWrapText(modello.isWrapText());
        nuovaLabel.setPadding(modello.getPadding());
        nuovaLabel.setPrefWidth(modello.getPrefWidth());
        nuovaLabel.setPrefHeight(modello.getPrefHeight());
        nuovaLabel.setStyle(modello.getStyle());
        HBox.setMargin(nuovaLabel, HBox.getMargin(modello));
        return nuovaLabel;
    }



    // --- BOTTONE ----------------------------------------------------
    public static Button creaBottoneScopriDiPiu(Button modello) {
        Button nuovoBottone = new Button();
        nuovoBottone.setText(modello.getText());
        nuovoBottone.setFont(modello.getFont());
        nuovoBottone.setTextFill(modello.getTextFill());
        nuovoBottone.setStyle(modello.getStyle());
        nuovoBottone.setPadding(modello.getPadding());

        nuovoBottone.setPrefWidth(150);
        nuovoBottone.setPrefHeight(modello.getPrefHeight());

        HBox.setHgrow(nuovoBottone, Priority.ALWAYS);

        HBox.setMargin(nuovoBottone, HBox.getMargin(modello));
        return nuovoBottone;
    }



}
