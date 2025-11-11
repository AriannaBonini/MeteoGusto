package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.PersonaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLPersonaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.model.Persona;

import java.io.IOException;
import java.sql.*;

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


    @Override
    public Persona login(Persona persona) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CONTROLLA_CREDENZIALI)) {

                ps.setString(1, persona.getEmail());
                ps.setString(2, persona.getPassword());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Persona(
                                rs.getString(NOME),
                                rs.getString(COGNOME),
                                rs.getString(TELEFONO),
                                rs.getString(EMAIL),
                                rs.getString(PASSWORD),
                                TipoPersona.fromId(rs.getString(RUOLO)),
                                null
                        );
                    } else {
                        return null;
                    }
                }

            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il login dell'utente", e);
        }
    }



    @Override
    public Persona informazioniUtente(Persona utente) throws EccezioneDAO {

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(OTTIENI_DATI_DA_EMAIL)) {

                ps.setString(1, utente.getEmail());


                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        return new Persona(
                                rs.getString(NOME),
                                rs.getString(COGNOME),
                                rs.getString(TELEFONO),
                                rs.getString(EMAIL),
                                null,
                                null,
                                null
                        );
                    } else {
                        return null;
                    }
                }

            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero dei dati dell'utente tramite email", e);
        }

    }

}
