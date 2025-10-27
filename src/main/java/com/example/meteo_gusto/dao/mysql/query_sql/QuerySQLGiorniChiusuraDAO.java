package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLGiorniChiusuraDAO {
    protected QuerySQLGiorniChiusuraDAO(){/* Costruttore vuoto */}
    protected static final String RISTORANTE = "ristorante";
    protected static final String GIORNO = "giorno";
    protected static final String TABELLA_GIORNI_CHIUSURA = "giornichiusura";

    protected static final String REGISTRA_GIORNI_CHIUSURA_RISTORANTE= " INSERT INTO " + TABELLA_GIORNI_CHIUSURA + " (" +
            RISTORANTE + ", " +
            GIORNO + ") " +
            "VALUES (?, ?)";


}
