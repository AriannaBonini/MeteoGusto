package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRecensioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecensioneDAOMySql extends QuerySQLRecensioneDAO implements RecensioneDAO {
    @Override
    public void nuovaRecensione(Recensione recensione) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_RECENSIONE)) {


                ps.setString(1, recensione.getUtente());
                ps.setString(2, recensione.getRistorante());
                ps.setBigDecimal(3, recensione.getStelle());
                ps.setDate(4, java.sql.Date.valueOf(recensione.getData()));

                ps.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante l'inserimento della recensione", e);
        }
    }

    @Override
    public boolean esisteRecensione(Recensione recensione) throws EccezioneDAO {
        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(VERIFICA_RECENSIONE)) {

            ps.setString(1, recensione.getUtente());
            ps.setString(2, recensione.getRistorante());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la verifica dell'esistenza della recensione", e);
        }

        return false;
    }


    @Override
    public void aggiornaRecensione(Recensione recensione) throws EccezioneDAO {
        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(AGGIORNA_RECENSIONE)) {

            ps.setBigDecimal(1, recensione.getStelle());
            ps.setDate(2, java.sql.Date.valueOf(recensione.getData()));
            ps.setString(3, recensione.getUtente());
            ps.setString(4, recensione.getRistorante());

            ps.executeUpdate();

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante l'aggiornamento della recensione", e);
        }
    }

    @Override
    public Ristorante calcolaNuovaMedia(Recensione recensione) throws EccezioneDAO {
        Ristorante ristoranteConMedia = new Ristorante();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CALCOLA_MEDIA_STELLE_RISTORANTE)) {

                ps.setString(1, recensione.getRistorante());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ristoranteConMedia.setPartitaIVA(rs.getString(RISTORANTE));
                        ristoranteConMedia.setMediaStelle(rs.getBigDecimal(STELLE));
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il calcolo della nuova media stelle del ristorante", e);
        }

        return ristoranteConMedia;
    }





}
