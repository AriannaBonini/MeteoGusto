package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.GiorniEOrariBean;
import com.example.meteo_gusto.bean.RecensioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller.RecensioneController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.SupportoComponentiGUISchedaRistorante;
import com.example.meteo_gusto.utilities.SupportoGUILogout;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfiloRistoranteCG {

    @FXML
    private Label nomeRistorante;
    @FXML
    private Label infoIndirizzoCivicoCittaCAP;
    @FXML
    private HBox hBoxStelle;
    @FXML
    private ImageView fotoRistorante;
    @FXML
    private Label mediaStelle;
    @FXML
    private Label infoTelefono;
    @FXML
    private Label infoGiorniOrariPranzoCena;
    @FXML
    private Label infoCucinaDieta;
    @FXML
    private HBox hBoxDollari;
    @FXML
    private Button menu;
    @FXML
    private Button prenota;
    @FXML
    private Button calendario;
    @FXML
    private ComboBox<Integer> comboBoxRecensione;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView prenotaRistorante;
    @FXML
    private ImageView recensisci;


    private RistoranteBean ristoranteSelezionato;
    private FiltriBean filtriSelezionati;

    public void initialize() {
        comboBoxRecensione.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        recensisci.setVisible(false);

        comboBoxRecensione.valueProperty().addListener((obs, oldVal, newVal) -> recensisci.setVisible(newVal != null));

    }

    public void setRistoranteSelezionato(RistoranteBean ristoranteSelezionato) {
        this.ristoranteSelezionato = ristoranteSelezionato;
        popolaInfoRistorante();
    }
    public void setFiltriCorrenti(FiltriBean filtriSelezionati) {
        this.filtriSelezionati=filtriSelezionati;
    }

    private void popolaInfoRistorante() {
        nomeRistorante.setText(ristoranteSelezionato.getNomeRistorante());
        infoIndirizzoCivicoCittaCAP.setText(" • " + ristoranteSelezionato.getPosizione().getIndirizzoCompleto() + " • " + ristoranteSelezionato.getPosizione().getCitta() + " • " + ristoranteSelezionato.getPosizione().getCap());
        infoTelefono.setText(" • " + ristoranteSelezionato.getTelefonoRistorante());
        mediaStelle.setText(ristoranteSelezionato.getMediaStelle()+"/5");
        GiorniEOrariBean giorniEOrariBean= ristoranteSelezionato.getGiorniEOrari();
        infoGiorniOrariPranzoCena.setText(" • " + giorniEOrariBean.getInizioPranzo() + " - " + giorniEOrariBean.getFinePranzo() + " • " + giorniEOrariBean.getInizioCena() + " - " + giorniEOrariBean.getFineCena());
        infoCucinaDieta.setText(" • " + ristoranteSelezionato.getCucina().getId() + " • " + stampaDieteRistorante());

        fotoRistorante.setImage(SupportoComponentiGUISchedaRistorante.immagineCucinaRistorante(ristoranteSelezionato));
        SupportoComponentiGUISchedaRistorante.immagineFasciaPrezzoRistorante(ristoranteSelezionato,hBoxDollari, false);
        SupportoComponentiGUISchedaRistorante.immagineStellaRistorante(ristoranteSelezionato,hBoxStelle);

    }
    private String stampaDieteRistorante() {
        Set<TipoDieta> diete = ristoranteSelezionato.getTipoDieta();
        if (diete == null || diete.isEmpty()) {
            return "Nessuna dieta disponibile";
        }
        return diete.stream()
                .map(TipoDieta::getId)
                .collect(Collectors.joining(" • "));
    }

    @FXML
    public void clickTornaIndietro(MouseEvent evento) {
        GestoreScena.cambiaScenaConParametri("/PrenotaRistorante.fxml", evento,
                (PrenotaRistoranteCG controller) -> controller.setFiltriBean(filtriSelezionati));
    }

    @FXML
    public void clickCalendario(){}
    @FXML
    public void clickPrenota(ActionEvent evento){GestoreScena.cambiaScena("/RiepilogoPrenotazione.fxml", evento);}

    @FXML
    public void clickMenu(){}


    public void clickProfiloPersonale(MouseEvent event) {
    }

    public void clickListaPrenotazioni(MouseEvent event) {
    }

    public void clickEsci(MouseEvent evento) {
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,prenotaRistorante,"/Foto/ClocheSelezionata.png","/Foto/ClocheNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    public void clickRecensisci(MouseEvent evento) {
        try {
            RecensioneController recensioneController = new RecensioneController();
            RecensioneBean recensioneBean = new RecensioneBean();

            Integer numeroStelle = comboBoxRecensione.getValue();
            if (numeroStelle != null) {
                recensioneBean.setStelle(BigDecimal.valueOf(numeroStelle));
            }

            recensioneBean.setRistorante(ristoranteSelezionato);
            recensioneController.recensisciRistorante(recensioneBean);
            ristoranteSelezionato.setMediaStelle(recensioneController.nuovaMediaRecensione(ristoranteSelezionato).getMediaStelle());
            SupportoComponentiGUISchedaRistorante.immagineStellaRistorante(ristoranteSelezionato,hBoxStelle);

        }catch (EccezioneDAO e) {
            GestoreScena.mostraAlertSenzaConferma("Errore", "Inserimento recensione non riuscito");
        }
    }

}
