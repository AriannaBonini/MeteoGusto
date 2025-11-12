package com.example.meteo_gusto.utilities.supporto_componenti_gui;

import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.controller_grafico.gui.utente.PrenotaRistoranteFormInizialeCG;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public final class SupportoGUILogout {

    private SupportoGUILogout() { /* costruttore vuoto per evitare istanziazione */ }
    private static final String ICONA_ESCI_SELEZIONATA = "/Foto/IconaEsciSelezionata.png";
    private static final String ICONA_ESCI_NON_SELEZIONATA = "/Foto/IconaEsciNonSelezionata.png";

    public static void cambiaImmaginePerLogout(ImageView immagineNuova, ImageView immaginePrecedente, String percorsoImmagineNuova, String percorsoImmaginePrecedente) {
        InputStream is = PrenotaRistoranteFormInizialeCG.class.getResourceAsStream(percorsoImmagineNuova);
        assert is != null;
        Image immagine = new Image(is);
        immagineNuova.setImage(immagine);

        is = PrenotaRistoranteFormInizialeCG.class.getResourceAsStream(percorsoImmaginePrecedente);
        assert is != null;
        immagine = new Image(is);
        immaginePrecedente.setImage(immagine);
    }

    /**
     * Esegue l’intero flusso di logout con conferma, cambio immagini e ritorno alla schermata di login.
     */
    public static boolean gestisciLogoutCompleto(ImageView iconaEsci, ImageView iconaPrecedente, String percorsoIconaPrecedenteSelezionata, String percorsoIconaPrecedenteNonSelezionata) {

        cambiaImmaginePerLogout(iconaEsci, iconaPrecedente,ICONA_ESCI_SELEZIONATA, percorsoIconaPrecedenteNonSelezionata);

        boolean conferma = GestoreScena.mostraAlertConConferma("Conferma uscita", "Sei sicuro di voler uscire?");
        if (conferma) {
            return true;
        }

        cambiaImmaginePerLogout(iconaPrecedente, iconaEsci, percorsoIconaPrecedenteSelezionata, ICONA_ESCI_NON_SELEZIONATA);
        return false;
    }
}
