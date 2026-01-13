package com.example.meteo_gusto.dao.csv;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.example.meteo_gusto.dao.csv.SupportoCSV.generaNuovoId;

public class PrenotazioneDAOCsv implements PrenotazioneDAO {

    private static final String PRENOTAZIONE_CSV = "csv/PrenotazioneCsv.csv";

    @Override
    public Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO {
        Prenotazione prenotazioneDaRitornare = new Prenotazione();
        int totalePersone = 0;

        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);
                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

                String dataColonna = colonne[2];
                String fasciaColonna = colonne[6];
                String ambienteColonna = colonne[1];
                String numeroPersoneColonna = colonne[4];

                if (dataColonna.equals(prenotazione.dataPrenotazione().toString()) &&
                        fasciaColonna.equals(prenotazione.getFasciaOraria().getId()) &&
                        Integer.parseInt(ambienteColonna) == prenotazione.getAmbiente().getIdAmbiente()) {

                    totalePersone += Integer.parseInt(numeroPersoneColonna);
                }
            }
        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante il calcolo dei posti occupati nel CSV", e);
        }

        prenotazioneDaRitornare.setNumeroPersone(totalePersone);
        return prenotazioneDaRitornare;
    }


    @Override
    public boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        boolean inserita;

        try {
            int nuovoId = generaNuovoId(PRENOTAZIONE_CSV);

            String sb = nuovoId + ";" +
                    prenotazione.getAmbiente().getIdAmbiente() + ";" +
                    prenotazione.dataPrenotazione() + ";" +
                    prenotazione.oraPrenotazione() + ";" +
                    prenotazione.numeroPersone() + ";" +
                    prenotazione.prenotante() + ";" +
                    prenotazione.getFasciaOraria().getId() + ";" +
                    "TRUE" + ";" +
                    "TRUE" + ";" +
                    (prenotazione.getNote() != null ? prenotazione.getNote() : "");

            inserita = SupportoCSV.scriviRiga(PRENOTAZIONE_CSV, sb);

        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante l'inserimento della prenotazione nel CSV", e);
        }

        return inserita;
    }

    @Override
    public boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);

                if (i != 0 && colonne[5].equals(prenotazione.prenotante())
                        && colonne[2].equals(prenotazione.dataPrenotazione().toString())
                        && colonne[6].equals(prenotazione.getFasciaOraria().getId())) {
                    return true; 
                }
            }
        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore durante il controllo esistenza prenotazione nel CSV", e);
        }

        return false;
    }



    @Override
    public Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO {
        Prenotazione prenotazione = new Prenotazione();
        int contatore = 0;

        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);

                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

                if (colonne.length > 7 &&
                        colonne[5].equalsIgnoreCase(utente.getEmail()) &&
                        "TRUE".equalsIgnoreCase(colonne[7])) {
                    contatore++;
                }
            }
        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche utente", e);
        }

        prenotazione.setNumeroNotifiche(contatore);
        return prenotazione;
    }

    @Override
    public List<Prenotazione> selezionaPrenotazioniUtente(Persona utente) throws EccezioneDAO {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);
                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

                String emailUtente = colonne[5];
                if (emailUtente.equals(utente.getEmail())) {
                    Prenotazione prenotazione = getPrenotazione(colonne);
                    prenotazioni.add(prenotazione);
                }
            }
        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni dell'utente", e);
        }

        return prenotazioni;
    }


    private static Prenotazione getPrenotazione(String[] colonne) {
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setData(LocalDate.parse(colonne[2]));
        prenotazione.setOra(LocalTime.parse(colonne[3]));

        Ambiente ambiente = new Ambiente();
        ambiente.setIdAmbiente(Integer.parseInt(colonne[1]));
        prenotazione.aggiungiAmbiente(ambiente);

        prenotazione.setNote(colonne[9]);
        prenotazione.setNumeroPersone(Integer.parseInt(colonne[4]));
        return prenotazione;
    }


    @Override
    public List<Prenotazione> selezionaPrenotazioniRistoratore(Ambiente ambiente) throws EccezioneDAO {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        if (ambiente == null) {
            return prenotazioni;
        }

        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);
                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

                int idAmbiente = Integer.parseInt(colonne[1]);
                if (idAmbiente != -1 && ambiente.getIdAmbiente().equals(idAmbiente)) {
                    Prenotazione prenotazione = getPrenotazione(colonne, idAmbiente);
                    prenotazioni.add(prenotazione);
                }
            }
        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni per più ambienti", e);
        }

        return prenotazioni;
    }

    private static Prenotazione getPrenotazione(String[] colonne, int idAmbiente) {
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setData(LocalDate.parse(colonne[2]));
        prenotazione.setOra(LocalTime.parse(colonne[3]));
        prenotazione.setNote(colonne[9]);
        prenotazione.setNumeroPersone(Integer.parseInt(colonne[4]));

        Ambiente ambiente = new Ambiente();
        ambiente.setIdAmbiente(idAmbiente);
        prenotazione.aggiungiAmbiente(ambiente);

        Persona utente = new Persona(colonne[5]);
        prenotazione.setPrenotante(utente.getEmail());
        return prenotazione;
    }


    @Override
    public Prenotazione contaNotificheAttiveRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        Prenotazione prenotazione = new Prenotazione();

        if (ambienti == null || ambienti.isEmpty()) {
            prenotazione.setNumeroNotifiche(0);
            return prenotazione;
        }

        try {
            List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);
            Set<Integer> idAmbienti = ambienti.stream()
                    .map(Ambiente::getIdAmbiente)
                    .collect(Collectors.toSet());

            int conteggio = getConteggio(righe, idAmbienti);

            prenotazione.setNumeroNotifiche(conteggio);
        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche del ristoratore", e);
        }

        return prenotazione;
    }

    private static int getConteggio(List<String[]> righe, Set<Integer> idAmbienti) {
        int conteggio = 0;
        for (int i = 0; i < righe.size(); i++) {
            String[] colonne = righe.get(i);

            if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

            if (colonne.length > 8) {
                int idAmbiente = Integer.parseInt(colonne[1]);
                String notificaRistoratore = colonne[8];

                if (idAmbienti.contains(idAmbiente) && "TRUE".equalsIgnoreCase(notificaRistoratore)) {
                    conteggio++;
                }
            }
        }
        return conteggio;
    }


    @Override
    public void resettaNotificheUtente(Persona utente) throws EccezioneDAO {
        List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);

        try {
            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);


                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;


                if (colonne.length > 7 &&
                        colonne[5].equalsIgnoreCase(utente.getEmail()) &&
                        "TRUE".equalsIgnoreCase(colonne[7])) {
                    colonne[7] = "FALSE";
                }
            }

            SupportoCSV.riscriviCSV(PRENOTAZIONE_CSV, righe);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la reimpostazione delle notifiche dell'utente", e);
        }
    }



    @Override
    public void resettaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        if (ambienti == null || ambienti.isEmpty()) {
            return;
        }

        List<String[]> righe = SupportoCSV.leggiCSV(PRENOTAZIONE_CSV);
        Set<Integer> idAmbienti = ambienti.stream()
                .map(Ambiente::getIdAmbiente)
                .collect(Collectors.toSet());

        try {
            for (int i = 0; i < righe.size(); i++) {
                String[] colonne = righe.get(i);


                if (i == 0 && colonne[0].equalsIgnoreCase("c1")) continue;

                if (colonne.length > 8) {
                    aggiornaNotificaRistoratore(colonne, idAmbienti);
                }
            }

            SupportoCSV.riscriviCSV(PRENOTAZIONE_CSV, righe);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la reimpostazione delle notifiche del ristoratore", e);
        }
    }

    /**
     * Metodo di supporto per la modifica della notifica del ristoratore
     */
    private void aggiornaNotificaRistoratore(String[] colonne, Set<Integer> idAmbienti) throws EccezioneDAO {
        try {
            int idAmbiente = Integer.parseInt(colonne[1]);
            if (idAmbienti.contains(idAmbiente)) {
                colonne[8] = "FALSE";
            }
        } catch (NumberFormatException e) {
            throw new EccezioneDAO("ID ambiente non valido nella riga: " + String.join(";", colonne), e);
        }
    }


}
