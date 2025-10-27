package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLDietaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.model.Dieta;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DietaDAOMySql extends QuerySQLDietaDAO implements DietaDAO {
    @Override
    public void registraDieta(Dieta dieta) throws EccezioneDAO {
        if (dieta == null || dieta.getDieta() == null) return;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DIETA)) {

                for (TipoDieta nomeDieta : dieta.getDieta()) {
                    ps.setString(1, dieta.getRistorante().getPartitaIVA());
                    ps.setString(2, nomeDieta.toString());
                    int righeInserite;
                    righeInserite = ps.executeUpdate();

                    if (righeInserite == 0) {
                        throw new EccezioneDAO("Nessuna riga inserita per la dieta: " + nomeDieta);
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione della dieta offerta dal ristorante", e);
        }
    }
}

