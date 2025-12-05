package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RiepilogoPrenotazioneCG {
    @FXML
    private Label campoDataPrenotazione;
    @FXML
    private Label campoOraPrenotazione;
    @FXML
    private Label campoNumeroPersone;
    @FXML
    private ComboBox<String> comboBoxAmbiente;
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


    private static final Logger logger = LoggerFactory.getLogger(RiepilogoPrenotazioneCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    PrenotazioneBean prenotazione;
    RistoranteBean ristoranteSelezionato;


    public void setRiepilogoPrenotazione(PrenotazioneBean prenotazione, RistoranteBean ristoranteSelezionato) {
        this.prenotazione = prenotazione;
        this.ristoranteSelezionato = ristoranteSelezionato;

        popolaCampiPrenotazione();
        popolaCampiRistorante();
        popolaCampiUtente();
    }

    private void popolaCampiUtente(){
        try {
            prenotazione.setUtente(prenotaRistoranteController.datiUtente());
            campoNomePrenotante.setText(prenotazione.getUtente().getNome());
            campoCognomePrenotante.setText(prenotazione.getUtente().getCognome());
            campoTelefonoPrenotante.setText(prenotazione.getUtente().getTelefono());

        }catch (ValidazioneException e) {
            logger.error("Errore durante la presa dei dati dell'utente",e);
        }
    }

    private void popolaCampiRistorante(){
        campoNomeRistorante.setText(ristoranteSelezionato.getNomeRistorante());
        campoCittaRistorante.setText(ristoranteSelezionato.getPosizione().getCitta());
        campoIndirizzoRistorante.setText(ristoranteSelezionato.getPosizione().getIndirizzoCompleto() + ", " + ristoranteSelezionato.getPosizione().getCap());
        popolaCampoDieta();
        popolaComboBoxAmbiente();
    }

    private void popolaCampiPrenotazione() {
        campoDataPrenotazione.setText(String.valueOf(prenotazione.getData()));
        campoOraPrenotazione.setText(String.valueOf(prenotazione.getOra()));
        campoNumeroPersone.setText(String.valueOf(prenotazione.getNumeroPersone()));
    }


    private void popolaCampoDieta() {
        StringBuilder listaDiete = new StringBuilder();

        Set<TipoDieta> diete = ristoranteSelezionato.getTipoDieta();

        if (diete == null || diete.isEmpty()) {
            listaDiete = new StringBuilder("Assenti");
        } else {
            for (TipoDieta tipoDieta : diete) {
                listaDiete.append(tipoDieta.getId()).append(", ");
            }
            listaDiete = new StringBuilder(listaDiete.substring(0, listaDiete.length() - 2));
        }

        campoDietaPrenotante.setText(listaDiete.toString());
    }

    private void popolaComboBoxAmbiente() {
        List<String> nomiAmbienti = new ArrayList<>();

        for (AmbienteBean ambiente : ristoranteSelezionato.getAmbiente()) {
            nomiAmbienti.add(ambiente.getTipoAmbiente().getId());
        }
        ObservableList<String> lista = FXCollections.observableArrayList(nomiAmbienti);
        comboBoxAmbiente.setItems(lista);
    }


    public void clickConfermaPrenotazione(ActionEvent evento)  {
        try {
            String ambienteScelto= comboBoxAmbiente.getValue();
            if(ambienteScelto==null || ambienteScelto.isEmpty()) {
                GestoreScena.mostraAlertSenzaConferma("Attenzione", "Per procedere con la prenotazione, selezionare un tavolo.");
                return;
            }

            AmbienteBean ambienteBean= new AmbienteBean();
            ambienteBean.setAmbiente(TipoAmbiente.tipoAmbienteDaId(ambienteScelto));


            ambienteBean.setRistorante(ristoranteSelezionato.getPartitaIVA());

            prenotazione.setAmbiente(ambienteBean);
            prenotazione.setNote(campoDietaPrenotante.getText());

            if(prenotaRistoranteController.prenotaRistorante(prenotazione, ristoranteSelezionato)){
                GestoreScena.mostraAlertSenzaConferma("Successo", "Prenotazione inserita con successo");
            }

            GestoreScena.cambiaScena("/HomeUtente.fxml", evento);

        }catch (EccezioneDAO | ValidazioneException e) {
            logger.error("Errore durante l'inserimento della prenotazione : ", e);
        }
    }


}
