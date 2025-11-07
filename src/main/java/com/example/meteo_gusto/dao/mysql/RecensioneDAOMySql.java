package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRecensioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecensioneDAOMySql extends QuerySQLRecensioneDAO implements RecensioneDAO {
    @Override
    public void nuovaRecensione(Recensione recensione) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_RECENSIONE)) {

                ps.setString(1, recensione.getUtente().getEmail());
                ps.setString(2, recensione.getRistorante().getPartitaIVA());
                ps.setBigDecimal(3, recensione.getStelle());
                ps.setDate(4, java.sql.Date.valueOf(recensione.getData()));

                ps.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante l'inserimento della recensione", e);
        }
    }

}
