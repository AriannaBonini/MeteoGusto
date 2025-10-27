package com.example.meteo_gusto.dao.mysql.query_sql;

public abstract class QuerySQLPersonaDAO {

    protected QuerySQLPersonaDAO(){/* Costruttore vuoto */}

    protected static final String NOME = "nome";
    protected static final String COGNOME = "cognome";
    protected static final String TELEFONO = "telefono";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password_hash";
    protected static final String RUOLO = "tipo_ruolo";
    protected static final String TABELLA_PERSONA = "persona";
    protected static final String REGISTRA_UTENTE= " INSERT INTO " + TABELLA_PERSONA + " (" +
            NOME + ", " +
            COGNOME + ", " +
            TELEFONO + ", " +
            EMAIL + ", " +
            PASSWORD + ", " +
            RUOLO + ") " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    protected static final String CONTROLLA_CREDENZIALI = "SELECT " + NOME + ", " + COGNOME + ", " + TELEFONO + ", " + EMAIL + ", " + PASSWORD + ", " + RUOLO + " " +
                    "FROM " + TABELLA_PERSONA + " " +
                    "WHERE " + EMAIL + " = ? AND " + PASSWORD + " = ?";


}
