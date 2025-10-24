package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.time.LocalTime.*;

public class RegistrazioneRistoratoreCG {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCognome;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoPassword;
    @FXML
    private TextField campoPartitaIva;
    @FXML
    private TextField campoNomeRistorante;
    @FXML
    private TextField campoIndirizzo;
    @FXML
    private TextField campoCitta;
    @FXML
    private TextField campoCap;
    @FXML
    private TextField campoTelefonoRistorante;
    @FXML
    private CheckBox checkBoxMaggiorenne;
    @FXML
    private CheckBox checkBoxTerminiPrivacy;
    @FXML
    private Label infoErrore;
    @FXML
    private ComboBox<FasciaPrezzoRistorante> comboBoxPrezzo;
    @FXML
    private CheckBox checkBoxLun;
    @FXML
    private CheckBox checkBoxMar;
    @FXML
    private CheckBox checkBoxMer;
    @FXML
    private CheckBox checkBoxGio;
    @FXML
    private CheckBox checkBoxVen;
    @FXML
    private CheckBox checkBoxSab;
    @FXML
    private CheckBox checkBoxDom;
    @FXML
    private ComboBox<TipoCucina> comboBoxCucina;
    @FXML
    private CheckBox checkBoxHalal;
    @FXML
    private CheckBox checkBoxKosher;
    @FXML
    private CheckBox checkBoxVegano;
    @FXML
    private CheckBox checkBoxVegetariano;
    @FXML
    private CheckBox checkBoxPescetariano;
    @FXML
    private CheckBox checkBoxCeliaco;
    @FXML
    private CheckBox checkBoxSenzaLattosio;
    @FXML
    private TextField inizioPranzo;
    @FXML
    private TextField finePranzo;
    @FXML
    private TextField inizioCena;
    @FXML
    private TextField fineCena;
    @FXML
    private CheckBox checkBoxInterno;
    @FXML
    private CheckBox checkBoxEsterno;
    @FXML
    private CheckBox checkBoxEsternoCoperto;
    @FXML
    private TextField campoCopertiInterni;
    @FXML
    private TextField campoCopertiEsterni;
    @FXML
    private TextField campoCopertiEsterniCoperti;
    @FXML
    private CheckBox checkBoxRiscaldamento;
    @FXML
    private CheckBox checkBoxRaffreddamento;

