package com.example.meteo_gusto.utilities.supporto_gui;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
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
import java.math.BigDecimal;
import java.util.Objects;


public class SupportoComponentiGUISchedaRistorante {

    private SupportoComponentiGUISchedaRistorante() { /* COSTRUTTORE VUOTO */ }

    // --- LABELS --------------------------------------------------

    public static Label creaLabelNomeRistorante() {
        Label label = new Label();
        label.setPrefWidth(213.0);
        label.setPrefHeight(30.0);
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(5, 0, 0, 14)); // top 5, left 14
        return label;
    }

    public static Label creaLabelCittaETipoCucinaRistorante() {
        Label label = new Label();
        label.setFont(new Font(20.0));
        HBox.setMargin(label, new Insets(0, 0, 0, 14));
        return label;
    }

    public static Label creaLabelMediaRecensione() {
        Label label = new Label();
        label.setFont(new Font(15.0));
        HBox.setMargin(label, new Insets(1, 0, 0, 0)); // top 3
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
        hbox.setSpacing(5.0); // spacing="3.0"
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

    public static ImageView creaImmagineRistorante(RistoranteBean ristorante) {
        ImageView img = new ImageView(immagineCucinaRistorante(ristorante));

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

    public static ImageView creaImmagineDollaroMiniatura(String percorso) {
        InputStream is = SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso);
        if (is == null) return new ImageView();

        Image immagine = new Image(is);
        ImageView img = new ImageView(immagine);

        img.setFitWidth(25.0);
        img.setFitHeight(25.0);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);

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
        HBox.setMargin(img, new Insets(0, 0, 0, 14));
        return img;
    }

    public static ImageView creaImmagineStellaMiniatura(String percorso) {
        InputStream is = SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso);
        if (is == null) return new ImageView();

        Image immagine = new Image(is);
        ImageView img = new ImageView(immagine);
        img.setFitWidth(30.0);
        img.setFitHeight(30.0);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        HBox.setMargin(img, new Insets(0, 3, 0, 0));
        return img;
    }


    public static Image immagineCucinaRistorante(RistoranteBean ristorantebean) {
        String percorso = switch (ristorantebean.getCucina()) {
            case ITALIANA -> "/Foto/FotoRistoranteItaliano.png";
            case SUSHI -> "/Foto/FotoRistoranteGiapponese.png";
            case FAST_FOOD -> "/Foto/FotoPaninoteca.png";
            case GRECA -> "/Foto/FotoRistoranteGreco.png";
            case CINESE -> "/Foto/FotoRistoranteCinese.png";
            case TURCA -> "/Foto/FotoRistoranteTurco.png";
            case PIZZA -> "/Foto/FotoPizzeria.png";
            case MESSICANA -> "/Foto/FotoRistoranteMessicano.png";
        };
        return new Image(Objects.requireNonNull(SupportoComponentiGUISchedaRistorante.class.getResourceAsStream(percorso)));
    }

    public static void immagineFasciaPrezzoRistorante(RistoranteBean ristorante, HBox hBoxInfoFasciaPrezzo, boolean dimensioneGrande) {
        switch (ristorante.getFasciaPrezzo()) {
            case FasciaPrezzoRistorante.ECONOMICO -> ripetiImmagineDollaro(1, hBoxInfoFasciaPrezzo,dimensioneGrande);
            case FasciaPrezzoRistorante.MODERATO -> ripetiImmagineDollaro(2, hBoxInfoFasciaPrezzo,dimensioneGrande);
            case FasciaPrezzoRistorante.COSTOSO -> ripetiImmagineDollaro(3, hBoxInfoFasciaPrezzo,dimensioneGrande);
            case FasciaPrezzoRistorante.LUSSO -> ripetiImmagineDollaro(4, hBoxInfoFasciaPrezzo,dimensioneGrande);
        }
    }

    private static void ripetiImmagineDollaro(int numeroRipetizioni, HBox hBoxInfoFasciaPrezzo, boolean dimensioneGrande) {
        for (int i = 0; i < numeroRipetizioni; i++) {
            if (dimensioneGrande) {
                hBoxInfoFasciaPrezzo.getChildren().add(SupportoComponentiGUISchedaRistorante.creaImmagineDollaro("/Foto/Prezzo.png"));
            } else {
                hBoxInfoFasciaPrezzo.getChildren().add(SupportoComponentiGUISchedaRistorante.creaImmagineDollaroMiniatura("/Foto/Prezzo.png"));
            }
        }
    }

    public static void immagineStellaRistorante(RistoranteBean ristorante, HBox hBoxInfoStelle) {
        hBoxInfoStelle.getChildren().clear();

        BigDecimal mediaBD = ristorante.getMediaStelle();
        double mediaStelle = mediaBD.doubleValue();
        int stellePiene = (int) mediaStelle;
        boolean mezzaStella = (mediaStelle - stellePiene) >= 0.5;
        int stelleTotali = 5;

        for (int i = 0; i < stelleTotali; i++) {
            String percorsoImmagine;

            if (i < stellePiene) {
                percorsoImmagine = "/Foto/stellinaColorata.png";
            } else if (i == stellePiene && mezzaStella) {
                percorsoImmagine = "/Foto/StellaMezzaColorata.png";
            } else {
                percorsoImmagine = "/Foto/StellaVuota.png";
            }

            ImageView stella = SupportoComponentiGUISchedaRistorante.creaImmagineStellaMiniatura(percorsoImmagine);
            hBoxInfoStelle.getChildren().add(stella);
        }
    }


}



