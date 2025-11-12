package com.example.meteo_gusto.controller_grafico.gui.ristoratore;

import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoComponentiGUIListaPrenotazioni;
import com.example.meteo_gusto.utilities.supporto_componenti_gui.SupportoGUILogout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class ListaPrenotazioniRistoranteCG {

    @FXML
    private ImageView esci;
    @FXML
    private ImageView calendario;
    @FXML
    private Label nomePrenotante;
    @FXML
    private Label dataPrenotazione;
    @FXML
    private Label oraPrenotazione;
    @FXML
    private Label campoDataPrenotazione;
    @FXML
    private Label campoOraPrenotazione;
    @FXML
    private Label campoNumeroPersone;
    @FXML
    private Label campoAmbiente;
    @FXML
    private Label campoNomePrenotante;
    @FXML
    private Label campoCognomePrenotante;
    @FXML
    private Label campoTelefonoPrenotante;
    @FXML
    private Label campoDietaPrenotante;
    @FXML
    private AnchorPane dettagliPrenotazionePane;
    @FXML
    private VBox vBoxListaPrenotazioni;
    @FXML
    private VBox vBoxPrenotazione;
    @FXML
    private HBox hBoxNomeRistorante;
    @FXML
    private HBox hBoxDataPrenotazione;
    @FXML
    private HBox hBoxOraPrenotazione;
    @FXML
    private Button bottoneDettagli;
    @FXML
    private Label titoloData;
    @FXML
    private Label titoloPrenotante;
    @FXML
    private Label titoloOra;


    PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();
    private static final Logger logger = LoggerFactory.getLogger(ListaPrenotazioniRistoranteCG.class.getName());
    private List<PrenotazioneBean> listaPrenotazioniRistorante= new ArrayList<>();
    private Button bottoneAttivo = null;


    public void initialize() {
        dettagliPrenotazionePane.setVisible(false);

        noticheVisualizzate();
        popolaListaPrenotazioni();

    }

    private void noticheVisualizzate() {
        try {
            prenotaRistoranteController.modificaStatoNotifica();
        }catch (ValidazioneException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione", "Errore durante la modifica dello stato notifica");
            logger.error("Errore durante il caricamento delle notifiche : ", e);
        }
    }


    private void popolaListaPrenotazioni() {
        try {
            listaPrenotazioniRistorante = prenotaRistoranteController.prenotazioniRistoratore();
            if(listaPrenotazioniRistorante==null) {
                GestoreScena.mostraAlertSenzaConferma("Informazione", "Non ci sono prenotazioni attive");
                return;
            }

            popolaVBox();

        }catch (ValidazioneException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione","Errore durante il caricamento delle prenotazioni");
            logger.error("Errore durante il caricamento delle prenotazioni del ristoratore : " , e);
        }
    }

    private void popolaVBox() {
        vBoxListaPrenotazioni.getChildren().clear();

        for (PrenotazioneBean prenotazioneBean : listaPrenotazioniRistorante) {


            VBox prenotazione = SupportoComponentiGUIListaPrenotazioni.creaVBoxPrenotazione(vBoxPrenotazione);


            HBox hBoxNome = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxNomeRistorante);
            HBox hBoxData = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxDataPrenotazione);
            HBox hBoxOra = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxOraPrenotazione);


            Label nome = SupportoComponentiGUIListaPrenotazioni.creaLabel(nomePrenotante, prenotazioneBean.getUtente().getNome() + " " + prenotazioneBean.getUtente().getCognome());
            Label data = SupportoComponentiGUIListaPrenotazioni.creaLabel(dataPrenotazione, prenotazioneBean.getData().toString());
            Label ora = SupportoComponentiGUIListaPrenotazioni.creaLabel(oraPrenotazione, prenotazioneBean.getOra().toString());


            Label campoNome = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloPrenotante, "Prenotante :");
            Label campoOra = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloOra, "Ora :");
            Label campoData = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloData, "Data :");

            Button dettagliPrenotazione = SupportoComponentiGUIListaPrenotazioni.creaBottoneScopriDiPiu(bottoneDettagli);
            HBox.setMargin(dettagliPrenotazione, new Insets(0, 0, 0, 10));


            dettagliPrenotazione.setUserData(prenotazioneBean);
            dettagliPrenotazione.setOnAction(this::clickDettagli);


            hBoxNome.getChildren().addAll(campoNome, nome);
            hBoxData.getChildren().addAll(campoData, data);
            hBoxOra.getChildren().addAll(campoOra, ora, dettagliPrenotazione);


            prenotazione.getChildren().addAll(hBoxNome, hBoxData, hBoxOra);

            vBoxListaPrenotazioni.getChildren().add(prenotazione);
        }
    }

    @FXML
    public void clickHome(MouseEvent evento) {
        GestoreScena.cambiaScena("/HomeRistoratore.fxml",evento);
    }

    @FXML
    public void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,calendario,"/Foto/IconaListaPrenotazioniRistoratoreSelezionata.png","/Foto/IconaListaPrenotazioniRistoratoreNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    public void clickDettagli(ActionEvent evento) {
        Button dettagliPrenotazione;
        dettagliPrenotazione = (Button)evento.getSource();
        PrenotazioneBean prenotazione;
        prenotazione = (PrenotazioneBean) dettagliPrenotazione.getUserData();


        if (bottoneAttivo != null) {
            bottoneAttivo.setDisable(false);
        }
        dettagliPrenotazionePane.setVisible(true);

        dettagliPrenotazione.setDisable(true);
        bottoneAttivo = dettagliPrenotazione;

        campoDataPrenotazione.setText(prenotazione.getData().toString());
        campoNumeroPersone.setText(prenotazione.getNumeroPersone().toString());
        campoOraPrenotazione.setText(prenotazione.getOra().toString());


        if(prenotazione.getAmbiente().getAmbiente().equals(TipoAmbiente.ESTERNO_COPERTO)) {
            campoAmbiente.setText("esterno coperto");
        }else {
            campoAmbiente.setText(prenotazione.getAmbiente().getAmbiente().getId());
        }

        campoNomePrenotante.setText(prenotazione.getUtente().getNome());
        campoCognomePrenotante.setText(prenotazione.getUtente().getCognome());
        campoTelefonoPrenotante.setText(prenotazione.getUtente().getTelefono());

        campoDietaPrenotante.setText(prenotazione.getNote());

        dettagliPrenotazione.setDisable(true);

    }

    public void clickMenu(MouseEvent event) {
    }
}
