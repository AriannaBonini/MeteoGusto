package com.example.meteo_gusto.controller_grafico.gui.ristoratore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.*;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoCheckBoxCss;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
    @FXML
    private CheckBox checkBoxCena;
    @FXML
    private CheckBox checkBoxPranzo;


    private final RegistrazioneController registrazioneController= new RegistrazioneController();
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneRistoratoreCG.class.getName());
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");

    public void initialize() {
        comboBoxCucina.setItems(FXCollections.observableArrayList(TipoCucina.values()));
        comboBoxPrezzo.setItems(FXCollections.observableArrayList(FasciaPrezzoRistorante.values()));

        campoCopertiInterni.setDisable(true);
        campoCopertiEsterni.setDisable(true);
        campoCopertiEsterniCoperti.setDisable(true);
        checkBoxRaffreddamento.setDisable(true);
        checkBoxRiscaldamento.setDisable(true);


        List<CheckBox> checkBoxes = List.of(
                checkBoxHalal, checkBoxKosher, checkBoxVegano, checkBoxVegetariano,
                checkBoxPescetariano, checkBoxCeliaco, checkBoxSenzaLattosio, checkBoxMaggiorenne, checkBoxTerminiPrivacy,
                checkBoxLun, checkBoxMar, checkBoxMer, checkBoxGio, checkBoxVen, checkBoxSab, checkBoxDom, checkBoxEsterno, checkBoxEsternoCoperto, checkBoxInterno,
                checkBoxRiscaldamento, checkBoxRaffreddamento, checkBoxPranzo, checkBoxCena
        );
        SupportoCheckBoxCss.inizializzaCheckBoxMultipli(checkBoxes);


        checkBoxInterno.selectedProperty().addListener((observable, oldValue, newValue) -> campoCopertiInterni.setDisable(!newValue));

        checkBoxEsterno.selectedProperty().addListener((observable, oldValue, newValue) -> campoCopertiEsterni.setDisable(!newValue));

        checkBoxEsternoCoperto.selectedProperty().addListener((observable, oldValue, newValue) -> {
            campoCopertiEsterniCoperti.setDisable(!newValue);
            checkBoxRaffreddamento.setDisable(!newValue);
            checkBoxRiscaldamento.setDisable(!newValue);
        });

        inizioPranzo.setDisable(true);
        finePranzo.setDisable(true);
        inizioCena.setDisable(true);
        fineCena.setDisable(true);

        checkBoxPranzo.selectedProperty().addListener((observable, oldValue, newValue) -> {
            inizioPranzo.setDisable(!newValue);
            finePranzo.setDisable(!newValue);
        });


        checkBoxCena.selectedProperty().addListener((observable, oldValue, newValue) -> {
            inizioCena.setDisable(!newValue);
            fineCena.setDisable(!newValue);
        });

    }


    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {
            RegistrazionePersonaBean registrazioneRistoratoreBean= new RegistrazionePersonaBean();

            registrazioneRistoratoreBean.setMaggiorenne(checkBoxMaggiorenne.isSelected());
            registrazioneRistoratoreBean.setAccettaTermini(checkBoxTerminiPrivacy.isSelected());
            registrazioneRistoratoreBean.setPersona(datiProprietarioRistorante());

            registrazioneController.registraRistoratore(registrazioneRistoratoreBean);

            GestoreScena.mostraAlertSenzaConferma("Successo", "La registrazione è andata a buon fine!");
            GestoreScena.cambiaScena("/Login.fxml", evento);

        } catch (ValidazioneException | EccezioneDAO e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
            logger.error("Errore registrazione: ", e);
        } catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Orario non valido. Usa il formato HH:mm");
            logger.error("Errore formato orario ", e);
        }
    }

    private Set<Extra> extraDelRistorante(){
        Set<Extra> extraSelezionati = new HashSet<>();
        if(checkBoxEsternoCoperto.isSelected()) {
            if (checkBoxRaffreddamento.isSelected()) extraSelezionati.add(Extra.RAFFREDDAMENTO);
            if (checkBoxRiscaldamento.isSelected()) extraSelezionati.add(Extra.RISCALDAMENTO);
        }

        return extraSelezionati;
    }

    private GiorniEOrariBean orariRistorante() throws ValidazioneException  {
        GiorniEOrariBean giorniEOrariBean= new GiorniEOrariBean();

        if (checkBoxPranzo.isSelected()) {
            LocalTime oraInizioPranzo = LocalTime.parse(inizioPranzo.getText().trim(), formatoOrario);
            LocalTime oraFinePranzo = LocalTime.parse(finePranzo.getText().trim(), formatoOrario);

            giorniEOrariBean.setInizioPranzo(oraInizioPranzo);
            giorniEOrariBean.setFinePranzo(oraFinePranzo);
        }

        if (checkBoxCena.isSelected()) {
            LocalTime oraInizioCena = LocalTime.parse(inizioCena.getText().trim(), formatoOrario);
            LocalTime oraFineCena = LocalTime.parse(fineCena.getText().trim(), formatoOrario);

            giorniEOrariBean.setInizioCena(oraInizioCena);
            giorniEOrariBean.setFineCena(oraFineCena);
        }

        giorniEOrariBean.setGiorniChiusura(giorniChiusura());
        return giorniEOrariBean;
    }

    private RistoranteBean datiRistorante() throws ValidazioneException {
        RistoranteBean ristoranteBean = new RistoranteBean();

        ristoranteBean.setPartitaIVA(campoPartitaIva.getText().trim());
        ristoranteBean.setNome(campoNomeRistorante.getText());
        ristoranteBean.setTelefono(campoTelefonoRistorante.getText());
        ristoranteBean.setCucina(comboBoxCucina.getValue().getId());
        ristoranteBean.setFasciaPrezzo(comboBoxPrezzo.getValue().getId());

        ristoranteBean.setIndirizzoCompleto(campoIndirizzo.getText());
        ristoranteBean.setCap(campoCap.getText());
        ristoranteBean.setCitta(campoCitta.getText());

        ristoranteBean.setOrariApertura(orariRistorante());
        ristoranteBean.setDiete(dietaOffertaDalRistorante());
        ristoranteBean.setAmbiente(ambientiDelRistorante());

        return ristoranteBean;
    }


    private PersonaBean datiProprietarioRistorante() throws ValidazioneException {
        PersonaBean personaBean = new PersonaBean();
        personaBean.setNome(campoNome.getText().trim());
        personaBean.setCognome(campoCognome.getText().trim());
        personaBean.setTelefono(campoTelefono.getText().trim());
        personaBean.setEmail(campoEmail.getText().trim());
        personaBean.setPassword(campoPassword.getText().trim());

        personaBean.setRistoranteBean(datiRistorante());

        return personaBean;
    }


    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "Completa tutti i moduli per finalizzare la registrazione.";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

    private List<String> giorniChiusura() {
        List<String> giorniSelezionati = new ArrayList<>();

        if (checkBoxLun.isSelected()) giorniSelezionati.add(GiorniSettimana.LUNEDI.getId());
        if (checkBoxMar.isSelected()) giorniSelezionati.add(GiorniSettimana.MARTEDI.getId());
        if (checkBoxMer.isSelected()) giorniSelezionati.add(GiorniSettimana.MERCOLEDI.getId());
        if (checkBoxGio.isSelected()) giorniSelezionati.add(GiorniSettimana.GIOVEDI.getId());
        if (checkBoxVen.isSelected()) giorniSelezionati.add(GiorniSettimana.VENERDI.getId());
        if (checkBoxSab.isSelected()) giorniSelezionati.add(GiorniSettimana.SABATO.getId());
        if (checkBoxDom.isSelected()) giorniSelezionati.add(GiorniSettimana.DOMENICA.getId());

        return giorniSelezionati;
    }

    private List<String> dietaOffertaDalRistorante() {
        List<String> dietaSelezionata = new ArrayList<>();

        if (checkBoxHalal.isSelected()) dietaSelezionata.add(TipoDieta.HALAL.getId());
        if (checkBoxKosher.isSelected()) dietaSelezionata.add(TipoDieta.KOSHER.getId());
        if (checkBoxVegano.isSelected()) dietaSelezionata.add(TipoDieta.VEGANO.getId());
        if (checkBoxVegetariano.isSelected()) dietaSelezionata.add(TipoDieta.VEGETARIANO.getId());
        if (checkBoxCeliaco.isSelected()) dietaSelezionata.add(TipoDieta.CELIACO.getId());
        if (checkBoxPescetariano.isSelected()) dietaSelezionata.add(TipoDieta.PESCETARIANO.getId());
        if (checkBoxSenzaLattosio.isSelected()) dietaSelezionata.add(TipoDieta.SENZA_LATTOSIO.getId());

        return dietaSelezionata;
    }

    private List<AmbienteBean> ambientiDelRistorante() throws ValidazioneException{
        List<AmbienteBean> listaAmbientiRistorante = new ArrayList<>();

        if (checkBoxInterno.isSelected()) {
            String testo = campoCopertiInterni.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.INTERNO.getId());
            ambiente.setNumeroCoperti(coperti);

            listaAmbientiRistorante.add(ambiente);
        }

        if (checkBoxEsterno.isSelected()) {
            String testo = campoCopertiEsterni.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.ESTERNO.getId());
            ambiente.setNumeroCoperti(coperti);

            listaAmbientiRistorante.add(ambiente);
        }

        if (checkBoxEsternoCoperto.isSelected()) {
            String testo = campoCopertiEsterniCoperti.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.ESTERNO_COPERTO.getId());
            ambiente.setNumeroCoperti(coperti);
            ambiente.setExtra(extraDelRistorante());

            listaAmbientiRistorante.add(ambiente);
            }
        return listaAmbientiRistorante;
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
