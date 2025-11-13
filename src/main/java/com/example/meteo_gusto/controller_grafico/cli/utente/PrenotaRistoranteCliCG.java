package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.MeteoBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreInput;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import static java.time.LocalDate.parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class PrenotaRistoranteCliCG implements InterfacciaCLI {


    private FiltriBean filtriBean= new FiltriBean();
    private MeteoBean meteoBean;
    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteCliCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private List<RistoranteBean> listaRistorantiPrenotabili= new ArrayList<>();
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");


    @Override
    public void start() {
        GestoreOutput.separatoreArancione();
        popolaListaRistoranti();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> inserisciFiltri();
                    case 2 -> modificaCampiFormIniziale();
                    case 3 -> scopriDiPiu();
                    case 4 -> GestoreScenaCLI.vaiAllaListaPrenotazioniUtente();
                    case 5 -> GestoreScenaCLI.viaAllaHomeUtente();
                    case 6 -> esci=true;
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
        GestoreOutput.stampaTitolo("PRENOTA RISTORANTE ");

        popolaNotifiche();

        if(meteoBean!=null && filtriBean.getMeteo()) {
            mostraPrevisioniMetereologiche();
        }

        mostraInfoScelte();
        mostraListaRistoranti();


        GestoreOutput.mostraGraficaMenu("Inserire dei filtri" ,"Modificare i campi del form iniziale" ,"Scegliere uno dei ristoranti mostrati" ,"Visualizza la tua lista delle prenotazioni", "Vai alla Home","Logout");
        return opzioneScelta(1,6);
    }


    private void mostraInfoScelte() {
        GestoreOutput.stampaMessaggio("Data : " + filtriBean.getData().toString() + "   Ora : " + filtriBean.getOra().toString() +
                "   Città : " + filtriBean.getCitta() + "   Numero Persone : " + filtriBean.getNumeroPersone().toString());
    }

    private void popolaNotifiche() {
        try {
            GestoreOutput.visualizzazioneNotifiche(prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle notifiche : ", e);
        }
    }


    private void popolaListaRistoranti() {
        try {
            listaRistorantiPrenotabili= prenotaRistoranteController.cercaRistorantiDisponibili(filtriBean);

            System.out.println(filtriBean.getFasciaPrezzoRistorante());
            System.out.println(filtriBean.getTipoDieta());
            System.out.println(filtriBean.getTipoCucina());

            listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean, meteoBean);

            System.out.println(filtriBean.getFasciaPrezzoRistorante());
            System.out.println(filtriBean.getTipoDieta());
            System.out.println(filtriBean.getTipoCucina());
        } catch (EccezioneDAO | ValidazioneException e) {
            logger.error("Errore durante la ricerca dei ristoranti filtrati: ", e);
            GestoreScena.mostraAlertSenzaConferma("Attenzione",e.getMessage());
        }
    }


    private void mostraListaRistoranti() {
        if (listaRistorantiPrenotabili == null || listaRistorantiPrenotabili.isEmpty()) {
            GestoreOutput.mostraAvvertenza("Nessun ristorante disponibile", "Prova a modificare i criteri di ricerca o i filtri applicati.");
            return;
        }

        int indiceDaMostrare;

        for(int indice=0 ; indice<listaRistorantiPrenotabili.size() ; ++indice) {
            indiceDaMostrare=indice+1;
            RistoranteBean ristoranteBean=listaRistorantiPrenotabili.get(indice);

            GestoreOutput.rettangolo("Ristorante numero : " + indiceDaMostrare, ristoranteBean.getNomeRistorante() + GestoreOutput.mostraFasciaPrezzoRistorante(ristoranteBean),
                    ristoranteBean.getPosizione().getCitta() + "  "  + CodiceAnsi.PUNTINO + "  "+ ristoranteBean.getCucina().getId(),
                    ristoranteBean.getMediaStelle()+"/5" + CodiceAnsi.STELLINA_GIALLA);
        }
    }

    private void inserisciFiltri(){
        try {
            GestoreOutput.stampaTitolo("FILTRI");
            filtriBean.setTipoCucina(GestoreInput.leggiCucineScelteDaInput());
            filtriBean.setTipoDieta(GestoreInput.leggiDieteScelteDaInput());
            filtriBean.setFasciaPrezzoRistorante(GestoreInput.leggiFasciaPrezzoSceltaDaInput());


            GestoreOutput.stampaTitolo("Vuoi applicare il filtro del meteo ?");
            GestoreOutput.mostraGraficaMenu("Si", "No");
            if (opzioneScelta(1, 2) == 1) {
                filtriBean.setMeteo(true);
                if (meteoBean == null) {
                    meteoBean = prenotaRistoranteController.previsioneMetereologiche(filtriBean);
                }
                listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean,meteoBean);

            }else {
                filtriBean.setMeteo(false);
                listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean,null);
            }


        }catch (ValidazioneException e) {
            logger.error("Errore : ", e);
        } catch (IOException e) {
            logger.error("Errore di comunicazione con il servizio meteo: {}", e.getMessage());
            GestoreOutput.mostraAvvertenza("Errore :","comunicazione non stabilita con il servizio meteo");
        } catch (EccezioneDAO e) {
            logger.error("Errore di accesso ai dati: {}", e.getMessage());
        }


    }

    private void modificaCampiFormIniziale() {
        GestoreOutput.stampaTitolo("Quale campo vuoi modificare ?");
        GestoreOutput.mostraGraficaMenu("Data", "Ora", "Città", "Numero Persone");
        try {
            switch (opzioneScelta(1, 4)) {
                case 1 -> filtriBean.setData(LocalDate.parse(GestoreInput.leggiStringaDaInput("Inserisci data (G/M/AAAA) :"),formatoData));
                case 2 -> filtriBean.setOra(LocalTime.from(parse(GestoreInput.leggiStringaDaInput("Inserisci un orario (HH:mm) :"),formatoOrario)));
                case 3 -> filtriBean.setCitta(GestoreInput.leggiStringaDaInput("Inserisci una città :"));
                case 4 -> filtriBean.setNumeroPersone(Integer.parseInt(GestoreInput.leggiStringaDaInput("Inserisci il numero di persone :")));
                default -> { GestoreOutput.stampaMessaggio("La tua scelta non è tra quelle indicate");
                            mostraMenu();}
            }

            System.out.println(filtriBean.getFasciaPrezzoRistorante());
            System.out.println(filtriBean.getTipoDieta());
            System.out.println(filtriBean.getTipoCucina());

            popolaListaRistoranti();
            mostraListaRistoranti();

        }catch (ValidazioneException e) {
            GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
        }
    }

    private void mostraPrevisioniMetereologiche() {

        String meteo;
        String iconaTempo;
        String iconaTemperatura;

        LocalTime oraCorrente = LocalTime.now();
        boolean giorno = oraCorrente.isBefore(LocalTime.of(18, 0));

        switch (meteoBean.getTempo()) {
            case "Sole" -> iconaTempo= (giorno ? CodiceAnsi.SOLE : CodiceAnsi.LUNA);
            case "Pioggia" -> iconaTempo = (CodiceAnsi.PIOGGIA);
            default -> iconaTempo = (giorno ? CodiceAnsi.GIORNATA_NUVOLOSA : CodiceAnsi.LUNA_CON_NUVOLE);
        }

        if (meteoBean.getTemperatura() >= 5 && meteoBean.getTemperatura() <= 14) {
            iconaTemperatura=CodiceAnsi.TEMPERATURA_FREDDA;

        } else if (meteoBean.getTemperatura() >= 26 && meteoBean.getTemperatura() <= 35) {
            iconaTemperatura= CodiceAnsi.TEMPERATURA_CALDA;
        } else {
            iconaTemperatura=CodiceAnsi.TEMPERATURA_NORMALE;
        }

        meteo = (iconaTempo + meteoBean.getTempo() + CodiceAnsi.PUNTINO + meteoBean.getTemperatura() + iconaTemperatura);
        GestoreOutput.mostraAvvertenza("PREVISIONI METEREOLOGICHE",meteo);

    }



    private void scopriDiPiu(){
        GestoreOutput.stampaMessaggio("Questa è la lista dei ristoranti filtrati in base alle tue preferenze");
        mostraListaRistoranti();
        GestoreOutput.stampaMessaggio("Inserisci il numero del ristorante per poter visualizzare il suo profilo e procedere con la prenotazione");
        RistoranteBean ristoranteSelezionato=listaRistorantiPrenotabili.get(opzioneScelta(1,listaRistorantiPrenotabili.size())-1);


        GestoreScenaCLI.cambiaVistaConParametri(
                ProfiloRistoranteCliCG.class,
                vista -> {vista.setFiltriCorrenti(filtriBean, meteoBean);
                        vista.setRistoranteSelezionato(ristoranteSelezionato);
                });
    }


    public void setFiltriBean(FiltriBean filtriBean) {
        this.filtriBean=filtriBean;
    }
}
