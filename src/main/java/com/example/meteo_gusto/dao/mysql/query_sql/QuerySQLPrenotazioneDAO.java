package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLPrenotazioneDAO {
    protected QuerySQLPrenotazioneDAO(){/* Costruttore vuoto */}
    protected static final String AMBIENTE="ambiente_id";
    protected static final String DATA = "data";
    protected static final String ORA = "ora";
    protected static final String NUMERO_PERSONE= "numero_persone";
    protected static final String UTENTE = "utente_id";
    protected static final String FASCIA_ORARIA = "fascia_oraria";
    protected static final String TABELLA_PRENOTAZIONE = "prenotazione";

    protected static final String POSTI_DISPONIBILI = "SELECT COALESCE(SUM(" + NUMERO_PERSONE + "), 0) AS totale_persone " +
                    "FROM " + TABELLA_PRENOTAZIONE + " " +
                    "WHERE " + DATA + " = ? " +
                    "AND " + FASCIA_ORARIA + " = ? " +
                    "AND " + AMBIENTE + " = ?";









}
