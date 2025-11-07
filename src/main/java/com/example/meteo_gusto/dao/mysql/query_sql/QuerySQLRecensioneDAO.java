package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLRecensioneDAO {

    protected QuerySQLRecensioneDAO() {/* Costruttore vuoto */}
    protected static final String UTENTE = "utente";
    protected static final String RISTORANTE = "ristorante";
    protected static final String STELLE = "stelle";
    protected static final String DATA = "data";
    protected static final String TABELLA_RECENSIONE = "recensione";
    protected static final String REGISTRA_RECENSIONE = "INSERT INTO " + TABELLA_RECENSIONE + " (" +
            UTENTE + ", " +
            RISTORANTE + ", " +
            STELLE + ", " +
            DATA + ") " +
            "VALUES (?, ?, ?, ?)";



}
