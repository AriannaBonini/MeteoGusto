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
import java.sql.SQLException;
import java.util.List;

public class DietaDAOMySql extends QuerySQLDietaDAO implements DietaDAO {
    @Override
    public void registraDieta(List<Dieta> listaDieta) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DIETA)) {

                for (Dieta dieta : listaDieta) {
                    Ristorante ristorante = dieta.getRistorante();
                    TipoDieta tipoDieta = dieta.getTipoDieta();

                    if (ristorante == null || ristorante.getPartitaIVA() == null) {
                        throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata per la dieta: " + tipoDieta);
                    }

                    ps.setString(1, ristorante.getPartitaIVA());
                    ps.setString(2, tipoDieta.toString());
                    ps.addBatch();
                }

                ps.executeBatch();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione della dieta offerta dal ristorante", e);
        }
    }


}

