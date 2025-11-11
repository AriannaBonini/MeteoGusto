package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.SupportoGUILogout;
import com.example.meteo_gusto.utilities.SupportoGUIPaginaIniziale;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PaginaInizialeRistoratoreCG {

    @FXML
    private Label titolo;
    @FXML
    private Label sottotitolo;
    @FXML
    private StackPane stackPane;
    @FXML
    private HBox carosello;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private VBox vBox3;
    @FXML
    private VBox vBox4;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView menu;
    @FXML
    private ImageView calendario;
    @FXML
    private Label suggerimento;

    public void initialize() {

        SupportoGUIPaginaIniziale.nascondiNodi(titolo, sottotitolo, carosello, suggerimento);

        SupportoGUIPaginaIniziale.fadeInNode(titolo, 2, 0);
        SupportoGUIPaginaIniziale.fadeInNode(sottotitolo, 2, 2.2);
        SupportoGUIPaginaIniziale.fadeInNode(suggerimento,2,4.4);

        SupportoGUIPaginaIniziale.effettoLucine(titolo, 40, 150);
        SupportoGUIPaginaIniziale.effettoLucine(sottotitolo, 40, 150);
        SupportoGUIPaginaIniziale.effettoLucine(suggerimento,40,150);

        FadeTransition fadeCarosello = new FadeTransition(Duration.seconds(2), carosello);
        fadeCarosello.setFromValue(0.0);
        fadeCarosello.setToValue(1.0);
        fadeCarosello.setDelay(Duration.seconds(4.5));
        fadeCarosello.play();

        SupportoGUIPaginaIniziale.effettoLucine(stackPane, 40, 150);
        SupportoGUIPaginaIniziale.suggerimentiOpachi(vBox1, vBox2, vBox3, vBox4);

        fadeCarosello.setOnFinished(e -> {
            double durataSecondi = 5;
            SupportoGUIPaginaIniziale.animaSuggerimenti(vBox1, vBox2, vBox3, vBox4, durataSecondi);
        });
    }



    @FXML
    private void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,calendario,"/Foto/IconaListaPrenotazioniRistoranteNonSelezionata.png","/Foto/IconaListaPrenotazioniRistoranteNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    public void clickCalendario(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniRistorante.fxml",evento);}

    @FXML
    public void clickProfiloPersonale(MouseEvent event) {
    }

    @FXML
    public void clickMenu(MouseEvent event) {
    }


}
