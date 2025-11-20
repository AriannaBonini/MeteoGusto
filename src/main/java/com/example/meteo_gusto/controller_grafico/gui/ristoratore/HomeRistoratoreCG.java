package com.example.meteo_gusto.controller_grafico.gui.ristoratore;

import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoGUILogout;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoGUIPaginaIniziale;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoNotificheGUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeRistoratoreCG {

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
    private Label suggerimento;
    @FXML
    private Label notifichePrenotazione;
    @FXML
    private ImageView home;

    private static final Logger logger = LoggerFactory.getLogger(HomeRistoratoreCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();


    public void initialize() {

        SupportoGUIPaginaIniziale.nascondiNodi(titolo, sottotitolo, carosello, suggerimento);
        popolaNotifiche();

        SupportoGUIPaginaIniziale.fadeInNode(titolo, 2, 0);
        SupportoGUIPaginaIniziale.effettoLucine(titolo, 40, 150);

        SupportoGUIPaginaIniziale.fadeInNode(sottotitolo, 2, 1.0);
        SupportoGUIPaginaIniziale.effettoLucine(sottotitolo, 40, 150);

        SupportoGUIPaginaIniziale.fadeInNode(suggerimento,2,2.0);
        SupportoGUIPaginaIniziale.effettoLucine(suggerimento,40,150);

        FadeTransition fadeCarosello;
        fadeCarosello = new FadeTransition(Duration.seconds(2), carosello);
        fadeCarosello.setFromValue(0.0);
        fadeCarosello.setToValue(1.0);
        fadeCarosello.setDelay(Duration.seconds(2.5));
        fadeCarosello.play();

        SupportoGUIPaginaIniziale.effettoLucine(stackPane, 40, 150);
        SupportoGUIPaginaIniziale.suggerimentiOpachi(vBox1, vBox2, vBox3, vBox4);

        fadeCarosello.setOnFinished(e -> {
            double durataSecondi = 1.5;
            SupportoGUIPaginaIniziale.animaSuggerimenti(durataSecondi, vBox1, vBox2, vBox3, vBox4);
        });
    }


    @FXML
    public void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,home,"/Foto/HomeSelezionata.png","/Foto/HomeNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    private void popolaNotifiche() {
        try {
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());
        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle motifiche : ",e );
        }
    }

    @FXML
    public void clickCalendario(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniRistorante.fxml",evento);}

    @FXML
    public void clickMenu() {GestoreScena.mostraAlertSenzaConferma("Siamo spiacenti","Sezione menù non disponibile");}


}