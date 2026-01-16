package com.example.meteo_gusto.controller_grafico.gui.utente;


import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrenotazioneEsistenteException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;


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
    private PrenotazioneBean prenotazione;


    public void setRiepilogoPrenotazione(PrenotazioneBean prenotazione) {
        this.prenotazione = prenotazione;

        popolaCampiPrenotazione();
        popolaCampiRistorante();
        popolaCampiUtente();
    }

    private void popolaCampiUtente(){
        try {
            PersonaBean personaBean= prenotaRistoranteController.datiUtente();

            campoNomePrenotante.setText(personaBean.getNome());
            campoCognomePrenotante.setText(personaBean.getCognome());
            campoTelefonoPrenotante.setText(personaBean.getTelefono());

        }catch (ValidazioneException e) {
            logger.error("Errore durante la presa dei dati dell'utente",e);
        }
    }

    private void popolaCampiRistorante(){
        campoNomeRistorante.setText(prenotazione.getRistorante().getNome());
        campoCittaRistorante.setText(prenotazione.getRistorante().getCitta());
        campoIndirizzoRistorante.setText(prenotazione.getRistorante().getIndirizzoCompleto() + ", " + prenotazione.getRistorante().getCap());
        popolaCampoDieta();
        popolaComboBoxAmbiente();
    }

    private void popolaCampiPrenotazione() {
        campoDataPrenotazione.setText(String.valueOf(prenotazione.getData()));
        campoOraPrenotazione.setText(String.valueOf(prenotazione.getOra()));
        campoNumeroPersone.setText(String.valueOf(prenotazione.getNumeroPersone()));
    }


    private void popolaCampoDieta() {

        String dieteSelezionate = prenotazione.getNote();

        if (dieteSelezionate == null || dieteSelezionate.isEmpty()) {
            campoDietaPrenotante.setText("Assenti");
        } else {
            campoDietaPrenotante.setText(String.join(", ", prenotazione.getNote()));
        }

    }

    private void popolaComboBoxAmbiente() {
        ObservableList<String> lista = FXCollections.observableArrayList(prenotazione.getAmbiente());
        comboBoxAmbiente.setItems(lista);
    }


    @FXML
    private void clickConfermaPrenotazione(ActionEvent evento)  {
        try {
            String ambienteScelto= comboBoxAmbiente.getValue();
            if(ambienteScelto==null || ambienteScelto.isEmpty()) {
                GestoreScena.mostraAlertSenzaConferma("Attenzione", "Per procedere con la prenotazione, selezionare un tavolo.");
                return;
            }

            prenotazione.setAmbiente(Collections.singletonList(ambienteScelto));
            prenotazione.setNote(campoDietaPrenotante.getText());

            if(prenotaRistoranteController.prenotaRistorante(prenotazione)){
                boolean risposta = GestoreScena.mostraAlertConConfermaTornaAllaHome("Successo", "Prenotazione inserita con successo");

                if(risposta) {
                    GestoreScena.cambiaScena("/HomeUtente.fxml", evento);
                }
            }

        }catch (EccezioneDAO e) {
            logger.error("Errore durante la registrazione della nuova prenotazione ",e );
        }catch (ValidazioneException e) {
            logger.error("Errore durante l'assegnazione dell'ambiete nella prenotazione ",e);
        } catch (PrenotazioneEsistenteException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione",e.getMessage());
        }
    }

}
