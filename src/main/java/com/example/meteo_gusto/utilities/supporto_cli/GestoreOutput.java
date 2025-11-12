package com.example.meteo_gusto.utilities.supporto_cli;

import static com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi.*;
import static java.lang.System.*;

public class GestoreOutput {

    private GestoreOutput(){/*costruttore privato per evitare istanze*/}

    public static void stampaMessaggio(String messaggio){out.println(messaggio);}
    public static void stampaTitolo(String messaggio){stampaMessaggio(ANSI_ARANCIONE + ANSI_GRASSETTO + messaggio + ANSI_RESET);}

    public static void pulisciPagina(){
        for (int i = 0; i < 100; i++){
            GestoreOutput.stampaMessaggio("\n");
        }
    }

    public static void mostraNotifiche(Integer numeroNotifiche) {
        GestoreOutput.stampaMessaggio(CAMPANELLA_GIALLA + ANSI_GIALLO + "Hai " + numeroNotifiche + " nuove prenotazioni" + ANSI_RESET);
        GestoreOutput.stampaMessaggio(ANSI_BIANCO_OPACO +  " Puoi vederle nella lista delle tue prenotazioni " + ANSI_RESET);

    }


    public static void separatoreArancione() {
        stampaMessaggio("");
        stampaMessaggio(ANSI_ARANCIONE + "------------------------------------------------" + ANSI_RESET);
        stampaMessaggio("");
    }

    public static void separatoreBiancoOpaco() {
        stampaMessaggio("");
        stampaMessaggio(ANSI_BIANCO_OPACO + "------------------------------------------------" + ANSI_RESET);
        stampaMessaggio("");
    }


    public static void mostraAvvertenza(String titolo, String sottotitolo) {
        separatoreBiancoOpaco();
        GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + titolo + ANSI_RESET);
        GestoreOutput.stampaMessaggio(ANSI_BIANCO_OPACO + sottotitolo + ANSI_RESET);
        separatoreBiancoOpaco();
    }

    public static void mostraGraficaMenu(String... opzioni) {
        GestoreOutput.stampaMessaggio("Come desideri procedere ?");

        for (int i = 0; i < opzioni.length; i++) {
            int indice = i + 1;
            GestoreOutput.stampaMessaggio(ANSI_BIANCO_OPACO + indice + ") " + opzioni[i] + ANSI_RESET);
        }
    }


    public static void stampaSchedaRistoranteMinimal(String nome, String citta, String cucina, String mediaStelle) {
        int larghezza = 40;

        stampaMessaggio(ANSI_BIANCO_OPACO + "+" + "-".repeat(larghezza) + "+" + ANSI_RESET);
        stampaMessaggio("| " + ANSI_ARANCIONE + ANSI_GRASSETTO + padCentrato(nome, larghezza)  + ANSI_RESET + " |");
        String secondoTesto = citta + " " + PUNTINO + " " + cucina;
        stampaMessaggio("| " + ANSI_BIANCO_OPACO + padCentrato(secondoTesto, larghezza) + ANSI_RESET + " |");
        stampaMessaggio("| " + ANSI_BIANCO_OPACO + padCentrato(mediaStelle, larghezza) + ANSI_RESET + STELLINA_GIALLA + " |");
        stampaMessaggio(ANSI_BIANCO_OPACO + "+" + "-".repeat(larghezza) + "+" + ANSI_RESET);
    }

    public static String padCentrato(String testo, int larghezza) {
        if (testo.length() >= larghezza) return testo.substring(0, larghezza);
        int spazi = larghezza - testo.length();
        int spaziSinistra = spazi / 2;
        int spaziDestra = spazi - spaziSinistra;
        return " ".repeat(spaziSinistra) + testo + " ".repeat(spaziDestra);
    }




}


