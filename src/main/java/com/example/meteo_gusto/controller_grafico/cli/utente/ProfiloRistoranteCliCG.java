package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller.RecensioneController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class ProfiloRistoranteCliCG implements InterfacciaCLI {


    private RistoranteBean ristoranteSelezionato;
    private FiltriBean filtriSelezionati;
    private MeteoBean meteoBean;
    private static final Logger logger = LoggerFactory.getLogger(ProfiloRistoranteCliCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();



    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> GestoreScenaCLI.vaiAlMenuDelRistorante();
                    case 2 -> vaiAlRiepilogoDellaPrenotazione();
                    case 3 -> tornaIndietro();
                    case 4 -> GestoreScenaCLI.vaiAllaListaPrenotazioniUtente();
                    case 5 -> GestoreScenaCLI.vaiAllaHomeUtente();
                    case 6 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException | EccezioneDAO e) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,e.getMessage());
            }
        }
        GestoreScenaCLI.login();
    }

    @Override
    public int mostraMenu() throws ValidazioneException, EccezioneDAO {
        GestoreOutput.stampaTitolo("PROFILO DEL RISTORANTE  ");
        popolaNotifiche();
        popolaProfiloRistorante();

        GestoreOutput.mostraGraficaMenu("Consulta il menù del ristorante","Prenota", "Torna indietro","Visualizza la tua lista delle prenotazioni", "Vai alla Home","Logout");
        return opzioneScelta(1,6);
    }

    public void setFiltriCorrenti(FiltriBean filtriBean, MeteoBean meteoBean) {
        this.filtriSelezionati=filtriBean;
        this.meteoBean=meteoBean;
    }

    public void setRistoranteSelezionato(RistoranteBean ristoranteSelezionato) {
        this.ristoranteSelezionato=ristoranteSelezionato;
    }

    private void popolaNotifiche() {
        try {
            GestoreOutput.visualizzazioneNotifiche(prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle notifiche : ", e);
        }
    }

    private void popolaProfiloRistorante() throws ValidazioneException, EccezioneDAO {
        ristoranteSelezionato= prenotaRistoranteController.dettagliRistorante(ristoranteSelezionato);

        GestoreOutput.stampaTitolo(ristoranteSelezionato.getNome() + CodiceAnsi.ANSI_RESET + GestoreOutput.stampaStelleRistorante(ristoranteSelezionato));
        GestoreOutput.stampaMessaggio(CodiceAnsi.PUNTINO + ristoranteSelezionato.getIndirizzoCompleto() + CodiceAnsi.PUNTINO + ristoranteSelezionato.getCitta() + CodiceAnsi.PUNTINO + ristoranteSelezionato.getCap());
        GestoreOutput.stampaMessaggio("Tel : "+ ristoranteSelezionato.getTelefono() + GestoreOutput.mostraFasciaPrezzoRistorante(ristoranteSelezionato));
        GestoreOutput.stampaMessaggio(orari(ristoranteSelezionato.getOrariApertura()));
        GestoreOutput.stampaMessaggio(ristoranteSelezionato.getCucina() + CodiceAnsi.PUNTINO + stampaDieteRistorante());

        GestoreOutput.stampaMessaggio("Come vuoi procedere ? ");
        GestoreOutput.mostraGraficaMenu("Lasciare una recensione","Menù principale");
        if(opzioneScelta(1,2)==2) {
            return;
        }

        recensisciRistorante();


    }

    private String stampaDieteRistorante() {
        List<String> diete = ristoranteSelezionato.getDiete();
        if (diete == null || diete.isEmpty()) {
            return "Nessuna dieta disponibile";
        }
        return String.join(" • ", diete);
    }


    private void recensisciRistorante() {
        GestoreOutput.stampaTitolo("RECENISCI RISTORANTE " + ristoranteSelezionato.getNome());
        RecensioneController recensioneController= new RecensioneController();
        RecensioneBean recensioneBean= new RecensioneBean();

        try {
            GestoreOutput.stampaMessaggio("Puoi assegnare al ristorante da 1 a 5 stelle");
            GestoreOutput.stampaMessaggio("Inserisci un numero da 1 a 5");
            int numeroStelle=opzioneScelta(1,5);

            recensioneBean.setStelle(BigDecimal.valueOf(numeroStelle));
            recensioneBean.setRistorante(ristoranteSelezionato.getPartitaIVA());
            recensioneController.recensisciRistorante(recensioneBean);
            ristoranteSelezionato.setMediaStelle(recensioneController.nuovaMediaRecensione(ristoranteSelezionato).getMediaStelle());


            GestoreOutput.stampaTitolo("GRAZIE PER LA TUA RECENSIONE");
        } catch (EccezioneDAO e) {
            GestoreOutput.mostraAvvertenza("Errore", "Inserimento recensione non riuscito");
            logger.error("Errore durante la recensione del ristorante ", e);
        }


    }

    private String orari(GiorniEOrariBean giorniEOrariBean){

        if(giorniEOrariBean.getInizioPranzo()==null && giorniEOrariBean.getFinePranzo()==null) {
            return (" • Non effettua pranzo " + " • " + giorniEOrariBean.getInizioCena() + " - " + giorniEOrariBean.getFineCena());
        }else if(giorniEOrariBean.getInizioCena()==null && giorniEOrariBean.getFineCena()==null) {
            return (" • " + giorniEOrariBean.getInizioPranzo() + " - " + giorniEOrariBean.getFinePranzo() + " • Non effettua cena");

        }else {
            return(" • " + giorniEOrariBean.getInizioPranzo() + " - " + giorniEOrariBean.getFinePranzo() + " • " + giorniEOrariBean.getInizioCena() + " - " + giorniEOrariBean.getFineCena());
        }
    }

    private void tornaIndietro(){
        GestoreScenaCLI.cambiaVistaConParametri(PrenotaRistoranteCliCG.class,
                vista -> vista.impostaFiltriSelezionati(filtriSelezionati,meteoBean));
    }

    private void vaiAlRiepilogoDellaPrenotazione() {
        try {
            PrenotazioneBean prenotazione = popolaPrenotazione();

            GestoreScenaCLI.cambiaVistaConParametri(RiepilogoPrenotazioneCliCG.class,
                    vista -> vista.setRiepilogoPrenotazione(prenotazione));
        }catch (ValidazioneException e) {
            logger.error("Attenzione", e);
        }
    }


    private PrenotazioneBean popolaPrenotazione() throws ValidazioneException {
        RistoranteBean ristoranteBean= new RistoranteBean();
        ristoranteBean.setNome(ristoranteSelezionato.getNome());
        ristoranteBean.setPartitaIVA(ristoranteSelezionato.getPartitaIVA());
        ristoranteBean.setCap(ristoranteSelezionato.getCap());
        ristoranteBean.setIndirizzoCompleto(ristoranteSelezionato.getIndirizzoCompleto());
        ristoranteBean.setCitta(ristoranteSelezionato.getCitta());

        PrenotazioneBean prenotazioneBean= new PrenotazioneBean();
        prenotazioneBean.setData(filtriSelezionati.getData());
        prenotazioneBean.setOra(filtriSelezionati.getOra());
        prenotazioneBean.setNumeroPersone(filtriSelezionati.getNumeroPersone());
        prenotazioneBean.setNote(String.join(", ", ristoranteSelezionato.getDiete()));



        prenotazioneBean.setRistorante(ristoranteBean);

        prenotazioneBean.setAmbiente(aggiungiAmbienti());

        return prenotazioneBean;
    }

    private List<String> aggiungiAmbienti() {
        List<String> ambienti=new ArrayList<>();

        for(AmbienteBean ambienteBean: ristoranteSelezionato.getAmbiente()) {
            ambienti.add(ambienteBean.getTipoAmbiente());
        }
        return ambienti;
    }

}
