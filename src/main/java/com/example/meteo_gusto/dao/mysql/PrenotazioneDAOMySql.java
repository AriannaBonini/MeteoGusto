package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLPrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                ps.setString(5, prenotazione.getNote());
                ps.setInt(6, prenotazione.getNumeroPersone());
                ps.setString(7, prenotazione.getUtente().getEmail());
                ps.setString(8, prenotazione.getFasciaOraria().getId());

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

    @Override
    public void resettaNotificheUtente(Persona utente) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(SEGNA_NOTIFICHE_COME_LETTE)) {

                ps.setString(1, utente.getEmail());
                ps.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la reimpostazione delle notifiche dell'utente", e);
        }
    }

    @Override
    public List<Prenotazione> selezionaPrenotazioniUtente(Persona utente) throws EccezioneDAO {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(PRENOTAZIONI_UTENTE)) {

                ps.setString(1, utente.getEmail());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Prenotazione prenotazione = new Prenotazione();
                        prenotazione.setData(rs.getDate(DATA).toLocalDate());
                        prenotazione.setOra(rs.getTime(ORA).toLocalTime());

                        Ambiente ambiente= new Ambiente();
                        ambiente.setIdAmbiente(rs.getInt(AMBIENTE));

                        prenotazione.setAmbiente(ambiente);
                        prenotazione.setNote(rs.getString(NOTE_PRENOTAZIONE));
                        prenotazione.setNumeroPersone(rs.getInt(NUMERO_PERSONE));

                        prenotazioni.add(prenotazione);
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni dell'utente", e);
        }

        return prenotazioni;
    }








}
