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
import java.util.stream.Collectors;

public class DietaDAOMySql extends QuerySQLDietaDAO implements DietaDAO {
    @Override
    public void registraDieta(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
        }

        Set<TipoDieta> diete = ristorante.getTipoDieta();
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
    public Ristorante controllaDieteDelRistorante(Ristorante ristoranteDaControllare) throws EccezioneDAO {
        if (ristoranteDaControllare == null || ristoranteDaControllare.getPartitaIVA() == null ||
                ristoranteDaControllare.getTipoDieta() == null || ristoranteDaControllare.getTipoDieta().isEmpty()) {
            return null;
        }

        Set<TipoDieta> dieteValide = new HashSet<>();

        try (Connection conn = new GestoreConnessioneDB().creaConnessione()) {

            String placeholders = ristoranteDaControllare.getTipoDieta().stream()
                    .map(d -> "?")
                    .collect(Collectors.joining(", "));

            String query = String.format(CONTROLLA_DIETE_RISTORANTE, placeholders);

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, ristoranteDaControllare.getPartitaIVA());

                int index = 2;
                for (TipoDieta tipo : ristoranteDaControllare.getTipoDieta()) {
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

        if (dieteValide.isEmpty()) {
            return null;
        }

        Ristorante ristoranteValido = new Ristorante();
        ristoranteValido.setTipoDieta(dieteValide);

        return ristoranteValido;
    }




}
