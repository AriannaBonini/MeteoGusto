package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.GiorniChiusuraDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLGiorniChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.model.GiorniChiusura;
import com.example.meteo_gusto.model.Ristorante;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GiorniChiusuraDAOMySql extends QuerySQLGiorniChiusuraDAO implements GiorniChiusuraDAO {

    @Override
    public void registraGiorniChiusuraRistorante(GiorniChiusura giorniChiusura) throws EccezioneDAO {
        if (giorniChiusura == null || giorniChiusura.getGiorniChiusura() == null) return;

        Ristorante ristorante = giorniChiusura.getRistorante();
        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
        }

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_GIORNI_CHIUSURA_RISTORANTE)) {

                for (GiorniSettimana giorno : giorniChiusura.getGiorniChiusura()) {
                    ps.setString(1, ristorante.getPartitaIVA());
                    ps.setString(2, giorno.toString());

                    int righeInserite;
                    righeInserite = ps.executeUpdate();
                    if (righeInserite == 0) {
                        throw new EccezioneDAO("Nessuna riga inserita per il giorno: " + giorno);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione dei giorni di chiusura", e);
        }
    }
}
