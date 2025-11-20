package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller.RecensioneController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoComponentiGUISchedaRistorante;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoGUILogout;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoNotificheGUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private ComboBox<Integer> comboBoxRecensione;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView prenotaRistorante;
    @FXML
    private ImageView recensisci;
    @FXML
    private AnchorPane contenitoreDinamico;
    @FXML
    private Label notifichePrenotazione;

    private RistoranteBean ristoranteSelezionato;
    private FiltriBean filtriSelezionati;
    private MeteoBean meteoBean;
    private static final Logger logger = LoggerFactory.getLogger(ProfiloRistoranteCG.class.getName());


    public void initialize() {
        comboBoxRecensione.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        recensisci.setVisible(false);

        comboBoxRecensione.valueProperty().addListener((obs, oldVal, newVal) -> recensisci.setVisible(newVal != null));
        popolaNotifiche();
    }


    private void popolaNotifiche() {
        try {
            PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle motifiche : ",e );
        }
    }


    public void setRistoranteSelezionato(RistoranteBean ristoranteSelezionato) {

        this.ristoranteSelezionato = ristoranteSelezionato;
        popolaInfoRistorante();
    }
    public void setFiltriCorrenti(FiltriBean filtriSelezionati, MeteoBean meteoBean) {
        this.filtriSelezionati=filtriSelezionati;
        this.meteoBean=meteoBean;
    }

    private void popolaInfoRistorante() {
        nomeRistorante.setText(ristoranteSelezionato.getNomeRistorante());
        infoIndirizzoCivicoCittaCAP.setText(" • " + ristoranteSelezionato.getPosizione().getIndirizzoCompleto() + " • " + ristoranteSelezionato.getPosizione().getCitta() + " • " + ristoranteSelezionato.getPosizione().getCap());
        infoTelefono.setText(" • " + ristoranteSelezionato.getTelefonoRistorante());
        mediaStelle.setText(ristoranteSelezionato.getMediaStelle()+"/5");
        GiorniEOrariBean giorniEOrariBean= ristoranteSelezionato.getGiorniEOrari();

        if(giorniEOrariBean.getInizioPranzo()==null && giorniEOrariBean.getFinePranzo()==null) {
            infoGiorniOrariPranzoCena.setText(" • Non effettua pranzo " + " • " + giorniEOrariBean.getInizioCena() + " - " + giorniEOrariBean.getFineCena());
        }else if(giorniEOrariBean.getInizioCena()==null && giorniEOrariBean.getFineCena()==null) {
            infoGiorniOrariPranzoCena.setText(" • " + giorniEOrariBean.getInizioPranzo() + " - " + giorniEOrariBean.getFinePranzo() + " • Non effettua cena");

        }else {
            infoGiorniOrariPranzoCena.setText(" • " + giorniEOrariBean.getInizioPranzo() + " - " + giorniEOrariBean.getFinePranzo() + " • " + giorniEOrariBean.getInizioCena() + " - " + giorniEOrariBean.getFineCena());
        }

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
                (PrenotaRistoranteCG controller) -> controller.impostaFiltriSelezionati(filtriSelezionati,meteoBean));
    }

    @FXML
    public void clickPrenota() {
        try {
            PrenotazioneBean prenotazione = popolaPrenotazione();

            GestoreScena.sostituisciContenutoConParametri(contenitoreDinamico, "/RiepilogoPrenotazione.fxml", controller -> ((RiepilogoPrenotazioneCG) controller).setRiepilogoPrenotazione(prenotazione, ristoranteSelezionato));
        }catch (ValidazioneException e) {
            logger.error("Errore durante la popolazione della bean prenotazione", e);
        }
    }


    private PrenotazioneBean popolaPrenotazione() throws ValidazioneException {
        /* Dati prenotazione */
        PrenotazioneBean prenotazioneBean= new PrenotazioneBean();
        prenotazioneBean.setData(filtriSelezionati.getData());
        prenotazioneBean.setOra(filtriSelezionati.getOra());
        prenotazioneBean.setNumeroPersone(filtriSelezionati.getNumeroPersone());

        return prenotazioneBean;
    }

    @FXML
    public void clickMenu(){GestoreScena.mostraAlertSenzaConferma("Siamo spiacenti","Sezione menù non disponibile");}

    @FXML
    public void clickHome(MouseEvent evento) {GestoreScena.cambiaScena("/HomeUtente.fxml",evento);}
    @FXML
    public void clickListaPrenotazioni(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniUtente.fxml",evento);}
    @FXML
    public void clickEsci(MouseEvent evento) {
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,prenotaRistorante,"/Foto/ClocheSelezionata.png","/Foto/ClocheNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    public void clickRecensisci() {
        try {
            RecensioneController recensioneController = new RecensioneController();
            RecensioneBean recensioneBean = new RecensioneBean();

            Integer numeroStelle = comboBoxRecensione.getValue();
            if (numeroStelle != null) {
                recensioneBean.setStelle(BigDecimal.valueOf(numeroStelle));

                recensioneBean.setRistorante(ristoranteSelezionato);
                recensioneController.recensisciRistorante(recensioneBean);

                ristoranteSelezionato.setMediaStelle(recensioneController.nuovaMediaRecensione(ristoranteSelezionato).getMediaStelle());
                SupportoComponentiGUISchedaRistorante.immagineStellaRistorante(ristoranteSelezionato, hBoxStelle);

                recensisci.setVisible(false);

            } else {
                GestoreScena.mostraAlertSenzaConferma("Recensione non valida", "Per favore, inserisci una valutazione compresa tra 1 e 5.");

            }
        }catch (EccezioneDAO e) {
            GestoreScena.mostraAlertSenzaConferma("Errore", "Inserimento recensione non riuscito");
        }
    }

}
