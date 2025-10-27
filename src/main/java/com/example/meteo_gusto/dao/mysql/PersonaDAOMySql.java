package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLPersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonaDAOMySql extends QuerySQLPersonaDAO implements PersonaDAO {

    @Override
    public void registraPersona(Persona utente) throws EccezioneDAO{
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_UTENTE)) {

                ps.setString(1, utente.getNome());
                ps.setString(2, utente.getCognome());
                ps.setString(3, utente.getTelefono());
                ps.setString(4, utente.getEmail());
                ps.setString(5, utente.getPassword());
                ps.setString(6, utente.getTipoPersona().getId());

                int righeInserite = ps.executeUpdate();
                if (righeInserite == 0) {
                    throw new EccezioneDAO("Nessuna riga inserita per l'utente.");
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente", e);
        }
    }







}
