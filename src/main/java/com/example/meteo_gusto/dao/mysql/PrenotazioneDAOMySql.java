package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLPrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
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
                    "Errore durante il controllo disponibilità posti per data e fascia oraria", e
            );
        }

        return p;
    }



}
