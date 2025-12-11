package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.PrevisioniMeteoFuoriRangeException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoGUILogout;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoNotificheGUI;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static java.time.LocalTime.parse;

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
    private Label infoErrore;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView prenotaRistorante;
    @FXML
    private Label notifichePrenotazione;

    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");
    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteFormInizialeCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();

    public void initialize() {

        campoData.setEditable(false);
        campoData.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate oggi = LocalDate.now();

                if (date.isBefore(oggi)) {
                    setDisable(true);
                    setStyle("-fx-background-color: rgba(0,0,0,0.58);");
                }
            }
        });

        popolaNotifiche();

    }

    private void popolaNotifiche() {
        try {
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());
        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle motifiche : ",e );
        }
    }


    @FXML
    private void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,prenotaRistorante,"/Foto/ClocheSelezionata.png","/Foto/ClocheNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    private void clickCerca(ActionEvent evento) {
        try {
            FiltriBean filtriBean = creaFiltriBean();
            prenotaRistoranteController.validaDati(filtriBean);

            GestoreScena.cambiaScenaConParametri("/PrenotaRistorante.fxml", evento,
                    (PrenotaRistoranteCG controller) -> controller.setFiltriBean(filtriBean));
        } catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Orario non valido. Usa il formato HH:mm");
        }catch (NumberFormatException e) {
            mostraErroreTemporaneamenteNellaLabel("Numero persone non valido. Riempire il campo");
        }catch (ValidazioneException e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
            logger.error("Errore di validazione dei dati inseriti", e );
        } catch (PrevisioniMeteoFuoriRangeException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione", e.getMessage());
        }
    }

    private FiltriBean creaFiltriBean() throws DateTimeParseException, NumberFormatException, ValidazioneException {
        FiltriBean filtriBean = new FiltriBean();
        filtriBean.setData(campoData.getValue());
        filtriBean.setOra(parse(campoOra.getText(), formatoOrario));
        filtriBean.setCitta(campoCitta.getText().trim());
        filtriBean.setNumeroPersone(Integer.parseInt(campoNumeroPersone.getText()));

        return filtriBean;
    }



    @FXML
    private void clickListaPrenotazioni(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniUtente.fxml", evento);}

    @FXML
    private void clickHome(MouseEvent evento) {GestoreScena.cambiaScena("/HomeUtente.fxml", evento);}

    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

}
