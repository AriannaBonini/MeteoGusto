package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLAmbienteDAO {
    protected QuerySQLAmbienteDAO() {/* Costruttore vuoto */}
    protected static final String AMBIENTE = "ambiente";
    protected static final String RISCALDAMENTO = "riscaldamento";
    protected static final String RAFFREDDAMENTO = "raffreddamento";
    protected static final String NUMERO_COPERTI = "coperti";
    protected static final String RISTORANTE = "ristorante_id";
    protected static final String ID_AMBIENTE = "id";
    protected static final String TABELLA_AMBIENTE = "ambiente";
    protected static final String  REGISTRA_DISPONIBILITA= "INSERT INTO " + TABELLA_AMBIENTE + " (" +
            AMBIENTE + ", " +
            RISCALDAMENTO + ", " +
            RAFFREDDAMENTO + ", " +
            RISTORANTE + ", " +
            NUMERO_COPERTI +
            ") VALUES (?, ?, ?, ?, ?)";

    protected static final String CERCA_AMBIENTI = "SELECT " +
                    ID_AMBIENTE + ", " +
                    AMBIENTE + ", " +
                    NUMERO_COPERTI + " " +
                    "FROM " + TABELLA_AMBIENTE + " " +
                    "WHERE " + RISTORANTE + " = ?";







}

