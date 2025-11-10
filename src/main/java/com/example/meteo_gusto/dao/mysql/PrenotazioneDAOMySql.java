package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLPrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioneDAOMySql extends QuerySQLPrenotazioneDAO implements PrenotazioneDAO {

    @Override
    public Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO {
        Prenotazione p = null;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(POSTI_DISPONIBILI)) {

                ps.setDate(1, java.sql.Date.valueOf(prenotazione.getData()));
                ps.setString(2, prenotazione.getFasciaOraria().getId());
                ps.setInt(3, prenotazione.getAmbiente().getIdAmbiente());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        p = new Prenotazione(
                                null,
                                null,
                                rs.getInt("totale_persone"),
                                null,
                                null,
                                null
                        );
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO(
                    "Errore durante il controllo disponibilità posti per data e fascia oraria ", e
            );
        }

        return p;
    }

    @Override
    public boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        boolean inserita ;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(INSERISCI_PRENOTAZIONE)) {

                ps.setString(1, prenotazione.getAmbiente().getTipoAmbiente().toString());
                ps.setString(2, prenotazione.getAmbiente().getRistorante());
                ps.setDate(3, java.sql.Date.valueOf(prenotazione.getData()));
                ps.setTime(4, java.sql.Time.valueOf(prenotazione.getOra()));
                ps.setInt(5, prenotazione.getNumeroPersone());
                ps.setString(6, prenotazione.getUtente().getEmail());
                ps.setString(7, prenotazione.getFasciaOraria().getId());

                int righe = ps.executeUpdate();
                inserita = righe > 0;
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante l'inserimento della prenotazione ", e);
        }

        return inserita;
    }

    @Override
    public boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        boolean esiste = false;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(PRENOTAZIONE_ESISTENTE)) {

                ps.setString(1, prenotazione.getUtente().getEmail());
                ps.setDate(2, java.sql.Date.valueOf(prenotazione.getData()));
                ps.setString(3, prenotazione.getFasciaOraria().toString());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt("numero_prenotazioni") > 0) {
                        esiste = true;
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la verifica della prenotazione esistente", e);
        }

        return esiste;
    }

    @Override
    public Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO {
        Prenotazione prenotazione= new Prenotazione();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CONTA_NOTIFICHE_UTENTE)) {

                ps.setString(1, utente.getEmail());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        prenotazione.setNumeroNotifiche(rs.getInt("notifiche_attive"));
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche utente", e);
        }

        return prenotazione;
    }






}
