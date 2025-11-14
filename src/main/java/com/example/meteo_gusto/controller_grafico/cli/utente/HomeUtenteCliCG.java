package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.HomeUtenteController;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class HomeUtenteCliCG implements InterfacciaCLI {


    private List<RistoranteBean> listaRistoranti= new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(HomeUtenteCliCG.class.getName());

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> GestoreScenaCLI.vaiAPrenotaRistoranteFormIniziale();
                    case 2 -> GestoreScenaCLI.vaiAllaListaPrenotazioniUtente();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Errore ",e.getMessage());
            }
        }
        GestoreScenaCLI.login();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("BENVENUTO SU METEOGUSTO");
        GestoreOutput.stampaMessaggio("Pioggia o sole? Prenota il tuo tavolo perfetto e gusta ogni momento");

        suggerimenti();
        popolaNotifiche();

        GestoreOutput.mostraGraficaMenu("Prenota un ristorante", "Visualizza la tua lista delle prenotazioni","Logout");
        return opzioneScelta(1,3);
    }

    private void popolaNotifiche() {
        PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();
        try {
            GestoreOutput.visualizzazioneNotifiche(prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle notifiche : ", e);
        }
    }


    private void suggerimenti() {
        HomeUtenteController homeUtenteController= new HomeUtenteController();

        try {
            listaRistoranti = homeUtenteController.trovaMiglioriRistoranti();

            if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                GestoreOutput.stampaMessaggio("Stiamo aggiornando la nostra selezione: presto nuovi ristoranti da scoprire!");
                return;
            }

            mostraRistoranti();

        }catch (ValidazioneException e) {
            logger.error("Errore durante il caricamento dei ristoranti", e);
        }
    }

    private void mostraRistoranti() {
        for(RistoranteBean ristoranteBean : listaRistoranti) {
            GestoreOutput.rettangolo(ristoranteBean.getNomeRistorante(), ristoranteBean.getPosizione().getCitta(),ristoranteBean.getCucina().toString(),ristoranteBean.getMediaStelle().toString());
        }
    }
}
