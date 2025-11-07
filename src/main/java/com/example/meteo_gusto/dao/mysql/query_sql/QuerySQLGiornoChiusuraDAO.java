package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLGiornoChiusuraDAO {
    protected QuerySQLGiornoChiusuraDAO(){/* Costruttore vuoto */}
    protected static final String RISTORANTE = "ristorante_id";
    protected static final String GIORNO = "giorno";
    protected static final String TABELLA_GIORNI_CHIUSURA = "giornichiusura";

    protected static final String REGISTRA_GIORNI_CHIUSURA_RISTORANTE= " INSERT INTO " + TABELLA_GIORNI_CHIUSURA + " (" +
            RISTORANTE + ", " +
            GIORNO + ") " +
            "VALUES (?, ?)";

    protected static final String CONTROLLA_GIORNO_CHIUSURA_IN_DATA =
            "SELECT " + GIORNO + " " +
                    "FROM " + TABELLA_GIORNI_CHIUSURA + " " +
                    "WHERE " + RISTORANTE + " = ?";





}
