package com.example.meteo_gusto.dao.mysql.query_sql;

public class QuerySQLRistoranteDAO {
    protected QuerySQLRistoranteDAO(){/* Costruttore vuoto */}
    protected static final String NOME = "nome";
    protected static final String PARTITA_IVA = "partita_iva";
    protected static final String TELEFONO = "telefono";
    protected static final String PROPRIETARIO = "proprietario";
    protected static final String INDIRIZZO = "indirizzo";
    protected static final String CIVICO = "civico";
    protected static final String CITTA = "citta";
    protected static final String CAP = "cap";
    protected static final String CUCINA = "cucina";
    protected static final String FASCIA_PREZZO = "fascia_prezzo";
    protected static final String PRANZO_INIZIO = "ora_apertura_pranzo";
    protected static final String PRANZO_FINE = "ora_chiusura_pranzo";
    protected static final String CENA_INIZIO = "ora_apertura_cena";
    protected static final String CENA_FINE = "ora_chiusura_cena";
    protected static final String MEDIA_STELLE = "media_stelle";
    protected static final String TABELLA_RISTORANTE = "ristorante";

    protected static final String  REGISTRA_RISTORANTE= "INSERT INTO " + TABELLA_RISTORANTE + " (" +
                    NOME + ", " +
                    PARTITA_IVA + ", " +
                    TELEFONO + ", " +
                    PROPRIETARIO + ", " +
                    INDIRIZZO + ", " +
                    CIVICO + ", " +
                    CITTA + ", " +
                    CAP + ", " +
                    CUCINA + ", " +
                    FASCIA_PREZZO + ", " +
                    PRANZO_INIZIO + ", " +
                    PRANZO_FINE + ", " +
                    CENA_INIZIO + ", " +
                    CENA_FINE +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


}
