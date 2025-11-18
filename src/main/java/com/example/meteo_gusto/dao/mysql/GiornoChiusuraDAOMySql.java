package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLGiornoChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class GiornoChiusuraDAOMySql extends QuerySQLGiornoChiusuraDAO implements GiornoChiusuraDAO {

    @Override
    public void registraGiorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
        }

        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(REGISTRA_GIORNI_CHIUSURA_RISTORANTE)) {

            for (GiorniSettimana giorno : ristorante.getOrari().getGiorniChiusura()) {
                ps.setString(1, ristorante.getPartitaIVA());
                ps.setString(2, giorno.getId());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione dei giorni di chiusura", e);
        }
    }


    @Override
    public GiorniEOrari giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {
        GiorniEOrari ristoranteChiuso = new GiorniEOrari();
        Set<GiorniSettimana> giorniChiusura = new HashSet<>();

        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(CONTROLLA_GIORNO_CHIUSURA_IN_DATA)) {

            ps.setString(1, ristorante.getPartitaIVA());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GiorniSettimana giorno = GiorniSettimana.giorniSettimanaDaId(rs.getString(GIORNO));
                    giorniChiusura.add(giorno);
                }
            }

            ristoranteChiuso.setGiorniChiusura(giorniChiusura);
            return ristoranteChiuso;

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il controllo dei giorni di chiusura", e);
        }
    }






}
