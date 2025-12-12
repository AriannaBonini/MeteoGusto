package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrenotazioneEsistenteException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class RiepilogoPrenotazioneCliCG implements InterfacciaCLI {

    private PrenotazioneBean prenotazioneBean;
    private RistoranteBean ristoranteSelezionato;
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
            prenotazioneBean.setAmbiente(scegliAmbiente());
            prenotazioneBean.getAmbiente().setRistorante(ristoranteSelezionato.getPartitaIVA());


            if(prenotaRistoranteController.prenotaRistorante(prenotazioneBean, ristoranteSelezionato)){
                GestoreOutput.mostraAvvertenza("Successo", "Prenotazione inserita con successo");
            }

            GestoreScenaCLI.viaAllaHomeUtente();


        }catch (ValidazioneException e) {
            logger.error("Errore durante l'assegnazione dell'ambiete nella prenotazione ",e);
        } catch (EccezioneDAO e) {
            logger.error("Errore durante la registrazione della nuova prenotazione ",e );
        } catch (PrenotazioneEsistenteException e) {
            GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
        }

    }

    private AmbienteBean scegliAmbiente() {

        int indiceDaMostrare;
        for (int indice=0 ; indice<ristoranteSelezionato.getAmbiente().size(); indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(indiceDaMostrare + " ) " + ristoranteSelezionato.getAmbiente().get(indice).getTipoAmbiente().getId());
        }
        GestoreOutput.stampaMessaggio("Scegli l'ambiente digitando il suo numero ");
        int scelta=opzioneScelta(1,ristoranteSelezionato.getAmbiente().size());

        GestoreOutput.stampaMessaggio(ristoranteSelezionato.getAmbiente().get(scelta-1).getAmbienteId().toString());
        return ristoranteSelezionato.getAmbiente().get(scelta-1);



    }

    private void popolaCampiUtente() {
        try {
            GestoreOutput.stampaTitolo("Dati prenotante");

            prenotazioneBean.setUtente(prenotaRistoranteController.datiUtente());
            GestoreOutput.stampaMessaggio("Nome : " + prenotazioneBean.getUtente().getNome());
            GestoreOutput.stampaMessaggio("Cognome : " + prenotazioneBean.getUtente().getCognome());
            GestoreOutput.stampaMessaggio("Telefono : " +prenotazioneBean.getUtente().getTelefono());

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
        GestoreOutput.stampaMessaggio("Nome : " + ristoranteSelezionato.getNomeRistorante());
        GestoreOutput.stampaMessaggio("Città : " + ristoranteSelezionato.getPosizione().getCitta());
        GestoreOutput.stampaMessaggio("Indirizzo :" + ristoranteSelezionato.getPosizione().getIndirizzoCompleto() + ", " + ristoranteSelezionato.getPosizione().getCap());
        popolaCampoDieta();

    }

    private void popolaCampoDieta() {
        String listaDiete = formattaDiete(ristoranteSelezionato.getTipoDieta());
        GestoreOutput.stampaMessaggio("Dieta : " + listaDiete);
        prenotazioneBean.setNote(listaDiete);
    }
    private String formattaDiete(Set<TipoDieta> diete) {
        if (diete == null || diete.isEmpty()) {
            return "Assenti";
        }

        StringBuilder listaDiete = new StringBuilder();
        for (TipoDieta tipoDieta : diete) {
            listaDiete.append(tipoDieta.getId()).append(", ");
        }

        listaDiete.setLength(listaDiete.length() - 2);

        return listaDiete.toString();
    }




    public void setRiepilogoPrenotazione(PrenotazioneBean prenotazioneBean, RistoranteBean ristoranteSelezionato) {
        this.prenotazioneBean= prenotazioneBean;
        this.ristoranteSelezionato=ristoranteSelezionato;
    }


}
