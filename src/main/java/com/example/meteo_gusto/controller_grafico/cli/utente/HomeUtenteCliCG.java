package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.HomeUtenteController;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.controller_grafico.cli.LoginCliCG;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import java.util.ArrayList;
import java.util.List;

import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class HomeUtenteCliCG implements InterfacciaCLI {


    private List<RistoranteBean> listaRistoranti= new ArrayList<>();

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> prenotaRistorante();
                    case 2 -> listaPrenotazioni();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Attenzione",e.getMessage());
            }
        }
        tornaAlLogin();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("BENVENUTO SU METEOGUSTO");
        GestoreOutput.stampaMessaggio("Pioggia o sole? Prenota il tuo tavolo perfetto e gusta ogni momento");

        try {
            suggerimenti();
            popolaNotifiche();

        }catch (ValidazioneException e) {
            GestoreOutput.mostraAvvertenza("Attenzione", "Errore durante il caricamento dei migliori ristoranti");
        }

        GestoreOutput.mostraGraficaMenu("Prenota un ristorante", "Visualizza la tua lista delle prenotazioni","Logout");
        return opzioneScelta(1,3);
    }

    private void popolaNotifiche() throws ValidazioneException{
        PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();
        Integer numeroNotifiche=prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche();

        if(numeroNotifiche>0) {
            GestoreOutput.mostraNotifiche(numeroNotifiche);
        }
    }


    private void suggerimenti() throws ValidazioneException{
        HomeUtenteController homeUtenteController= new HomeUtenteController();

        listaRistoranti=homeUtenteController.trovaMiglioriRistoranti();

        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            GestoreOutput.stampaMessaggio("Stiamo aggiornando la nostra selezione: presto nuovi ristoranti da scoprire!");
            return;
        }

        mostraRistoranti();

    }

    private void mostraRistoranti() {
        for(RistoranteBean ristoranteBean : listaRistoranti) {
            GestoreOutput.stampaSchedaRistoranteMinimal(ristoranteBean.getNomeRistorante(), ristoranteBean.getPosizione().getCitta(),ristoranteBean.getCucina().toString(),ristoranteBean.getMediaStelle().toString());
        }
    }


    private void tornaAlLogin() {
        Sessione.getInstance().logout();
        new LoginCliCG().start();
    }

    private void prenotaRistorante() {}
    private void listaPrenotazioni() {}
}
