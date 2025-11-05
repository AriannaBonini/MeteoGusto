package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.SupportoGUILogout;
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
import java.time.LocalDate;
import java.time.LocalTime;
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
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");

    public void initialize() {

        campoData.setEditable(false);
        campoData.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: rgba(0,0,0,0.58);");
                }
            }
        });
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
            GestoreScena.cambiaScenaConParametri("/PrenotaRistorante.fxml", evento,
                    (PrenotaRistoranteCG controller) -> controller.setFiltriBean(filtriBean));
        } catch (ValidazioneException e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
        } catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Orario non valido. Usa il formato HH:mm");
        }catch (NumberFormatException e) {
            mostraErroreTemporaneamenteNellaLabel("Numero persone non valido. Riempire il campo");
        }
    }

    private FiltriBean creaFiltriBean() throws ValidazioneException, DateTimeParseException, NumberFormatException {
        LocalTime ora = parse(campoOra.getText(), formatoOrario);
        return new FiltriBean(campoData.getValue(), ora, campoCitta.getText(), Integer.parseInt(campoNumeroPersone.getText()));
    }



    @FXML
    private void clickListaPrenotazioni(MouseEvent evento) {/**/}

    @FXML
    private void clickProfiloPersonale(MouseEvent evento) {/**/}

    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

}
