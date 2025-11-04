package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLDietaDAO {

    protected QuerySQLDietaDAO(){/* Costruttore vuoto */}
    protected static final String RISTORANTE = "ristorante_id";
    protected static final String DIETA = "dieta";
    protected static final String TABELLA_DIETA = "ristorante_dieta";
    protected static final String REGISTRA_DIETA= " INSERT INTO " + TABELLA_DIETA + " (" +
            RISTORANTE + ", " +
            DIETA + ") " +
            "VALUES (?, ?)";

    protected static final String CONTROLLA_DIETE_RISTORANTE =
            "SELECT DISTINCT " + DIETA + " " +
                    "FROM " + TABELLA_DIETA + " " +
                    "WHERE " + RISTORANTE + " = ? " +
                    "AND " + DIETA + " IN (%s)";


}
