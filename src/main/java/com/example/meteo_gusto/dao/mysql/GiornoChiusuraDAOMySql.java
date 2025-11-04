package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLGiornoChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.model.GiornoChiusura;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GiornoChiusuraDAOMySql extends QuerySQLGiornoChiusuraDAO implements GiornoChiusuraDAO {

    @Override
    public void registraGiorniChiusuraRistorante(List<GiornoChiusura> giorniChiusura) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_GIORNI_CHIUSURA_RISTORANTE)) {

                for (GiornoChiusura giornoChiusura : giorniChiusura) {
                    Ristorante ristorante = giornoChiusura.getRistorante();
                    GiorniSettimana giorno = giornoChiusura.getNomeGiornoChiusura();

                    if (ristorante == null || ristorante.getPartitaIVA() == null) {
                        throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata per il giorno: " + giorno);
                    }

                    ps.setString(1, ristorante.getPartitaIVA());
                    ps.setString(2, giorno.toString());
                    ps.addBatch();
                }

                ps.executeBatch();
            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione dei giorni di chiusura", e);
        }
    }

    @Override
    public List<GiornoChiusura> giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {
        List<GiornoChiusura> listaGiorniChiusuraRistorante= new ArrayList<>();

        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(CONTROLLA_GIORNO_CHIUSURA_IN_DATA)) {

            ps.setString(1, ristorante.getPartitaIVA());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    GiornoChiusura giornoChiusura= new GiornoChiusura(new Ristorante(rs.getString(RISTORANTE)),GiorniSettimana.giorniSettimanaDaId(rs.getString(GIORNO)));
                    listaGiorniChiusuraRistorante.add(giornoChiusura);
                }
            }

            return listaGiorniChiusuraRistorante;

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il controllo dei giorni di chiusura", e);
        }
    }





}
