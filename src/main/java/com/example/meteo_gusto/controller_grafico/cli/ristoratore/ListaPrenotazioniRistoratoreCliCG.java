package com.example.meteo_gusto.controller_grafico.cli.ristoratore;


import com.example.meteo_gusto.bean.PrenotazioneBean;
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

public class ListaPrenotazioniRistoratoreCliCG implements InterfacciaCLI {

    private List<PrenotazioneBean> listaPrenotazioniRistorante= new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ListaPrenotazioniRistoratoreCliCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();
        inizializzazioneCLI();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> GestoreScenaCLI.vaiAlMenu();
                    case 2 -> GestoreScenaCLI.vaiAllaHomeRistoratore();
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
        GestoreOutput.stampaMessaggio("Tieniti pronto a ricevere i tuoi clienti, con il meteo dalla tua parte");


        GestoreOutput.mostraGraficaMenu("Visualizza il tuo menù", "Vai alla Home","Logout");
        return opzioneScelta(1,3);
    }


    private void inizializzazioneCLI() {
        GestoreOutput.stampaTitolo("LE MIE PRENOTAZIONI ");

        noticheVisualizzate();
        popolaLista();
    }


    private void noticheVisualizzate() {
        try {
            prenotaRistoranteController.modificaStatoNotifica();
        }catch (ValidazioneException e) {
            logger.error("Errore durante la modifica dello stato della notifica : ", e);
        }
    }


    private void popolaLista() {
        try {
            listaPrenotazioniRistorante = prenotaRistoranteController.prenotazioniRistoratore();
            if (listaPrenotazioniRistorante == null) {
                GestoreOutput.mostraAvvertenza("Informazione", "Non ci sono prenotazioni attive");
                return;
            }
            mostraListaPrenotazioni();

        }catch (ValidazioneException e) {
            logger.error("Errore durante il caricamento delle prenotazioni del ristoratore :" , e);

        }
    }

    private void mostraListaPrenotazioni() {
        int indice=1;
        for(PrenotazioneBean prenotazioneBean : listaPrenotazioniRistorante) {
            GestoreOutput.rettangolo("Numero Prenotazione : " + indice, "Prenotante : " + prenotazioneBean.getUtente().getNome() + " " + prenotazioneBean.getUtente().getCognome(), "Data : " + prenotazioneBean.getData().toString(), "Ora : " + prenotazioneBean.getOra().toString());
            indice++;
        }

        GestoreOutput.stampaMessaggio("Digita il numero della prenotazione per visualizzarne i dettagli, altrimenti digita " + indice + " per tornare al menu");
        int scelta=opzioneScelta(1,indice);

        if(indice == scelta) {
            return;
        }

        mostraDettagliPrenotazione(scelta-1);

    }


    private void mostraDettagliPrenotazione(Integer indiceScelto) {
        PrenotazioneBean prenotazioneBean= listaPrenotazioniRistorante.get(indiceScelto);

        GestoreOutput.stampaTitolo("DETTAGLI");

        GestoreOutput.rettangolo("Dati Prenotazione ", prenotazioneBean.getData().toString(),prenotazioneBean.getOra().toString(),
                prenotazioneBean.getNumeroPersone().toString() + " persone", "Ambiente : " + prenotazioneBean.getAmbiente().getTipoAmbiente().toString());


        GestoreOutput.rettangolo("Dati Prenotante ", prenotazioneBean.getUtente().getNome(), prenotazioneBean.getUtente().getCognome(), prenotazioneBean.getUtente().getTelefono(), prenotazioneBean.getNote());

        GestoreOutput.mostraGraficaMenu("Tornare alla lista delle prenotazioni","tornare al manù");
        if(opzioneScelta(1,2)==2) {
            return;
        }

        mostraListaPrenotazioni();
    }

}
