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
import java.util.Collections;
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

                ps.setInt(1, prenotazione.getAmbiente().getIdAmbiente());
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
                 PreparedStatement ps = conn.prepareStatement(SEGNA_NOTIFICHE_UTENTE_COME_LETTE)) {

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

    @Override
    public List<Prenotazione> selezionaPrenotazioniRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        if (ambienti == null || ambienti.isEmpty()) {
            return prenotazioni;
        }

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            String query = generaQueryPrenotazioniRistoratore(ambienti.size());

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                for (int i = 0; i < ambienti.size(); i++) {
                    ps.setInt(i + 1, ambienti.get(i).getIdAmbiente());
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Prenotazione prenotazione = new Prenotazione();
                        prenotazione.setData(rs.getDate(DATA).toLocalDate());
                        prenotazione.setOra(rs.getTime(ORA).toLocalTime());
                        prenotazione.setNote(rs.getString(NOTE_PRENOTAZIONE));
                        prenotazione.setNumeroPersone(rs.getInt(NUMERO_PERSONE));

                        Ambiente ambiente = new Ambiente();
                        ambiente.setIdAmbiente(rs.getInt(AMBIENTE));
                        prenotazione.setAmbiente(ambiente);
                        prenotazione.setUtente(new Persona(rs.getString(UTENTE)));

                        prenotazioni.add(prenotazione);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni per più ambienti", e);
        }

        return prenotazioni;
    }

    private String generaQueryPrenotazioniRistoratore(int numeroAmbienti) {
        String placeholders = String.join(",", Collections.nCopies(numeroAmbienti, "?"));
        return PRENOTAZIONI_RISTORATORE + placeholders + ")";
    }

    @Override
    public Prenotazione contaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        Prenotazione prenotazione = new Prenotazione();

        if (ambienti == null || ambienti.isEmpty()) {
            prenotazione.setNumeroNotifiche(0);
            return prenotazione;
        }

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            String query = generaQueryContaNotificheRistoratore(ambienti.size());

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                for (int i = 0; i < ambienti.size(); i++) {
                    ps.setInt(i + 1, ambienti.get(i).getIdAmbiente());
                }

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        prenotazione.setNumeroNotifiche(rs.getInt("notifiche_attive"));
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche per il ristoratore", e);
        }

        return prenotazione;
    }

    private String generaQueryContaNotificheRistoratore(int numeroAmbienti) {
        String placeholders = String.join(",", Collections.nCopies(numeroAmbienti, "?"));
        return CONTA_NOTIFICHE_RISTORATORE_PREFIX + placeholders + ")";
    }
    @Override
    public void resettaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {

        if (ambienti == null || ambienti.isEmpty()) {
            return;
        }

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            String query = generaQueryResettaNotificheRistoratore(ambienti.size());

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                for (int i = 0; i < ambienti.size(); i++) {
                    ps.setInt(i + 1, ambienti.get(i).getIdAmbiente());
                }

                ps.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la reimpostazione delle notifiche del ristoratore", e);
        }
    }

    private String generaQueryResettaNotificheRistoratore(int numeroAmbienti) {
        String placeholders = String.join(",", Collections.nCopies(numeroAmbienti, "?"));
        return SEGNA_NOTIFICHE_RISTORATORE_COME_LETTE + placeholders + ")";
    }






}
