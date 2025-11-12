package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLPrenotazioneDAO {
    protected QuerySQLPrenotazioneDAO(){/* Costruttore vuoto */}
    protected static final String AMBIENTE="ambiente_id";
    protected static final String DATA = "data";
    protected static final String ORA = "ora";
    protected static final String NUMERO_PERSONE= "numero_persone";
    protected static final String UTENTE = "utente_id";
    protected static final String FASCIA_ORARIA = "fascia_oraria";
    protected static final String NOTIFICA_UTENTE = "notificaUtente";
    protected static final String NOTIFICA_RISTORATORE = "notificaRistoratore";
    protected static final String NOTE_PRENOTAZIONE = "note";
    protected static final String TABELLA_PRENOTAZIONE = "prenotazione";


    /* COSTANTI PER IL CODICE SQL */
    protected static final String FROM="FROM ";
    protected static final String IN=" IN (";

    protected static final String POSTI_DISPONIBILI = "SELECT COALESCE(SUM(" + NUMERO_PERSONE + "), 0) AS totale_persone " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + DATA + " = ? " +
                    "AND " + FASCIA_ORARIA + " = ? " +
                    "AND " + AMBIENTE + " = ?";

    protected static final String INSERISCI_PRENOTAZIONE =
            "INSERT INTO " + TABELLA_PRENOTAZIONE + " (" +
                    AMBIENTE + ", " +
                    DATA + ", " +
                    ORA + ", " +
                    NOTE_PRENOTAZIONE + ", " +
                    NUMERO_PERSONE + ", " +
                    UTENTE + ", " +
                    FASCIA_ORARIA + ") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";


    protected static final String PRENOTAZIONE_ESISTENTE =
            "SELECT COUNT(*) AS numero_prenotazioni " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + UTENTE + " = ? " +
                    "AND " + DATA + " = ? " +
                    "AND " + FASCIA_ORARIA + " = ?";

    protected static final String CONTA_NOTIFICHE_UTENTE =
            "SELECT COUNT(*) AS notifiche_attive " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + UTENTE + " = ? " +
                    "AND " + NOTIFICA_UTENTE + " = TRUE";

    protected static final String SEGNA_NOTIFICHE_UTENTE_COME_LETTE =
            "UPDATE " + TABELLA_PRENOTAZIONE + " " +
                    "SET " + NOTIFICA_UTENTE + " = FALSE " +
                    "WHERE " + UTENTE + " = ? " +
                    "AND " + NOTIFICA_UTENTE + " = TRUE";


    protected static final String PRENOTAZIONI_UTENTE =
            "SELECT " +
                    DATA + ", " +
                    ORA + ", " +
                    AMBIENTE + ", " +
                    NOTE_PRENOTAZIONE + ", " +
                    NUMERO_PERSONE + " " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + UTENTE + " = ?";


    protected static final String PRENOTAZIONI_RISTORATORE =
            "SELECT " +
                    DATA + ", " +
                    ORA + ", " +
                    AMBIENTE + ", " +
                    UTENTE + ", " +
                    NOTE_PRENOTAZIONE + ", " +
                    NUMERO_PERSONE + " " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + AMBIENTE + IN;

    protected static final String CONTA_NOTIFICHE_RISTORATORE_PREFIX =
            "SELECT COUNT(*) AS notifiche_attive " +
                    FROM + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + NOTIFICA_RISTORATORE + " = TRUE " +
                    "AND " + AMBIENTE + IN;

    protected static final String SEGNA_NOTIFICHE_RISTORATORE_COME_LETTE =
            "UPDATE " + TABELLA_PRENOTAZIONE + " " +
                    "SET " + NOTIFICA_RISTORATORE + " = FALSE " +
                    "WHERE " + NOTIFICA_RISTORATORE + " = TRUE " +
                    "AND " + AMBIENTE + IN;



}
