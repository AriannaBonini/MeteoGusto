package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.enumerazione.*;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
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

    public static Set<TipoCucina> leggiCucineScelteDaInput(boolean sceltaMultipla) {
        Set<TipoCucina> tipologieCucineScelte = new HashSet<>();
        TipoCucina[] tipi = TipoCucina.values();

        if(sceltaMultipla) {
            GestoreOutput.mostraGraficaMenu("Inserisci i filtri sulle tipologie di cucina ", CodiceAnsi.PROSSIMO_FILTRO);
            if(opzioneScelta(1,2)==2) {
                return tipologieCucineScelte;
            }
        }else{
            GestoreOutput.stampaMessaggio("Quale cucina propone il tuo ristorante? ");
        }

        if (!sceltaMultipla) {

            GestoreOutput.mostraTipologieCucina(tipi);
            GestoreOutput.stampaMessaggio("Inserisci il numero della cucina: ");
            tipologieCucineScelte.add(tipi[opzioneScelta(1, tipi.length) - 1]);
            return tipologieCucineScelte;
        }


        boolean termina = false;
        while (!termina) {
            GestoreOutput.mostraTipologieCucina(tipi);
            GestoreOutput.stampaMessaggio("Inserisci il numero della cucina: ");

            TipoCucina scelta = tipi[opzioneScelta(1, tipi.length) - 1];
            if (!tipologieCucineScelte.add(scelta)) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE, "Questa cucina è già stata aggiunta");
            }

            GestoreOutput.mostraGraficaMenu("Vuoi inserire un'altra cucina?", CodiceAnsi.PROSSIMO_FILTRO);
            if (opzioneScelta(1, 2) == 2) {
                termina = true;
            }
        }

        return tipologieCucineScelte;
    }


    public static Set<TipoDieta> leggiDieteScelteDaInput(boolean registrazione) {
        Set<TipoDieta> tipologieDieteScelte =new HashSet<>();

        if(!registrazione) {
            GestoreOutput.mostraGraficaMenu("Inserire i filtri sulle tipologie di dieta", CodiceAnsi.PROSSIMO_FILTRO);
        }else {
            GestoreOutput.mostraGraficaMenu("Inserisci le diete offerte dal tuo ristorante", CodiceAnsi.PROSSIMO_MODULO);
        }

        if (opzioneScelta(1, 2) == 2) {
            return tipologieDieteScelte;
        }


        boolean termina=false;
        TipoDieta[] tipi = TipoDieta.values();

        while(!termina) {

            GestoreOutput.mostraTipologieDieta(tipi);

            GestoreOutput.stampaMessaggio("Inserisci il numero della dieta : ");
            if(!tipologieDieteScelte.add(tipi[opzioneScelta(1, tipi.length)-1])) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,"Questa dieta è stata già aggiunta");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altre diete" , CodiceAnsi.PROSSIMO_FILTRO);
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

    public static Set<GiorniSettimana> giorniChiusuraScelteDaInput() {
        Set<GiorniSettimana> giorniChiusura =new HashSet<>();

        GestoreOutput.mostraGraficaMenu("Scegliere i giorni di chiusura",CodiceAnsi.PROSSIMO_MODULO);
        if(opzioneScelta(1,2)==2){
            return giorniChiusura;
        }

        boolean termina=false;
        GiorniSettimana[] giorni= GiorniSettimana.values();

        while(!termina) {

            GestoreOutput.mostraGiorniSettimana(giorni);

            GestoreOutput.stampaMessaggio("Inserisci il numero del giorno : ");
            if(!giorniChiusura.add(giorni[opzioneScelta(1, giorni.length)-1])) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,"Questa giorno è stata già aggiunto");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altri giorni di chiusura" , CodiceAnsi.PROSSIMO_MODULO);
            if(opzioneScelta(1,2)==2) {
                termina=true;
            }

        }

        return giorniChiusura;
    }

    public static Set<TipoAmbiente> leggiAmbientiSceltiDaInput() {
        Set<TipoAmbiente> listaAmbienti= new HashSet<>();

        GestoreOutput.stampaMessaggio("Quali ambienti offre il tuo ristorante");

        boolean termina=false;
        TipoAmbiente[] ambienti= TipoAmbiente.values();

        while(!termina) {

            GestoreOutput.mostraAmbienti(ambienti);

            GestoreOutput.stampaMessaggio("Inserisci il numero dell'ambiente : ");
            if(!listaAmbienti.add(ambienti[opzioneScelta(1, ambienti.length)-1])) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,"Questo ambiente è stata già aggiunto");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altri ambienti del ristorante" , CodiceAnsi.PROSSIMO_MODULO);
            if(opzioneScelta(1,2)==2) {
                termina=true;
            }

        }

        return listaAmbienti;
    }

    public static Set<Extra> leggiExtraSceltiDaInput() {
        Set<Extra> listaExtra = new HashSet<>();

        GestoreOutput.stampaMessaggio("Quali extra offre il tuo ristorante per il suo ambiente coperto ?");

        boolean termina=false;
        Extra[] extra = Extra.values();

        while(!termina) {

            GestoreOutput.mostraExtra(extra);

            GestoreOutput.stampaMessaggio("Inserisci il numero dell'extra : ");
            if(!listaExtra.add(extra[opzioneScelta(1, extra.length)-1])) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,"Questo extra è stata già aggiunto");
            }

            GestoreOutput.mostraGraficaMenu("Inserire altri extra del ristorante" , CodiceAnsi.PROSSIMO_MODULO);
            if(opzioneScelta(1,2)==2) {
                termina=true;
            }

        }

        return listaExtra;
    }



}
