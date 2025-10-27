package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLDisponibilitaDAO {
    protected QuerySQLDisponibilitaDAO() {/* Costruttore vuoto */}
    protected static final String AMBIENTE = "ambiente";
    protected static final String FASCIA_ORARIA = "fascia_oraria";
    protected static final String RISTORANTE = "ristorante";
    protected static final String RISCALDAMENTO = "riscaldamento";
    protected static final String RAFFREDDAMENTO = "raffreddamento";
    protected static final String NUMERO_COPERTI = "coperti";
    protected static final String TABELLA_DISPONIBILITA = "disponibilita";

    protected static final String  REGISTRA_DISPONIBILITA= "INSERT INTO " + TABELLA_DISPONIBILITA + " (" +
            AMBIENTE + ", " +
            FASCIA_ORARIA + ", " +
            RISTORANTE + ", " +
            RISCALDAMENTO + ", " +
            RAFFREDDAMENTO + ", " +
            NUMERO_COPERTI +
            ") VALUES (?, ?, ?, ?, ?, ?)";




}

