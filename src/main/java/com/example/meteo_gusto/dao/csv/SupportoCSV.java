package com.example.meteo_gusto.dao.csv;

import com.example.meteo_gusto.eccezione.EccezioneDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupportoCSV {

    private SupportoCSV(){ /* COSTRUTTORE PRIVATO */ }

    /**
     * Legge tutte le righe di un file CSV.
     * @param percorsoFile percorso relativo al file csv.
     * @return Lista di righe, ciascuna rappresentata come array di stringhe
     * @throws EccezioneDAO in caso di problemi di lettura del file o se il file non viene trovato
     */
    static List<String[]> leggiCSV(String percorsoFile) throws EccezioneDAO {
        List<String[]> righe = new ArrayList<>();

        File file = new File(percorsoFile);
        if (!file.exists()) {
            throw new EccezioneDAO("File CSV non trovato: " + percorsoFile);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] colonne = linea.split(";");
                righe.add(colonne);
            }
        } catch (IOException e) {
            throw new EccezioneDAO("Errore durante la lettura del file CSV: " + percorsoFile, e);
        }

        return righe;
    }







    /**
     * Metodo di supporto per generare un nuovo ID unico.
     * Qui potresti leggere tutte le righe del CSV e prendere il massimo + 1.
     */
    static int generaNuovoId(String percorsoFile) throws EccezioneDAO {
        List<String[]> righe = SupportoCSV.leggiCSV(percorsoFile);
        int maxId = 0;
        for (String[] riga : righe) {
            try {
                int id = Integer.parseInt(riga[0]);
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {
                throw new EccezioneDAO("Errore durante la generazione dell'id :",e);
            }
        }
        return maxId + 1;
    }

    /**
    * Metodo di supporto per scrivere una riga in un file CSV.
    * La riga passata viene aggiunta in coda al file.
    * Se il file non esiste, viene creato automaticamente.
    **/
    public static boolean scriviRiga(String percorso, String riga) throws EccezioneDAO {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(percorso, true))) {
            bw.write(riga);
            bw.newLine();
            return true;
        } catch (IOException e) {
            throw new EccezioneDAO("Errore durante la scrittura nel CSV", e);
        }
    }



    /**
     * Metodo di supporto per riscrivere completamente un file CSV.
     * Sovrascrive il contenuto esistente con le righe fornite.
     * Se il file non esiste, viene generata un'eccezione.
     */

    public static void riscriviCSV(String pathFile, List<String[]> righe) throws EccezioneDAO {
        File file = new File(pathFile);
        if (!file.exists()) {
            throw new EccezioneDAO("File CSV non trovato: " + pathFile);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String[] colonne : righe) {
                bw.write(String.join(";", colonne));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new EccezioneDAO("Errore durante la riscrittura del CSV", e);
        }
    }


}