    RegistrazioneController registrazioneController= new RegistrazioneController();
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneRistoratoreCG.class.getName());
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    public void initialize() {
        comboBoxCucina.setItems(FXCollections.observableArrayList(TipoCucina.values()));
        comboBoxPrezzo.setItems(FXCollections.observableArrayList(FasciaPrezzoRistorante.values()));

        campoCopertiInterni.setDisable(true);
        campoCopertiEsterni.setDisable(true);
        campoCopertiEsterniCoperti.setDisable(true);
        checkBoxRaffreddamento.setDisable(true);
        checkBoxRiscaldamento.setDisable(true);


        checkBoxInterno.selectedProperty().addListener((observable, oldValue, newValue) -> campoCopertiInterni.setDisable(!newValue));

        checkBoxEsterno.selectedProperty().addListener((observable, oldValue, newValue) -> campoCopertiEsterni.setDisable(!newValue));

        checkBoxEsternoCoperto.selectedProperty().addListener((observable, oldValue, newValue) -> {
            campoCopertiEsterniCoperti.setDisable(!newValue);
            checkBoxRaffreddamento.setDisable(!newValue);
            checkBoxRiscaldamento.setDisable(!newValue);
        });

    }


    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {

            RegistrazioneUtenteBean registrazioneUtenteBean = new RegistrazioneUtenteBean(campoNome.getText().trim(),campoCognome.getText().trim(),campoTelefono.getText().trim(), campoEmail.getText().trim(), campoPassword.getText().trim(),checkBoxMaggiorenne.isSelected(),checkBoxTerminiPrivacy.isSelected());
            OrariBean orariBean = new OrariBean(parse(inizioPranzo.getText(), formatoOrario), parse(finePranzo.getText(), formatoOrario), parse(inizioCena.getText(), formatoOrario), parse(fineCena.getText(), formatoOrario), giorniChiusura());
            PosizioneBean posizioneBean= new PosizioneBean(campoIndirizzo.getText(),campoCitta.getText(), campoCap.getText());
            OffertaCulinariaBean offertaCulinariaBean= new OffertaCulinariaBean(comboBoxCucina.getValue(), dietaOffertaDalRistorante(),comboBoxPrezzo.getValue());
            AmbienteECopertiBean ambienteECopertiBean= ambienteECopertiDelRistorante();

            RistoranteBean ristoranteBean= new RistoranteBean(campoPartitaIva.getText(),campoNomeRistorante.getText(),campoTelefonoRistorante.getText(), orariBean, ambienteECopertiBean,offertaCulinariaBean,posizioneBean);
            RegistrazioneRistoratoreBean registrazioneRistoratoreBean= new RegistrazioneRistoratoreBean(registrazioneUtenteBean,ristoranteBean);

            registrazioneController.registraRistoratore(registrazioneRistoratoreBean);

            GestoreScena.mostraAlertSenzaConferma("Successo","La registrazione è andata a buon fine ! ");
            GestoreScena.cambiaScena("/Login.fxml", evento);

        }catch (ValidazioneException | EccezioneDAO e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
            logger.error("Errore registrazione: ", e);
        }catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Orario non valido. Usa il formato HH:mm");
        }
    }


    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = infoErrore.getText();
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

    private Set<GiorniSettimana> giorniChiusura() {
        Set<GiorniSettimana> giorniSelezionati = new HashSet<>();

        if (checkBoxLun.isSelected()) giorniSelezionati.add(GiorniSettimana.LUNEDI);
        if (checkBoxMar.isSelected()) giorniSelezionati.add(GiorniSettimana.MARTEDI);
        if (checkBoxMer.isSelected()) giorniSelezionati.add(GiorniSettimana.MERCOLEDI);
        if (checkBoxGio.isSelected()) giorniSelezionati.add(GiorniSettimana.GIOVEDI);
        if (checkBoxVen.isSelected()) giorniSelezionati.add(GiorniSettimana.VENERDI);
        if (checkBoxSab.isSelected()) giorniSelezionati.add(GiorniSettimana.SABATO);
        if (checkBoxDom.isSelected()) giorniSelezionati.add(GiorniSettimana.DOMENICA);

        return giorniSelezionati;
    }

    private Set<TipoDieta> dietaOffertaDalRistorante() {
        Set<TipoDieta> dietaSelezionata = new HashSet<>();

        if (checkBoxHalal.isSelected()) dietaSelezionata.add(TipoDieta.HALAL);
        if (checkBoxKosher.isSelected()) dietaSelezionata.add(TipoDieta.KOSHER);
        if (checkBoxVegano.isSelected()) dietaSelezionata.add(TipoDieta.VEGANO);
        if (checkBoxVegetariano.isSelected()) dietaSelezionata.add(TipoDieta.VEGETARIANO);
        if (checkBoxCeliaco.isSelected()) dietaSelezionata.add(TipoDieta.CELIACO);
        if (checkBoxPescetariano.isSelected()) dietaSelezionata.add(TipoDieta.PESCETARIANO);
        if (checkBoxSenzaLattosio.isSelected()) dietaSelezionata.add(TipoDieta.SENZA_LATTOSIO);

        return dietaSelezionata;
    }

    private AmbienteECopertiBean ambienteECopertiDelRistorante() throws ValidazioneException {
        Map<TipoAmbiente, Integer> copertiMap = new HashMap<>();
        Set<Extra> extraSelezionati = new HashSet<>();

        if (checkBoxInterno.isSelected()) {
            String testo = campoCopertiInterni.getText();
            Integer coperti = parseCoperti(testo);
            copertiMap.put(TipoAmbiente.INTERNO, coperti);
        }

        if (checkBoxEsterno.isSelected()) {
            String testo = campoCopertiEsterni.getText();
            Integer coperti = parseCoperti(testo);
            copertiMap.put(TipoAmbiente.ESTERNO, coperti);
        }

        if (checkBoxEsternoCoperto.isSelected()) {
            String testo = campoCopertiEsterniCoperti.getText();
            Integer coperti = parseCoperti(testo);
            copertiMap.put(TipoAmbiente.ESTERNO_COPERTO, coperti);
        }

        if (checkBoxRaffreddamento.isSelected()) extraSelezionati.add(Extra.RAFFREDDAMENTO);
        if (checkBoxRiscaldamento.isSelected()) extraSelezionati.add(Extra.RISCALDAMENTO);


        return new AmbienteECopertiBean(copertiMap,extraSelezionati);
    }

    private Integer parseCoperti(String testo) {
        if (testo == null || testo.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(testo.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }


    @FXML
    private void clickRitornaAlLogin(MouseEvent evento) {
        boolean risposta = GestoreScena.mostraAlertConConferma("Attenzione", "Sei sicuro di voler tornare al Login ?");
        if (risposta) {
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }


}
