package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.HomeUtenteController;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoComponentiGUISchedaRistorante;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoGUILogout;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoGUIPaginaIniziale;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoNotificheGUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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


    public void initialize() {
        popolaNotifiche();

        SupportoGUIPaginaIniziale.nascondiNodi(titolo, sottotitolo, carosello, suggerimento);

        SupportoGUIPaginaIniziale.fadeInNode(titolo, 2, 0);
        SupportoGUIPaginaIniziale.fadeInNode(sottotitolo, 2, 1.0);
        SupportoGUIPaginaIniziale.fadeInNode(suggerimento, 2, 2.0);
        SupportoGUIPaginaIniziale.effettoLucine(suggerimento, 40, 150);

        SupportoGUIPaginaIniziale.effettoLucine(titolo, 40, 150);
        SupportoGUIPaginaIniziale.effettoLucine(sottotitolo, 40, 150);


        popolaSchedeRistoranti();
    }


    private void popolaSchedeRistoranti() {
        HomeUtenteController homeUtenteController = new HomeUtenteController();

        try {
            listaRistoranti = homeUtenteController.trovaMiglioriRistoranti();

            if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                SupportoGUIPaginaIniziale.nascondiNodi(vBox1,vBox2,vBox3,vBox4);
                suggerimento.setText("Stiamo aggiornando la nostra selezione: presto nuovi ristoranti da scoprire!");
                return;
            }


            inserisciDatiNeiBox();


            int numeroSchede = Math.min(4, listaRistoranti.size());

            VBox[] tuttiBox = {vBox1, vBox2, vBox3, vBox4};

            List<VBox> boxPopolati = new ArrayList<>();
            if (numeroSchede > 0) boxPopolati.add(vBox1);
            if (numeroSchede > 1) boxPopolati.add(vBox2);
            if (numeroSchede > 2) boxPopolati.add(vBox3);
            if (numeroSchede > 3) boxPopolati.add(vBox4);


            for (VBox box : tuttiBox) {
                if (!boxPopolati.contains(box)) {
                    SupportoGUIPaginaIniziale.nascondiNodi(box);
                }else {
                    SupportoGUIPaginaIniziale.effettoLucine(box, 40, 150);
                }
            }

            SupportoGUIPaginaIniziale.suggerimentiOpachi(boxPopolati.toArray(new VBox[0]));

            FadeTransition fadeCarosello = new FadeTransition(Duration.seconds(2), carosello);
            fadeCarosello.setFromValue(0.0);
            fadeCarosello.setToValue(1.0);
            fadeCarosello.setDelay(Duration.seconds(2.5));
            fadeCarosello.play();

            fadeCarosello.setOnFinished(e -> {
                double durataSecondi = 1.5;
                SupportoGUIPaginaIniziale.animaSuggerimenti(
                        durataSecondi,
                        boxPopolati.toArray(new VBox[0])
                );
            });

        } catch (ValidazioneException | IllegalStateException e) {
            logger.error("Errore durante il caricamento dei ristoranti", e);
        }
    }


    private void inserisciDatiNeiBox() throws IllegalStateException{
        
        for (int i = 0; i < Math.min(4, listaRistoranti.size()); i++) {
            RistoranteBean r = listaRistoranti.get(i);
            switch (i) {
                case 0 -> {
                    nome1.setText(r.getNome());
                    info1.setText(r.getCitta() + " • " + r.getCucina());
                    immagine1.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r));
                    media1.setText(r.getMediaStelle().toString());
                }
                case 1 -> {
                    nome2.setText(r.getNome());
                    info2.setText(r.getCitta() + " • " + r.getCucina());
                    immagine2.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r));
                    media2.setText(r.getMediaStelle().toString());
                }
                case 2 -> {
                    nome3.setText(r.getNome());
                    info3.setText(r.getCitta() + " • " + r.getCucina());
                    immagine3.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r));
                    media3.setText(r.getMediaStelle().toString());
                }
                case 3 -> {
                    nome4.setText(r.getNome());
                    info4.setText(r.getCitta() + " • " + r.getCucina());
                    immagine4.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(r));
                    media4.setText(r.getMediaStelle().toString());
                }
                default ->  throw new IllegalStateException("Indice ristorante non gestito: " + i);
            }
        }
    }


    private void popolaNotifiche() {
        PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();

        try {
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());
        } catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle notifiche : ", e);
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
