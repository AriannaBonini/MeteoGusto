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


    public static void rettangolo(String... righe) {
        int larghezza = 0;
        for (String riga : righe) {
            int len = riga.replaceAll("\u001B\\[[;\\d]*m", "").length();
            if (len > larghezza) larghezza = len;
        }

        stampaMessaggio(ANSI_BIANCO_OPACO + ANGOLO_ALTO_SX + BORDO_ORIZZONTALE.repeat(larghezza + 2) + ANGOLO_ALTO_DX + ANSI_RESET);

        for (int i = 0; i < righe.length; i++) {
            String testo = righe[i];
            String colore = (i == 0) ? ANSI_ARANCIONE + ANSI_GRASSETTO : ANSI_BIANCO_OPACO;

            stampaMessaggio(
                    ANSI_BIANCO_OPACO + BORDO_VERTICALE + " " +
                            colore + padCentrato(testo, larghezza) + ANSI_RESET +
                            " " + ANSI_BIANCO_OPACO + BORDO_VERTICALE + ANSI_RESET
            );
        }

        stampaMessaggio(ANSI_BIANCO_OPACO + ANGOLO_BASSO_SX + BORDO_ORIZZONTALE.repeat(larghezza + 2) + ANGOLO_BASSO_DX + ANSI_RESET);
    }

    public static String padCentrato(String testo, int larghezza) {
        if (testo.length() >= larghezza) return testo.substring(0, larghezza);
        int spazi = larghezza - testo.length();
        int spaziSinistra = spazi / 2;
        int spaziDestra = spazi - spaziSinistra;
        return " ".repeat(spaziSinistra) + testo + " ".repeat(spaziDestra);
    }





}


