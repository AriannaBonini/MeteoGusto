package com.example.meteo_gusto.controller_grafico.utente;

import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.GestoreScena;
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
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListaPrenotazioniUtenteCG {
    @FXML
    private Label nomeRistorante;
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
    private Label campoNomeRistorante;
    @FXML
    private Label campoCittaRistorante;
    @FXML
    private Label campoIndirizzoRistorante;
    @FXML
    private Label campoNomePrenotante;
    @FXML
    private Label campoCognomePrenotante;
    @FXML
    private Label campoTelefonoPrenotante;
    @FXML
    private Label campoDietaPrenotante;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView listaPrenotazioni;
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
    private Label titoloRistorante;
    @FXML
    private Label titoloData;
    @FXML
    private Label titoloOra;
    @FXML
    private Button bottoneDettagli;
    @FXML
    private AnchorPane dettagliPrenotazionePane;



    private static final Logger logger = LoggerFactory.getLogger(ListaPrenotazioniUtenteCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private PersonaBean listaPrenotazioniUtente;
    private Button bottoneAttivo = null;


    public void initialize(){
        dettagliPrenotazionePane.setVisible(false);

        noticheVisualizzate();
        popolaListaPrenotazioni();
    }

    private void noticheVisualizzate() {
        try {
            prenotaRistoranteController.modificaStatoNotifica();
        }catch (ValidazioneException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione", "Errore durante la modifica dello stato notifica");
        }
    }


    private void popolaListaPrenotazioni() {
        try {
            listaPrenotazioniUtente = prenotaRistoranteController.prenotazioniUtente();
            if(listaPrenotazioniUtente==null) {
                GestoreScena.mostraAlertSenzaConferma("Informazione", "Non ci sono prenotazioni attive");
                return;
            }
            popolaVBox();

        }catch (ValidazioneException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione","Errore durante il caricamento delle prenotazioni");
            logger.error("Errore durante il caricamento delle prenotazioni dell'utente :" , e);
        }
    }

    private void popolaVBox() {
        vBoxListaPrenotazioni.getChildren().clear();

        for (PrenotazioneBean prenotazioneBean : listaPrenotazioniUtente.getPrenotazioniAttive()) {


            VBox prenotazione = SupportoComponentiGUIListaPrenotazioni.creaVBoxPrenotazione(vBoxPrenotazione);


            HBox hBoxNome = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxNomeRistorante);
            HBox hBoxData = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxDataPrenotazione);
            HBox hBoxOra = SupportoComponentiGUIListaPrenotazioni.copiaHBox(hBoxOraPrenotazione);


            Label nome = SupportoComponentiGUIListaPrenotazioni.creaLabel(nomeRistorante, prenotazioneBean.getAmbiente().getNomeRistorante());
            Label data = SupportoComponentiGUIListaPrenotazioni.creaLabel(dataPrenotazione, prenotazioneBean.getData().toString());
            Label ora = SupportoComponentiGUIListaPrenotazioni.creaLabel(oraPrenotazione, prenotazioneBean.getOra().toString());


            Label campoNome = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloRistorante, "Ristorante :");
            Label campoData = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloData, "Data :");
            Label campoOra = SupportoComponentiGUIListaPrenotazioni.creaLabel(titoloOra, "Ora :");


            Button dettagliPrenotazione = SupportoComponentiGUIListaPrenotazioni.creaBottoneScopriDiPiu(bottoneDettagli);
            dettagliPrenotazione.setUserData(prenotazioneBean);
            dettagliPrenotazione.setOnAction(this::clickDettagli);

            HBox.setMargin(dettagliPrenotazione, new Insets(0, 0, 0, 10));


            hBoxNome.getChildren().addAll(campoNome, nome);
            hBoxData.getChildren().addAll(campoData, data);
            hBoxOra.getChildren().addAll(campoOra, ora, dettagliPrenotazione);


            prenotazione.getChildren().addAll(hBoxNome, hBoxData, hBoxOra);

            vBoxListaPrenotazioni.getChildren().add(prenotazione);
        }
    }


    @FXML
    public void clickHome(MouseEvent evento) {GestoreScena.cambiaScena("/HomeUtente.fxml",evento);}

    public void clickPrenotaRistorante(MouseEvent evento) {GestoreScena.cambiaScena("/PrenotaRistoranteFormIniziale.fxml", evento);}


    @FXML
    private void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,listaPrenotazioni,"/Foto/IconaListaPrenotazioniSelezionata.png","/Foto/IconaPrenotazioniNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    public void clickDettagli(ActionEvent evento) {
        Button dettagli=  (Button)evento.getSource();
        PrenotazioneBean prenotazione= (PrenotazioneBean)dettagli.getUserData();

        dettagliPrenotazionePane.setVisible(true);

        if (bottoneAttivo != null) {
            bottoneAttivo.setDisable(false);
        }

        dettagli.setDisable(true);
        bottoneAttivo = dettagli;

        campoDataPrenotazione.setText(prenotazione.getData().toString());
        campoOraPrenotazione.setText(prenotazione.getOra().toString());
        campoNumeroPersone.setText(prenotazione.getNumeroPersone().toString());

        if(prenotazione.getAmbiente().getAmbiente().equals(TipoAmbiente.ESTERNO_COPERTO)) {
            campoAmbiente.setText("esterno coperto");
        }else {
            campoAmbiente.setText(prenotazione.getAmbiente().getAmbiente().getId());
        }

        campoNomeRistorante.setText(prenotazione.getAmbiente().getNomeRistorante());
        campoCittaRistorante.setText(prenotazione.getAmbiente().getCittaRistorante());
        campoIndirizzoRistorante.setText(prenotazione.getAmbiente().getIndirizzoCompletoRistorante());

        campoNomePrenotante.setText(listaPrenotazioniUtente.getNome());
        campoCognomePrenotante.setText(listaPrenotazioniUtente.getCognome());
        campoTelefonoPrenotante.setText(listaPrenotazioniUtente.getTelefono());

        campoDietaPrenotante.setText(prenotazione.getNote());

        dettagli.setDisable(true);

    }
}
