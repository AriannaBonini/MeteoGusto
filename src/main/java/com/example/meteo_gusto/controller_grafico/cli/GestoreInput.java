package com.example.meteo_gusto.controller_grafico.cli;


import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import java.util.Scanner;

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
}
