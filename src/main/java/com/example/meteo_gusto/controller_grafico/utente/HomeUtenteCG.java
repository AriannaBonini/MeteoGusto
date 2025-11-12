package com.example.meteo_gusto.controller_grafico.utente;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.HomeUtenteController;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.GestoreScena;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoComponentiGUISchedaRistorante;
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

import java.util.ArrayList;
import java.util.List;

public class HomeUtenteCG {
    @FXML
    public ImageView esci;
    @FXML
    private ImageView home;
    @FXML
    private Label titolo;
    @FXML
    private Label sottotitolo;
    @FXML
    private Label suggerimento;
    @FXML
    private Label notifichePrenotazione;
    @FXML
    private HBox carosello;
    @FXML
    private StackPane stackPane;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private VBox vBox3;
    @FXML
    private VBox vBox4;
    @FXML
    private ImageView immagine1;
    @FXML
    private Label nome1;
    @FXML
    private Label info1;
    @FXML
    private Label media1;
    @FXML
    private ImageView immagine2;
    @FXML
    private Label nome2;
    @FXML
    private Label info2;
    @FXML
    private Label media2;
    @FXML
    private ImageView immagine3;
    @FXML
    private Label nome3;
    @FXML
    private Label info3;
    @FXML
    private Label media3;
    @FXML
    private ImageView immagine4;
    @FXML
    private Label nome4;
    @FXML
    private Label info4;
    @FXML
    private Label media4;


    private static final Logger logger = LoggerFactory.getLogger(HomeUtenteCG.class.getName());
    private List<RistoranteBean> listaRistoranti= new ArrayList<>();


    public void initialize(){
        popolaNotifiche();

        SupportoGUIPaginaIniziale.nascondiNodi(titolo, sottotitolo, carosello, suggerimento);

        SupportoGUIPaginaIniziale.fadeInNode(titolo, 2, 0);
        SupportoGUIPaginaIniziale.fadeInNode(sottotitolo, 2, 2.2);
        SupportoGUIPaginaIniziale.fadeInNode(suggerimento,2,4.4);

        SupportoGUIPaginaIniziale.effettoLucine(titolo, 40, 150);
        SupportoGUIPaginaIniziale.effettoLucine(sottotitolo, 40, 150);
        SupportoGUIPaginaIniziale.effettoLucine(suggerimento,40,150);

        popolaSchedeRistoranti();

        FadeTransition fadeCarosello = new FadeTransition(Duration.seconds(2), carosello);
        fadeCarosello.setFromValue(0.0);
        fadeCarosello.setToValue(1.0);
        fadeCarosello.setDelay(Duration.seconds(5.0));
        fadeCarosello.play();

        SupportoGUIPaginaIniziale.effettoLucine(stackPane, 40, 150);
        SupportoGUIPaginaIniziale.suggerimentiOpachi(vBox1, vBox2, vBox3, vBox4);

        fadeCarosello.setOnFinished(e -> {
            double durataSecondi = 3.5;
            SupportoGUIPaginaIniziale.animaSuggerimenti(vBox1, vBox2, vBox3, vBox4, durataSecondi);
        });
    }

    private void popolaSchedeRistoranti() {
        HomeUtenteController homeUtenteController= new HomeUtenteController();

        try{
           listaRistoranti= homeUtenteController.trovaMiglioriRistoranti();
           inserisciDatiNeiBox();

        }catch (ValidazioneException e) {
            GestoreScena.mostraAlertSenzaConferma("Errore","Errore durante il caricamento dei ristoranti migliori");
            logger.error("Errore durante il caricamento dei ristoranti", e);
        }
    }

    private void inserisciDatiNeiBox() {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) return;


        RistoranteBean r1 = listaRistoranti.getFirst();
        nome1.setText(r1.getNomeRistorante());
        info1.setText(r1.getPosizione().getCitta() + " • " + r1.getCucina());
        immagine1.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r1));
        media1.setText(r1.getMediaStelle().toString());

        if (listaRistoranti.size() > 1) {
            RistoranteBean r2 = listaRistoranti.get(1);
            nome2.setText(r2.getNomeRistorante());
            info2.setText(r2.getPosizione().getCitta() + " • " + r2.getCucina());
            immagine2.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r2));
            media2.setText(r2.getMediaStelle().toString());
        }

        if (listaRistoranti.size() > 2) {
            RistoranteBean r3 = listaRistoranti.get(2);
            nome3.setText(r3.getNomeRistorante());
            info3.setText(r3.getPosizione().getCitta() + " • " + r3.getCucina());
            immagine3.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r3));
            media3.setText(r3.getMediaStelle().toString());
        }

        if (listaRistoranti.size() > 3) {
            RistoranteBean r4 = listaRistoranti.get(3);
            nome4.setText(r4.getNomeRistorante());
            info4.setText(r4.getPosizione().getCitta() + " • " + r4.getCucina());
            immagine4.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r4));
            media4.setText(r4.getMediaStelle().toString());
        }
    }


    private void popolaNotifiche() {
        PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();

        try {
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());
        } catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle motifiche : ", e);
        }
    }


    @FXML
    public void clickListaPrenotazioni(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniUtente.fxml",evento);}

    @FXML
    public void clickPrenotaRistorante(MouseEvent evento) {GestoreScena.cambiaScena("/PrenotaRistoranteFormIniziale.fxml",evento);}

    @FXML
    public void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,home,"/Foto/HomeSelezionata.png","/Foto/HomeNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }
}
