package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.model.Filtro;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDAOMySql extends QuerySQLRistoranteDAO implements RistoranteDAO {

    @Override
    public void registraRistorante(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null) {
            throw new EccezioneDAO("Il ristorante da registrare non può essere nullo.");
        }

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_RISTORANTE)) {

                ps.setString(1, ristorante.getNomeRistorante());
                ps.setString(2, ristorante.getPartitaIVA());
                ps.setString(3, ristorante.getTelefonoRistorante());
                ps.setString(4, ristorante.getProprietario().getEmail());
                ps.setString(5, ristorante.getVia());
                ps.setInt(6, Integer.parseInt(ristorante.getCivico()));
                ps.setString(7, ristorante.getCitta());
                ps.setString(8, ristorante.getCap());
                ps.setString(9, ristorante.getCucina().name());
                ps.setTime(10, Time.valueOf(ristorante.getInizioPranzo()));
                ps.setTime(11, Time.valueOf(ristorante.getFinePranzo()));
                ps.setTime(12, Time.valueOf(ristorante.getInizioCena()));
                ps.setTime(13, Time.valueOf(ristorante.getFineCena()));
                ps.setString(14, ristorante.getFasciaPrezzo().name());

                int righeInserite = ps.executeUpdate();
                if (righeInserite == 0) {
                    throw new EccezioneDAO("Nessuna riga inserita per il ristorante: " + ristorante.getNomeRistorante());
                }

            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristorante: " + ristorante.getNomeRistorante(), e);
        }
    }


    @Override
    public List<Ristorante> filtraRistorantiPerCitta(Filtro filtro) throws EccezioneDAO {
        List<Ristorante> listaRistoranti = new ArrayList<>();
        try (Connection conn = new GestoreConnessioneDB().creaConnessione();
             PreparedStatement ps = conn.prepareStatement(RICERCA_RISTORANTI_PER_CITTA)) {

            ps.setString(1, filtro.getCitta());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Ristorante ristoranteTrovato = new Ristorante(
                            rs.getString(PARTITA_IVA),
                            null,
                            rs.getString(NOME),
                            rs.getString(TELEFONO),
                            TipoCucina.fromId(rs.getString(CUCINA)),
                            FasciaPrezzoRistorante.fasciaPrezzoDaId(rs.getString(FASCIA_PREZZO)),
                            rs.getString(INDIRIZZO),
                            rs.getString(CITTA),
                            rs.getString(CAP),
                            rs.getBigDecimal(MEDIA_STELLE)
                    );

                    Time inizioPranzo = rs.getTime(INIZIO_PRANZO);
                    Time finePranzo   = rs.getTime(FINE_PRANZO);
                    Time inizioCena   = rs.getTime(INIZIO_CENA);
                    Time fineCena     = rs.getTime(FINE_CENA);

                    if (inizioPranzo != null) ristoranteTrovato.setInizioPranzo(inizioPranzo.toLocalTime());
                    if (finePranzo != null)   ristoranteTrovato.setFinePranzo(finePranzo.toLocalTime());
                    if (inizioCena != null)   ristoranteTrovato.setInizioCena(inizioCena.toLocalTime());
                    if (fineCena != null)     ristoranteTrovato.setFineCena(fineCena.toLocalTime());

                    listaRistoranti.add(ristoranteTrovato);
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la ricerca dei ristoranti filtrati per città", e);
        }

        return listaRistoranti;
    }


}
