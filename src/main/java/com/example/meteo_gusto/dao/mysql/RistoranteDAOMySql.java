package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.model.Filtro;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Posizione;
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
                ps.setString(5, ristorante.getPosizione().getVia());
                ps.setInt(6, Integer.parseInt(ristorante.getPosizione().getCivico()));
                ps.setString(7, ristorante.getPosizione().getCitta());
                ps.setString(8, ristorante.getPosizione().getCap());
                ps.setString(9, ristorante.getCucina().name());
                ps.setTime(10, Time.valueOf(ristorante.getOrari().getInizioPranzo()));
                ps.setTime(11, Time.valueOf(ristorante.getOrari().getFinePranzo()));
                ps.setTime(12, Time.valueOf(ristorante.getOrari().getInizioCena()));
                ps.setTime(13, Time.valueOf(ristorante.getOrari().getFineCena()));
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


                    Ristorante ristoranteTrovato = new Ristorante(rs.getString(PARTITA_IVA));

                    ristoranteTrovato.setNomeRistorante(rs.getString(NOME));
                    ristoranteTrovato.setTelefonoRistorante(rs.getString(TELEFONO));
                    ristoranteTrovato.setCucina(TipoCucina.fromId(rs.getString(CUCINA)));
                    ristoranteTrovato.setFasciaPrezzo(FasciaPrezzoRistorante.fasciaPrezzoDaId(rs.getString(FASCIA_PREZZO)));
                    ristoranteTrovato.setPosizione(new Posizione(rs.getString(INDIRIZZO), rs.getString(CITTA), rs.getString(CAP)));
                    ristoranteTrovato.setMediaStelle(rs.getBigDecimal(MEDIA_STELLE));

                    Time inizioPranzo = rs.getTime(INIZIO_PRANZO);
                    Time finePranzo   = rs.getTime(FINE_PRANZO);
                    Time inizioCena   = rs.getTime(INIZIO_CENA);
                    Time fineCena     = rs.getTime(FINE_CENA);

                    if (ristoranteTrovato.getOrari() == null) {
                        ristoranteTrovato.setOrari(new GiorniEOrari());
                    }

                    if (inizioPranzo != null) ristoranteTrovato.getOrari().setInizioPranzo(inizioPranzo.toLocalTime());
                    if (finePranzo != null)   ristoranteTrovato.getOrari().setFinePranzo(finePranzo.toLocalTime());
                    if (inizioCena != null)   ristoranteTrovato.getOrari().setInizioCena(inizioCena.toLocalTime());
                    if (fineCena != null)     ristoranteTrovato.getOrari().setFineCena(fineCena.toLocalTime());

                    listaRistoranti.add(ristoranteTrovato);
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la ricerca dei ristoranti filtrati per città", e);
        }

        return listaRistoranti;
    }

    @Override
    public Ristorante mediaStelleRistorante(Ristorante ristorante) throws EccezioneDAO {
        Ristorante mediaRistorante= new Ristorante();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(MEDIA_STELLE_PER_RISTORANTE)) {

                ps.setString(1, ristorante.getPartitaIVA());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        mediaRistorante.setMediaStelle(rs.getBigDecimal(MEDIA_STELLE));
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero della media stelle del ristorante", e);
        }

        return mediaRistorante;
    }

    @Override
    public void aggiornaMediaStelle(Ristorante ristorante) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(AGGIORNA_MEDIA_STELLE_RISTORANTE)) {

                ps.setBigDecimal(1, ristorante.getMediaStelle());
                ps.setString(2, ristorante.getPartitaIVA());

                ps.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante l'aggiornamento della media stelle del ristorante", e);
        }
    }



}

