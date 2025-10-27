package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class RistoranteDAOMySql extends QuerySQLRistoranteDAO implements RistoranteDAO {

    @Override
    public void registraRistorante(Ristorante ristorante) throws EccezioneDAO{
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_RISTORANTE)) {

                ps.setString(1, ristorante.getNome());
                ps.setString(2, ristorante.getPartitaIVA());
                ps.setString(3, ristorante.getTelefono());
                ps.setString(4, ristorante.getProprietario().getEmail());
                ps.setString(5, ristorante.getPosizione().getVia());
                ps.setString(6, ristorante.getPosizione().getCivico());
                ps.setString(7, ristorante.getPosizione().getCitta());
                ps.setString(8, ristorante.getPosizione().getCap());
                ps.setString(9, ristorante.getOffertaCulinaria().getCucina().getId());
                ps.setString(10, ristorante.getOffertaCulinaria().getFasciaPrezzo().getId());
                ps.setTime(11, Time.valueOf(ristorante.getOrari().getInizioPranzo()));
                ps.setTime(12, Time.valueOf(ristorante.getOrari().getFinePranzo()));
                ps.setTime(13, Time.valueOf(ristorante.getOrari().getInizioCena()));
                ps.setTime(14, Time.valueOf(ristorante.getOrari().getFineCena()));

                int righeInserite = ps.executeUpdate();
                if (righeInserite == 0) {
                    throw new EccezioneDAO("Nessuna riga inserita per il ristoratore.");
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione dell'utente", e);
        }
    }



}
