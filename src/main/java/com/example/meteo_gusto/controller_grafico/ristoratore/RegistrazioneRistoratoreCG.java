package com.example.meteo_gusto.controller_grafico.ristoratore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.controller_grafico.GestoreScena;
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


    RegistrazioneController registrazioneController= new RegistrazioneController();
    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneRistoratoreCG.class.getName());
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");
    private TipoPersona tipoPersona;

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

    public void setTipoRegistrazione(TipoPersona tipoPersona) { this.tipoPersona=tipoPersona;}


    @FXML
    private void clickRegistrati(ActionEvent evento) {
        try {
            RegistrazioneUtenteBean registrazioneRistoratoreBean= new RegistrazioneUtenteBean();

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

    private GiorniEOrariBean orariRistorante()  {
        GiorniEOrariBean giorniEOrariBean= new GiorniEOrariBean();
        try {

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

        }catch (ValidazioneException e) {
            mostraErroreTemporaneamenteNellaLabel(e.getMessage());
        }
        return giorniEOrariBean;
    }

    private RistoranteBean datiRistorante() throws ValidazioneException {
        RistoranteBean ristoranteBean = new RistoranteBean();

        ristoranteBean.setPartitaIVA(campoPartitaIva.getText().trim());
        ristoranteBean.setNomeRistorante(campoNomeRistorante.getText());
        ristoranteBean.setTelefonoRistorante(campoTelefonoRistorante.getText());
        ristoranteBean.setCucina(comboBoxCucina.getValue());
        ristoranteBean.setFasciaPrezzo(comboBoxPrezzo.getValue());

        PosizioneBean posizioneBean = new PosizioneBean();
        posizioneBean.setIndirizzoCompleto(campoIndirizzo.getText());
        posizioneBean.setCap(campoCap.getText());
        posizioneBean.setCitta(campoCitta.getText());
        ristoranteBean.setPosizione(posizioneBean);

        ristoranteBean.setGiorniEOrari(orariRistorante());
        ristoranteBean.setTipoDieta(dietaOffertaDalRistorante());
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
        personaBean.setTipoPersona(tipoPersona);

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

    private List<AmbienteBean> ambientiDelRistorante() throws ValidazioneException{
        List<AmbienteBean> listaAmbientiRistorante = new ArrayList<>();

        if (checkBoxInterno.isSelected()) {
            String testo = campoCopertiInterni.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.INTERNO);
            ambiente.setNumeroCoperti(coperti);

            listaAmbientiRistorante.add(ambiente);
        }

        if (checkBoxEsterno.isSelected()) {
            String testo = campoCopertiEsterni.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.ESTERNO);
            ambiente.setNumeroCoperti(coperti);

            listaAmbientiRistorante.add(ambiente);
        }

        if (checkBoxEsternoCoperto.isSelected()) {
            String testo = campoCopertiEsterniCoperti.getText();
            Integer coperti = parseCoperti(testo);
            AmbienteBean ambiente = new AmbienteBean();
            ambiente.setAmbiente(TipoAmbiente.ESTERNO_COPERTO);
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
