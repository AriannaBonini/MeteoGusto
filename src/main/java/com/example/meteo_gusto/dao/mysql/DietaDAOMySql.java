package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLDietaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DietaDAOMySql extends QuerySQLDietaDAO implements DietaDAO {
    @Override
    public void registraDieta(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
        }

        Set<TipoDieta> diete = ristorante.dieteOfferte();
        if (diete == null || diete.isEmpty()) {
            return;
        }

        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(REGISTRA_DIETA)) {

            for (TipoDieta dieta : diete) {
                ps.setString(1, ristorante.getPartitaIVA());
                ps.setString(2, dieta.getId());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione delle diete offerte dal ristorante", e);
        }
    }

    @Override
    public Ristorante controllaDieteDelRistorante(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null || ristorante.getPartitaIVA() == null
                || ristorante.dieteOfferte() == null || ristorante.dieteOfferte().isEmpty()) {
            return null;
        }

        Set<TipoDieta> dieteValide = new HashSet<>();

        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(CONTROLLA_DIETE_RISTORANTE)) {

            ps.setString(1, ristorante.getPartitaIVA());

            for (TipoDieta dieta : ristorante.dieteOfferte()) {
                ps.setString(2, dieta.name());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dieteValide.add(dieta);
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore nella ricerca delle diete compatibili", e);
        }

        if (dieteValide.isEmpty()) return null;

        Ristorante ristoranteValido = new Ristorante();
        ristoranteValido.setDiete(dieteValide);
        return ristoranteValido;
    }


}
