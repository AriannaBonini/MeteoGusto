package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class ListaPrenotazioniUtenteCliCG implements InterfacciaCLI {

    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private PersonaBean listaPrenotazioniUtente;
    private static final Logger logger = LoggerFactory.getLogger(ListaPrenotazioniUtenteCliCG.class.getName());

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
                    case 1 -> GestoreScenaCLI.vaiAPrenotaRistorante();
                    case 2 -> GestoreScenaCLI.viaAllaHomeUtente();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        GestoreScenaCLI.logout();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("Menù: ");
        GestoreOutput.mostraGraficaMenu("Prenota un ristorante", "Vai alla Home","Logout");
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
            listaPrenotazioniUtente = prenotaRistoranteController.prenotazioniUtente();
            if (listaPrenotazioniUtente == null) {
                GestoreOutput.mostraAvvertenza("Informazione", "Non ci sono prenotazioni attive");
                return;
            }
            mostraListaPrenotazioni();

        }catch (ValidazioneException e) {
            logger.error("Errore durante il caricamento delle prenotazioni dell'utente :" , e);

        }
    }

    private void mostraListaPrenotazioni() {
        int indice=1;
        for(PrenotazioneBean prenotazioneBean : listaPrenotazioniUtente.getPrenotazioniAttive()) {
            GestoreOutput.rettangolo("Numero Prenotazione : " + indice, "Ristorante : " + prenotazioneBean.getAmbiente().getNomeRistorante(), "Data : " + prenotazioneBean.getData().toString(), "Ora : " + prenotazioneBean.getOra().toString());
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
        PrenotazioneBean prenotazioneBean= listaPrenotazioniUtente.getPrenotazioniAttive().get(indiceScelto);

        GestoreOutput.stampaTitolo("DETTAGLI");

        GestoreOutput.rettangolo("Dati Prenotazione " + ++indiceScelto, prenotazioneBean.getData().toString(),prenotazioneBean.getOra().toString(),
                prenotazioneBean.getNumeroPersone().toString(), prenotazioneBean.getAmbiente().getTipoAmbiente().toString());

        AmbienteBean ambienteBean= prenotazioneBean.getAmbiente();

        GestoreOutput.rettangolo("Dati Ristorante",ambienteBean.getNomeRistorante(),ambienteBean.getCittaRistorante(),ambienteBean.getIndirizzoCompletoRistorante());

        GestoreOutput.rettangolo("Dati Prenotante ", listaPrenotazioniUtente.getNome(), listaPrenotazioniUtente.getCognome(), listaPrenotazioniUtente.getTelefono(), prenotazioneBean.getNote());

        GestoreOutput.mostraGraficaMenu("Tornare alla lista delle prenotazioni","tornare al manù");
        if(opzioneScelta(1,2)==2) {
            return;
        }

        mostraListaPrenotazioni();
    }


}

