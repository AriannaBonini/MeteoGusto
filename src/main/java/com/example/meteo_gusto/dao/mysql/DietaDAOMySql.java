package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLDietaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.model.Dieta;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DietaDAOMySql extends QuerySQLDietaDAO implements DietaDAO {
    @Override
    public void registraDieta(List<Dieta> listaDieta) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DIETA)) {

                for (Dieta dieta : listaDieta) {
                    Ristorante ristorante = dieta.getRistorante();
                    Set<TipoDieta> tipiDieta = dieta.getTipoDieta();

                    if (ristorante == null || ristorante.getPartitaIVA() == null) {
                        throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata per le diete: " + tipiDieta);
                    }

                    if (tipiDieta != null && !tipiDieta.isEmpty()) {
                        for (TipoDieta tipo : tipiDieta) {
                            ps.setString(1, ristorante.getPartitaIVA());
                            ps.setString(2, tipo.toString());
                            ps.addBatch();
                        }
                    }
                }

                ps.executeBatch();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione delle diete offerte dal ristorante", e);
        }
    }

    @Override
    public Dieta controllaDieteDelRistorante(Dieta filtroDieta) throws EccezioneDAO {
        if (filtroDieta == null || filtroDieta.getRistorante() == null ||
                filtroDieta.getTipoDieta() == null || filtroDieta.getTipoDieta().isEmpty()) {
            return null;
        }

        Set<TipoDieta> dieteValide = new HashSet<>();

        try (Connection conn = new GestoreConnessioneDB().creaConnessione()) {

            String placeholders = filtroDieta.getTipoDieta().stream()
                    .map(d -> "?")
                    .collect(Collectors.joining(", "));

            String query = String.format(CONTROLLA_DIETE_RISTORANTE, placeholders);

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, filtroDieta.getRistorante().getPartitaIVA());

                int index = 2;
                for (TipoDieta tipo : filtroDieta.getTipoDieta()) {
                    ps.setString(index++, tipo.toString());
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String idDieta = rs.getString("dieta");
                        TipoDieta tipo = TipoDieta.tipoDietaDaId(idDieta);
                        dieteValide.add(tipo);
                    }

                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la ricerca delle diete compatibili per il ristorante", e);
        }

        return dieteValide.isEmpty() ? null : new Dieta(filtroDieta.getRistorante(), dieteValide);
    }



}
