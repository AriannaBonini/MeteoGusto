package com.example.meteo_gusto.utilities;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class AlertPersonalizzato {
    private boolean risposta = false;
    private static final String ROBOTO = "Roboto";
    private static final int SECONDI_AUTO_CHIUSURA=3;
    private static final String COLORE_PRIMARIO = "#e97627";
    private static final String COLORE_HOVER = "#d16622";
    private static final String STILE_ROOT = "-fx-background-color: white; -fx-border-color: #e97627; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;";
    private static final String STILE_BOTTONE_BASE = "-fx-background-radius: 8px; -fx-border-radius: 8px; -fx-cursor: hand;";
    private static final String STILE_BOTTONE_NORMALE = "-fx-background-color: " + COLORE_PRIMARIO + "; -fx-text-fill: white; -fx-font-weight: bold; " + STILE_BOTTONE_BASE;
    private static final String STILE_BOTTONE_HOVER = "-fx-background-color: " + COLORE_HOVER + "; -fx-text-fill: white; -fx-font-weight: bold; " + STILE_BOTTONE_BASE;

    /**
     * Alert con bottoni "Sì" e "No" - l'utente deve scegliere
     */
    public boolean mostraAlertConferma(String titolo, String messaggio) {
        Stage stage = creaStageBase();
        BorderPane root = creaRootBase();

        root.setTop(creaHeader(titolo));
        root.setCenter(creaMessaggio(messaggio, true));
        root.setBottom(creaBottoniConferma(stage));

        mostraStage(stage, root);
        return risposta;
    }

    /**
     * Alert con bottone "Torna alla Home".
     * L'utente può cliccare il bottone per tornare subito alla Home,
     * altrimenti dopo 5 secondi il reindirizzamento avverrà automaticamente.
     */
    public boolean mostraAlertConfermaTornaAllaHome(String titolo, String messaggio) {
        Stage stage = creaStageBase();
        BorderPane root = creaRootBase();

        root.setTop(creaHeader(titolo));
        root.setCenter(creaMessaggio(messaggio, true));
        root.setBottom(creaBottoneTornaAllaHome(stage));

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> {
            risposta = true;
            stage.close();
        });
        delay.play();

        mostraStage(stage, root);
        return risposta;
    }


    /**
     * Alert informativo che si chiude automaticamente dopo tot secondi
     */
    public void mostraAlertInfo(String titolo, String messaggio) {
        Stage stage = creaStageBase();
        BorderPane root = creaRootBase();

        root.setTop(creaHeader(titolo));
        root.setCenter(creaMessaggio(messaggio, false));
        configuraAutoChiusura(root, stage);

        mostraStage(stage, root);
    }

    private Stage creaStageBase() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        return stage;
    }

    private BorderPane creaRootBase() {
        BorderPane root = new BorderPane();
        root.setStyle(STILE_ROOT);
        root.setPrefSize(400, 200);
        return root;
    }

    private Label creaHeader(String titolo) {
        Label label = new Label(titolo);
        label.setStyle("-fx-text-fill: " + COLORE_PRIMARIO + "; -fx-font-weight: bold;");
        label.setFont(Font.font(ROBOTO, FontWeight.BOLD, 20));
        label.setPadding(new Insets(15, 0, 10, 0));
        BorderPane.setAlignment(label, Pos.CENTER);
        return label;
    }

    private VBox creaMessaggio(String messaggio, boolean conBottoni) {
        Label label = new Label(messaggio);
        label.setStyle("-fx-text-fill: black;");
        label.setFont(Font.font(ROBOTO, 16));
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);

        VBox messaggioBox = new VBox(label);
        messaggioBox.setAlignment(Pos.CENTER);
        messaggioBox.setPadding(new Insets(0, 0, conBottoni ? 20 : 0, 0));
        return messaggioBox;
    }

    private HBox creaBottoniConferma(Stage stage) {
        Button btnSi = creaBottone("Sì", stage, true);
        Button btnNo = creaBottone("No", stage, false);

        HBox bottoniBox = new HBox(20, btnSi, btnNo);
        bottoniBox.setAlignment(Pos.CENTER);
        bottoniBox.setPadding(new Insets(0, 0, 20, 0));
        return bottoniBox;
    }

    private HBox creaBottoneTornaAllaHome(Stage stage) {
        Button bottone = creaBottone("Torna alla home", stage, true);

        bottone.setPrefSize(200,35);

        HBox bottoniBox = new HBox(bottone);
        bottoniBox.setAlignment(Pos.CENTER);
        bottoniBox.setPadding(new Insets(0, 20, 20, 0));

        return bottoniBox;
    }


    private void configuraAutoChiusura(BorderPane root, Stage stage) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(SECONDI_AUTO_CHIUSURA), e -> stage.close()));
        timeline.setCycleCount(1);
        timeline.play();

        root.setOnMouseClicked(e -> stage.close());}

    private Button creaBottone(String testo, Stage stage, boolean isSi) {
        Button bottone = new Button(testo);
        bottone.setStyle(STILE_BOTTONE_NORMALE);
        bottone.setFont(Font.font(ROBOTO, FontWeight.BOLD, 16));
        bottone.setPrefSize(80, 35);

        bottone.setOnMouseEntered(e -> bottone.setStyle(STILE_BOTTONE_HOVER));
        bottone.setOnMouseExited(e -> bottone.setStyle(STILE_BOTTONE_NORMALE));
        bottone.setOnAction(e -> {
            risposta = isSi;
            stage.close();
        });

        return bottone;
    }

    private void mostraStage(Stage stage, BorderPane root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
}