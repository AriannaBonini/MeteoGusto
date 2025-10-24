package com.example.meteo_gusto.dao.mysql;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class GestoreConnessioneDB {
    private final String url;
    private final String utente;
    private final String password;

    public GestoreConnessioneDB() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new IOException("Configurazione database mancante");
            }
            properties.load(input);

            this.url = properties.getProperty("jdbcURL");
            this.utente = properties.getProperty("jdbcUsername");
            this.password = properties.getProperty("jdbcPassword");

            if (url == null || utente == null || password == null) {
                throw new IOException("Configurazione database incompleta");
            }

        } catch (IOException e) {
            throw new IOException("Errore configurazione database", e);
        }
    }

    /**
     * Crea una NUOVA connessione al database
     * @return Connection una nuova connessione al DB
     * @throws SQLException se la connessione fallisce
     */
    public Connection creaConnessione() throws SQLException {
        return DriverManager.getConnection(url, utente, password);
    }
}