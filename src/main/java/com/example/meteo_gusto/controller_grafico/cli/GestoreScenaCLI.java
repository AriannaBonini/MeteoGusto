package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.controller_grafico.cli.ristoratore.HomeRistoratoreCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.HomeUtenteCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.ListaPrenotazioniUtenteCliCG;
import com.example.meteo_gusto.sessione.Sessione;

public class GestoreScenaCLI {

    private GestoreScenaCLI(){ /* COSTRUTTORE PRIVATO */ }

    public static void logout() {
        Sessione.getInstance().logout();
        new LoginCliCG().start();
    }

    public static void vaiAllaListaPrenotazioniUtente() {new ListaPrenotazioniUtenteCliCG().start();}
    public static void vaiAPrenotaRistorante() {}
    public static void viaAllaHomeUtente() {new HomeUtenteCliCG().start();}
    public static void vaiAllaHomeRistoratore(){new HomeRistoratoreCliCG().start();}
    public static void vaiAllaSceltaRegistrazione(){new SceltaRegistrazioneCliCG().start();}


}
