package com.example.meteo_gusto.utilities;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.InputStream;


public class SupportoComponentiGUISchedaRistorante {

    private SupportoComponentiGUISchedaRistorante() { /* COSTRUTTORE VUOTO */ }

    // --- LABELS --------------------------------------------------

    public static Label creaLabelNomeRistorante() {
        Label label = new Label();
        label.setPrefWidth(133.0);
        label.setPrefHeight(18.0);
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(5, 0, 0, 14)); // top 5, left 14
        return label;
    }

    public static Label creaLabelCittaRistorante() {
        Label label = new Label();
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(0, 0, 0, 14));
        return label;
    }

    public static Label creaLabelTipoCucinaRistorante() {
        Label label = new Label();
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(0, 0, 0, 14));
        return label;
    }

    public static Label creaLabelMediaRecensione() {
        Label label = new Label();
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(3, 0, 0, 0)); // top 3
        return label;
    }

    // --- HBOX ----------------------------------------------------

    public static HBox creaHBoxRigaRistoranti(HBox modello) {
        HBox nuovaRiga = new HBox();
        nuovaRiga.setAlignment(modello.getAlignment() != null ? modello.getAlignment() : Pos.CENTER);
        nuovaRiga.setSpacing(modello.getSpacing());
        nuovaRiga.setPadding(modello.getPadding());
        nuovaRiga.setStyle(modello.getStyle());
        VBox.setMargin(nuovaRiga, VBox.getMargin(modello));
        return nuovaRiga;
    }


    public static HBox creaHBoxInfoFasciaPrezzo() {
        HBox hbox = new HBox();
        hbox.setPrefHeight(40.0);
        hbox.setPrefWidth(144.0);
        // spacing non presente in FXML
        return hbox;
    }

    public static HBox creaHBoxInfoRistorante1() {
        HBox hbox = new HBox();
        hbox.setPrefHeight(40.0);
        hbox.setPrefWidth(285.0);
        hbox.setSpacing(3.0); // spacing="3.0"
        return hbox;
    }

    public static HBox creaHBoxInfoRistorante2() {
        HBox hbox = new HBox();
        hbox.setPrefHeight(39.0);
        hbox.setPrefWidth(285.0);
        hbox.setSpacing(20.0); // spacing="54.0"
        return hbox;
    }

    public static HBox creaHBoxInfoRistorante3() {
        HBox hbox = new HBox();
        hbox.setPrefHeight(113.0);
        hbox.setPrefWidth(285.0);
        hbox.setPadding(new Insets(0, 0, 10, 0));
        return hbox;
    }

    public static HBox creaHBoxInfoStelle() {
        HBox hbox = new HBox();
        hbox.setPrefHeight(114.0);
        hbox.setPrefWidth(109.0);
        hbox.setSpacing(4.0); // spacing="4.0"
        VBox.setMargin(hbox, new Insets(12, 0, 0, 0)); // top 12
        return hbox;
    }

    // --- VBOX ----------------------------------------------------

    public static VBox creaVBoxSchedaRistorante() {
        VBox vbox = new VBox();
        vbox.setPrefHeight(358.0);
        vbox.setPrefWidth(286.0);
        vbox.setSpacing(14.0); // spacing="14.0"
        vbox.setStyle("-fx-border-color: rgba(0, 0, 0, 0.26); "
                + "-fx-background-color: #ffffff; "
                + "-fx-border-radius: 20px;");
        return vbox;
    }

    // --- BOTTONI -------------------------------------------------

    public static Button creaBottoneScopriDiPiu() {
        Button button = new Button("Scopri di più");
        button.setPrefHeight(40.0);
        button.setPrefWidth(176.0);
        button.setStyle("-fx-background-color: #e97627; -fx-background-radius: 8px;");
        button.setFont(new Font(20.0));
        button.setTextFill(Color.WHITE);
        HBox.setMargin(button, new Insets(3, 17, 0, 0)); // top 10, right 10
        return button;
    }

    // --- IMMAGINI ------------------------------------------------

    public static ImageView creaImmagineRistorante(String percorso) {
        InputStream is = SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso);
        if (is == null) return new ImageView();

        Image immagine = new Image(is);
        ImageView img = new ImageView(immagine);
        img.setFitWidth(271.0);
        img.setFitHeight(200.0);
        img.setPickOnBounds(true);
        VBox.setMargin(img, new Insets(6, 0, 0, 6)); // top 6, left 6
        return img;
    }

    public static ImageView creaImmagineDollaro(String percorso) {
        InputStream is = SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso);
        if (is == null) return new ImageView();

        Image immagine = new Image(is);
        ImageView img = new ImageView(immagine);
        img.setFitWidth(27.0);
        img.setFitHeight(36.0);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        HBox.setMargin(img, new Insets(5, 0, 0, 0)); // top 5
        return img;
    }

    public static ImageView creaImmagineStella(String percorso) {
        InputStream is = SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso);
        if (is == null) return new ImageView();

        Image immagine = new Image(is);
        ImageView img = new ImageView(immagine);
        img.setFitWidth(95.0);
        img.setFitHeight(36.0);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        HBox.setMargin(img, new Insets(0, 0, 0, 14)); // left 14
        return img;
    }
}


