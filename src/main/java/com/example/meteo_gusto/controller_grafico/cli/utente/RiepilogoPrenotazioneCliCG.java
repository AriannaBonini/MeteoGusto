package com.example.meteo_gusto.controller_grafico.cli.utente;


import com.example.meteo_gusto.bean.PersonaBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrenotazioneEsistenteException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class RiepilogoPrenotazioneCliCG implements InterfacciaCLI {

    private PrenotazioneBean prenotazioneBean;
    private final PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();
    private static final Logger logger = LoggerFactory.getLogger(RiepilogoPrenotazioneCliCG.class.getName());

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> prenota();
                    case 2 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,e.getMessage());
            }
        }
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("RIEPILOGO DELLA PRENOTAZIONE ");

        popolaCampiPrenotazione();
        popolaCampiRistorante();
        popolaCampiUtente();


        GestoreOutput.mostraGraficaMenu("Prenota","Torna indietro");
        return opzioneScelta(1,2);
    }


    private void prenota() {
        GestoreOutput.stampaMessaggio("Per procedere con la conferma della prenotazione, scegli l'ambiente");
        try {
            prenotazioneBean.setAmbiente(Collections.singletonList(scegliAmbiente()));


            if(prenotaRistoranteController.prenotaRistorante(prenotazioneBean)){
                GestoreOutput.mostraAvvertenza("Successo", "Prenotazione inserita con successo");
            }

            GestoreScenaCLI.vaiAllaHomeUtente();


        }catch (ValidazioneException e) {
            logger.error("Errore durante l'assegnazione dell'ambiete nella prenotazione ",e);
        } catch (EccezioneDAO e) {
            logger.error("Errore durante la registrazione della nuova prenotazione ",e );
        } catch (PrenotazioneEsistenteException e) {
            GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            GestoreScenaCLI.vaiAllaHomeUtente();
        }

    }

    private String scegliAmbiente() {

        int indiceDaMostrare;
        for (int indice=0 ; indice<prenotazioneBean.getAmbiente().size(); indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(indiceDaMostrare + " ) " + prenotazioneBean.getAmbiente().get(indice));
        }
        GestoreOutput.stampaMessaggio("Scegli l'ambiente digitando il suo numero ");
        int scelta=opzioneScelta(1,prenotazioneBean.getAmbiente().size());

        GestoreOutput.stampaMessaggio(prenotazioneBean.getAmbiente().get(scelta-1));
        return prenotazioneBean.getAmbiente().get(scelta-1);



    }

    private void popolaCampiUtente() {
        try {
            GestoreOutput.stampaTitolo("Dati prenotante");

            PersonaBean utenteBean= prenotaRistoranteController.datiUtente();
            GestoreOutput.stampaMessaggio("Nome : " + utenteBean.getNome());
            GestoreOutput.stampaMessaggio("Cognome : " + utenteBean.getCognome());
            GestoreOutput.stampaMessaggio("Telefono : " + utenteBean.getTelefono());

        }catch (ValidazioneException e) {
            logger.error("Errore durante la presa dei dati dell'utente",e);
        }
    }


    private void popolaCampiPrenotazione() {
        GestoreOutput.stampaTitolo("Dati prenotazione :");
        GestoreOutput.stampaMessaggio("Data :"  + prenotazioneBean.getData().toString());
        GestoreOutput.stampaMessaggio("Ora : " + prenotazioneBean.getOra().toString());
        GestoreOutput.stampaMessaggio("Numero persone : " + prenotazioneBean.getNumeroPersone().toString());

    }

    private void popolaCampiRistorante(){
        GestoreOutput.stampaTitolo("Dati ristorante :");
        GestoreOutput.stampaMessaggio("Nome : " + prenotazioneBean.getRistorante().getNome());
        GestoreOutput.stampaMessaggio("Città : " + prenotazioneBean.getRistorante().getCitta());
        GestoreOutput.stampaMessaggio("Indirizzo :" + prenotazioneBean.getRistorante().getIndirizzoCompleto() + ", " + prenotazioneBean.getRistorante().getCap());
        popolaCampoDieta();

    }

    private void popolaCampoDieta() {
        String listaDiete = String.join(", ", prenotazioneBean.getNote());

        if(listaDiete.isEmpty()) {
            GestoreOutput.stampaMessaggio("Assenti");
        }

        GestoreOutput.stampaMessaggio("Dieta : " + listaDiete);
    }


    public void setRiepilogoPrenotazione(PrenotazioneBean prenotazioneBean) {
        this.prenotazioneBean= prenotazioneBean;
    }


}
