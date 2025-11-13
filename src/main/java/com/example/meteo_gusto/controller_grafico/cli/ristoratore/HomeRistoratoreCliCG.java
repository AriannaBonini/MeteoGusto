package com.example.meteo_gusto.controller_grafico.cli.ristoratore;

import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class HomeRistoratoreCliCG implements InterfacciaCLI {

    private static final Logger logger = LoggerFactory.getLogger(HomeRistoratoreCliCG.class.getName());

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> GestoreScenaCLI.vaiAlMenu();
                    case 2 -> GestoreScenaCLI.vaiAllaListaPrenotazioniRistorante();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Errore ",e.getMessage());
            }
        }
        GestoreScenaCLI.logout();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("BENVENUTO SU METEOGUSTO");
        GestoreOutput.stampaMessaggio("Tieniti pronto a ricevere i tuoi clienti, con il meteo dalla tua parte");

        suggerimenti();
        popolaNotifiche();

        GestoreOutput.mostraGraficaMenu("Visualizza il tuo menù", "Visualizza la tua lista delle prenotazioni","Logout");
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
        GestoreOutput.stampaTitolo("Idee stagionali per arricchire il tuo menù ! ");

        GestoreOutput.rettangolo("Tagliatelle piselli e menta","Piatto fresco e profumato, ideale per la primavera");
        GestoreOutput.rettangolo("Insalata di mare","Un piatto fresco e leggero, adatto per l'estate.");
        GestoreOutput.rettangolo("Risotto alla zucca ","Piatto caldo e cremoso, perfetto per l'autunno");
        GestoreOutput.rettangolo("Polenta con spuntatura", "Piatto caldo e sostanzioso, perfetto per le freddi giornate invernali");

    }
}
