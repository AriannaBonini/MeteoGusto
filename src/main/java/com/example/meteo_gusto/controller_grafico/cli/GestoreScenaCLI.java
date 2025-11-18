package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.controller_grafico.cli.ristoratore.HomeRistoratoreCliCG;
import com.example.meteo_gusto.controller_grafico.cli.ristoratore.ListaPrenotazioniRistoratoreCliCG;
import com.example.meteo_gusto.controller_grafico.cli.ristoratore.RegistrazioneRistoratoreCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.HomeUtenteCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.ListaPrenotazioniUtenteCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.PrenotaRistoranteFormInizialeCliCG;
import com.example.meteo_gusto.controller_grafico.cli.utente.RegistrazioneUtenteCliCG;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import java.util.function.Consumer;
import static java.lang.System.*;


public class GestoreScenaCLI {

    private GestoreScenaCLI(){ /* COSTRUTTORE PRIVATO */ }

    public static void login() {
        Sessione.getInstance().logout();
        new LoginCliCG().start();
    }

    /**
    * @param vistaClass la classe della schermata CLI da istanziare
     * @param configuratoreVista lambda per impostare la bean o fare setup della schermata
     * @param <V> tipo della schermata CLI
     */
    public static <V> void cambiaVistaConParametri(Class<V> vistaClass, Consumer<V> configuratoreVista) {
        try {
            V vista = vistaClass.getDeclaredConstructor().newInstance();

            configuratoreVista.accept(vista);

            vistaClass.getMethod("start").invoke(vista);

        } catch (Exception e) {
            out.println("Errore durante il cambio vista CLI: " + e.getMessage());
        }
    }


    public static void vaiAllaListaPrenotazioniUtente() {new ListaPrenotazioniUtenteCliCG().start();}
    public static void vaiAPrenotaRistoranteFormIniziale() {new PrenotaRistoranteFormInizialeCliCG().start();}
    public static void viaAllaHomeUtente() {new HomeUtenteCliCG().start();}
    public static void vaiAllaHomeRistoratore(){new HomeRistoratoreCliCG().start();}
    public static void vaiAllaSceltaRegistrazione(){new SceltaRegistrazioneCliCG().start();}
    public static void vaiAlMenuDelRistorante(){GestoreOutput.mostraAvvertenza("Siamo spiacenti","La sezione per la visualizzazione del menù del ristorante non è al momento disponibile");}
    public static void vaiAlMenu(){GestoreOutput.mostraAvvertenza("Siamo spiacenti","La sezione per la creazione nel menù non è al momento disponibile");}
    public static void vaiAllaListaPrenotazioniRistorante() {new ListaPrenotazioniRistoratoreCliCG().start();}
    public static void vaiAllaRegistrazioneUtente(){new RegistrazioneUtenteCliCG().start();}
    public static void vaiAllaRegistrazioneRistoratore(){new RegistrazioneRistoratoreCliCG().start();}

}
