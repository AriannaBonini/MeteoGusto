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
    protected static final String CUCINA = "cucina";
    protected static final String CAP = "cap";
    protected static final String FASCIA_PREZZO = "fascia_prezzo";
    protected static final String MEDIA_STELLE = "media_stelle";
    protected static final String INIZIO_PRANZO = "ora_inizio_pranzo";
    protected static final String FINE_PRANZO = "ora_fine_pranzo";
    protected static final String INIZIO_CENA = "ora_inizio_cena";
    protected static final String FINE_CENA = "ora_fine_cena";
    protected static final String TABELLA_RISTORANTE = "ristorante";


    /* COSTANTI PER IL CODICE SQL */
    protected static final String AND = " AND ";

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
                    INIZIO_PRANZO + ", " +
                    FINE_PRANZO + ", " +
                    INIZIO_CENA + ", " +
                    FINE_CENA + ", " +
                    FASCIA_PREZZO +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    protected static final String RICERCA_RISTORANTI_PER_CITTA =
            "SELECT " +
                    NOME + ", " +
                    PARTITA_IVA + ", " +
                    TELEFONO + ", " +
                    INDIRIZZO + ", " +
                    CIVICO + ", " +
                    CITTA + ", " +
                    CAP + ", " +
                    CUCINA + ", " +
                    FASCIA_PREZZO + ", " +
                    MEDIA_STELLE + ", " +
                    INIZIO_PRANZO + ", " +
                    FINE_PRANZO + ", " +
                    INIZIO_CENA + ", " +
                    FINE_CENA + " " +
                    "FROM " + TABELLA_RISTORANTE + " " +
                    "WHERE " + CITTA + " = ?";

    protected static final String MEDIA_STELLE_PER_RISTORANTE = "SELECT " + MEDIA_STELLE + " " +
                    "FROM " + TABELLA_RISTORANTE + " " +
                    "WHERE " + NOME + " = ?";



}
