package com.example.meteo_gusto.utilities.supporto_cli;

import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.enumerazione.*;
import java.math.BigDecimal;
import static com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi.*;
import static java.lang.System.*;


public class GestoreOutput {

    private GestoreOutput(){/*costruttore privato per evitare istanze*/}

    public static void stampaMessaggio(String messaggio){out.println(messaggio);}
    public static void stampaTitolo(String messaggio){stampaMessaggio(ANSI_ARANCIONE + ANSI_GRASSETTO + messaggio + ANSI_RESET);}


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

    public static String mostraFasciaPrezzoRistorante(RistoranteBean ristoranteBean) {
        return switch (ristoranteBean.getFasciaPrezzo()) {
            case FasciaPrezzoRistorante.ECONOMICO -> mostraDollariColorati(1);
            case FasciaPrezzoRistorante.MODERATO -> mostraDollariColorati(2);
            case FasciaPrezzoRistorante.COSTOSO -> mostraDollariColorati(3);
            case FasciaPrezzoRistorante.LUSSO -> mostraDollariColorati(4);
        };
    }

    private static String mostraDollariColorati(int numeroRipetizioni) {
        return DOLLARO.repeat(Math.max(0, numeroRipetizioni));
    }



    public static void mostraTipologieCucina(TipoCucina[] tipi) {
        GestoreOutput.stampaTitolo(" CUCINA : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<tipi.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + tipi[indice].getId());
        }

    }

    public static void mostraTipologieDieta(TipoDieta[] tipi) {
        GestoreOutput.stampaTitolo(" DIETA : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<tipi.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + tipi[indice].getId());
        }

    }

    public static void mostraTipologieFasciaPrezzo(FasciaPrezzoRistorante[] tipi) {
        GestoreOutput.stampaTitolo(" FASCIA PREZZO : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<tipi.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + tipi[indice].getId());
        }

    }


    public static void visualizzazioneNotifiche(Integer numeroNotifiche) {
        if (numeroNotifiche > 0) {
            GestoreOutput.mostraNotifiche(numeroNotifiche);
        }
    }

    public static String stampaStelleRistorante(RistoranteBean ristorante) {
        BigDecimal mediaBD = ristorante.getMediaStelle();
        double mediaStelle = mediaBD.doubleValue();

        int stellePiene;
        double decimale = mediaStelle - Math.floor(mediaStelle);

        if (decimale >= 0.5) {
            stellePiene = (int) Math.ceil(mediaStelle);
        } else {
            stellePiene = (int) Math.floor(mediaStelle);
        }

        int stelleTotali = 5;
        StringBuilder stelle = new StringBuilder();

        for (int i = 0; i < stelleTotali; i++) {
            if (i < stellePiene) {
                stelle.append(STELLINA_GIALLA);
            } else {
                stelle.append(STELLINA_VUOTA);
            }
        }

        return stelle.toString();
    }

    public static void mostraGiorniSettimana(GiorniSettimana[] giorniSettimana) {
        GestoreOutput.stampaTitolo(" GIORNI DELLA SETTIMANA : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<giorniSettimana.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + giorniSettimana[indice].getId());
        }

    }

    public static void mostraAmbienti(TipoAmbiente[] ambienti) {
        GestoreOutput.stampaTitolo(" AMBIENTI : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<ambienti.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + ambienti[indice].getId());
        }

    }

    public static void mostraExtra(Extra[] extra) {
        GestoreOutput.stampaTitolo(" EXTRA : ");
        int indice;
        int indiceDaMostrare;
        for (indice=0; indice<extra.length ; indice++) {
            indiceDaMostrare=indice+1;
            GestoreOutput.stampaMessaggio(ANSI_ARANCIONE + indiceDaMostrare + " ) " + ANSI_RESET + extra[indice].getId());
        }

    }



}


