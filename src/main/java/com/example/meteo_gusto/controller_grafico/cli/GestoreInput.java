package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;

import java.util.*;

public class GestoreInput {

    private GestoreInput() { /* COSTRUTTORE PRIVATO */ }

    private static final Scanner input = new Scanner(System.in);

    /**
     * Legge un numero intero da riga di comando compreso tra min e max.
     */
    public static int opzioneScelta(int min, int max) {
        int scelta;
        while (true) {
            GestoreOutput.stampaMessaggio("\nInserisci la tua scelta: ");
            String linea = input.nextLine();
            try {
                scelta = Integer.parseInt(linea);
                if (scelta >= min && scelta <= max) break;
                else GestoreOutput.stampaMessaggio("Errore: devi inserire un numero tra " + min + " e " + max);
            } catch (NumberFormatException e) {
                GestoreOutput.stampaMessaggio("Errore: devi inserire un numero intero.");
            }
        }
        return scelta;
    }

    /**
     * Legge una stringa da riga di comando.
     */
    public static String leggiStringaDaInput(String richiesta) {
        GestoreOutput.stampaMessaggio(richiesta);
        return input.nextLine();
    }

    public static Set<TipoCucina> leggiCucineScelteDaInput() {
        Set<TipoCucina> tipologieCucineScelte=new HashSet<>();

        GestoreOutput.mostraGraficaMenu("Inserire i filtri sulle tipologie di cucine","Prossimo filtro");
        if(opzioneScelta(1,2)==2){
            return tipologieCucineScelte;
        }

        boolean termina=false;
        TipoCucina[] tipi = TipoCucina.values();

        while(!termina) {
            GestoreOutput.mostraTipologieCucina(tipi);
            GestoreOutput.stampaMessaggio("Inserisci il numero della cucina : ");
            if(!tipologieCucineScelte.add(tipi[opzioneScelta(1, tipi.length)-1])) {
                GestoreOutput.mostraAvvertenza("Attenzione","Questa cucina è stata già aggiunta");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altre cucine" , "Prossimo filtro");
            if(opzioneScelta(1,2)==2) {
                termina=true;
            }

        }

        return tipologieCucineScelte;
    }

    public static Set<TipoDieta> leggiDieteScelteDaInput() {
        Set<TipoDieta> tipologieDieteScelte =new HashSet<>();

        GestoreOutput.mostraGraficaMenu("Inserire i filtri sulle tipologie di dieta","Prossimo filtro");
        if(opzioneScelta(1,2)==2){
            return tipologieDieteScelte;
        }

        boolean termina=false;
        TipoDieta[] tipi = TipoDieta.values();

        while(!termina) {

            GestoreOutput.mostraTipologieDieta(tipi);

            GestoreOutput.stampaMessaggio("Inserisci il numero della dieta : ");
            if(!tipologieDieteScelte.add(tipi[opzioneScelta(1, tipi.length)-1])) {
                GestoreOutput.mostraAvvertenza("Attenzione","Questa dieta è stata già aggiunta");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altre diete" , "Prossimo filtro");
            if(opzioneScelta(1,2)==2) {
                termina=true;
            }

        }

        return tipologieDieteScelte;
    }

    public static FasciaPrezzoRistorante leggiFasciaPrezzoSceltaDaInput() {

        GestoreOutput.mostraGraficaMenu("Scegliere una fascia di prezzo","Prossimo filtro");
        if(opzioneScelta(1,2)==2){
            return null;
        }

        FasciaPrezzoRistorante[] tipi=FasciaPrezzoRistorante.values();
        GestoreOutput.mostraTipologieFasciaPrezzo(tipi);

        GestoreOutput.stampaMessaggio("Inserisci il numero della fascia prezzo: ");
        return (tipi[opzioneScelta(1, tipi.length)-1]);
    }


}
