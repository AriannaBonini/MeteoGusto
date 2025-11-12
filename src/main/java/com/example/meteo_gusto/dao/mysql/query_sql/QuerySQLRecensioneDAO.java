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

    protected static final String VERIFICA_RECENSIONE = "SELECT COUNT(*) FROM " + TABELLA_RECENSIONE + " WHERE " +
            UTENTE + " = ? AND " +
            RISTORANTE + " = ?";

    protected static final String AGGIORNA_RECENSIONE = "UPDATE " + TABELLA_RECENSIONE + " SET " +
            STELLE + " = ?, " +
            DATA + " = ? " +
            "WHERE " + UTENTE + " = ? AND " + RISTORANTE + " = ?";

    protected static final String CALCOLA_MEDIA_STELLE_RISTORANTE =
            "SELECT AVG(" + STELLE + ") AS " + STELLE + ", " + RISTORANTE + " " +
                    "FROM " + TABELLA_RECENSIONE + " " +
                    "WHERE " + RISTORANTE + " = ?";







}
