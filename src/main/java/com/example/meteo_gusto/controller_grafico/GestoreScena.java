package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.utilities.AlertPersonalizzato;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.function.Consumer;

public class GestoreScena {

    private GestoreScena(){}


    /**
     * Metodo per cambiare scena SENZA ActionEvent (usato nel main)
     */
    public static void cambiaScenaSenzaEvento(String percorso, Stage finestra) {
        try {
            FXMLLoader caricatore = new FXMLLoader(GestoreScena.class.getResource(percorso));
            Parent radice = caricatore.load();

            finestra.setScene(new Scene(radice));

            finestra.setResizable(false);
            finestra.centerOnScreen();


            finestra.show();

        } catch (IOException e) {
            mostraErrore("Impossibile caricare la schermata: " + percorso);
        }
    }


    /**
     * Metodo principale per cambiare scena
     * Usa questo per la maggior parte dei casi
     * @param percorso il percorso del file FXML della nuova scena
     * @param evento l'evento che ha scatenato il cambio scena
     */
    public static void cambiaScena(String percorso, Event evento) {
        try {
            Stage finestra = (Stage) ((Node) evento.getSource()).getScene().getWindow();

            FXMLLoader caricatore = new FXMLLoader(GestoreScena.class.getResource(percorso));
            Parent radice = caricatore.load();

            finestra.setScene(new Scene(radice));
            finestra.show();

        } catch (IOException e) {
            mostraErrore("Impossibile caricare la schermata: " + percorso);
        }
    }

    /**
     * Cambia scena permettendo di configurare il controller prima di mostrare
     * Usa questo quando devi passare dati tra le schermate
     * @param percorso il percorso del file FXML
     * @param evento l'evento che ha scatenato il cambio
     * @param gestoreController funzione per configurare il controller
     */
    public static <T> void cambiaScenaConParametri(String percorso, ActionEvent evento, Consumer<T> gestoreController) {

        try {
            FXMLLoader caricatore = new FXMLLoader(GestoreScena.class.getResource(percorso));
            Parent radice = caricatore.load();

            T controller = caricatore.getController();

            gestoreController.accept(controller);

            Stage finestra = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            finestra.setScene(new Scene(radice));
            finestra.show();

        } catch (IOException e) {
            mostraErrore("Errore durante il caricamento della schermata: " + percorso);
        }
    }

    /**
     * Mostra un messaggio di errore all'utente
     * @param messaggio il messaggio di errore da visualizzare
     */
    private static void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public static boolean mostraAlertConConferma(String titolo, String messaggio) {
        return new AlertPersonalizzato().mostraAlertConferma(titolo,messaggio);
    }

    public static void mostraAlertSenzaConferma(String titolo, String messaggio) {
        new AlertPersonalizzato().mostraAlertInfo(titolo,messaggio);
    }

}

