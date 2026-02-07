package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLRistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.model.*;
import java.io.IOException;
import java.math.BigDecimal;
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

                ps.setString(1, ristorante.getNome());
                ps.setString(2, ristorante.getPartitaIVA());
                ps.setString(3, ristorante.getTelefono());
                ps.setString(4, ristorante.getRistoratore());
                ps.setString(5, ristorante.posizioneRistorante().via());
                ps.setInt(6, Integer.parseInt(ristorante.posizioneRistorante().numeroCivico()));
                ps.setString(7, ristorante.posizioneRistorante().getCitta());
                ps.setString(8, ristorante.posizioneRistorante().getCap());
                ps.setString(9, ristorante.cucinaRistorante().getId());
                ps.setTime(10, Time.valueOf(ristorante.aperturaRistorante().orarioInizioPranzo()));
                ps.setTime(11, Time.valueOf(ristorante.aperturaRistorante().orarioFinePranzo()));
                ps.setTime(12, Time.valueOf(ristorante.aperturaRistorante().orarioInizioCena()));
                ps.setTime(13, Time.valueOf(ristorante.aperturaRistorante().orarioFineCena()));
                ps.setString(14, ristorante.fasciaPrezzoRistorante().name());

                int righeInserite = ps.executeUpdate();
                if (righeInserite == 0) {
                    throw new EccezioneDAO("Nessuna riga inserita per il ristorante: " + ristorante.getNome());
                }

            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione del ristorante: " + ristorante.getNome(), e);
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

                    ristoranteTrovato.setNome(rs.getString(NOME));
                    ristoranteTrovato.setCucina(TipoCucina.tipoCucinaDaId(rs.getString(CUCINA)));
                    ristoranteTrovato.setFasciaPrezzo(FasciaPrezzoRistorante.fasciaPrezzoDaId(rs.getString(FASCIA_PREZZO)));

                    ristoranteTrovato.setPosizione(new Posizione());
                    ristoranteTrovato.posizioneRistorante().setCitta(rs.getString(CITTA));

                    ristoranteTrovato.setMediaStelle(rs.getBigDecimal(MEDIA_STELLE));

                    Time inizioPranzo = rs.getTime(INIZIO_PRANZO);
                    Time finePranzo   = rs.getTime(FINE_PRANZO);
                    Time inizioCena   = rs.getTime(INIZIO_CENA);
                    Time fineCena     = rs.getTime(FINE_CENA);

                    if (ristoranteTrovato.aperturaRistorante() == null) {
                        ristoranteTrovato.setOrariApertura(new GiorniEOrari());
                    }

                    if (inizioPranzo != null) ristoranteTrovato.aperturaRistorante().setInizioPranzo(inizioPranzo.toLocalTime());
                    if (finePranzo != null)   ristoranteTrovato.aperturaRistorante().setFinePranzo(finePranzo.toLocalTime());
                    if (inizioCena != null)   ristoranteTrovato.aperturaRistorante().setInizioCena(inizioCena.toLocalTime());
                    if (fineCena != null)     ristoranteTrovato.aperturaRistorante().setFineCena(fineCena.toLocalTime());

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

    @Override
    public Ristorante selezionaInfoRistorante(Ambiente ambiente) throws EccezioneDAO {
        Ristorante ristorante = null;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(SELEZIONA_INFO_RISTORANTE)) {

                ps.setString(1, ambiente.getRistorante());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ristorante = new Ristorante();
                        ristorante.setNome(rs.getString(NOME));

                        Posizione posizione= new Posizione();
                        posizione.indirizzoCompleto(rs.getString(INDIRIZZO), rs.getString(CIVICO));
                        posizione.setCitta(rs.getString(CITTA));
                        posizione.setCap(rs.getString(CAP));

                        ristorante.setPosizione(posizione);
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero delle informazioni del ristorante", e);
        }

        return ristorante;
    }

    @Override
    public Ristorante selezionaRistorantePerProprietario(Persona ristoratore) throws EccezioneDAO {
        Ristorante ristorante = null;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(SELEZIONA_RISTORANTE_PER_PROPRIETARIO)) {

                ps.setString(1, ristoratore.getEmail());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ristorante = new Ristorante();
                        ristorante.setPartitaIVA(rs.getString(PARTITA_IVA));
                        ristorante.setNome(rs.getString(NOME));
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero del ristorante del ristoratore", e);
        }

        return ristorante;
    }

    @Override
    public List<Ristorante> selezionaTop4RistorantiPerMedia() throws EccezioneDAO {
        List<Ristorante> ristorantiTop = new ArrayList<>();


        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(SELEZIONA_TOP4_RISTORANTI_PER_MEDIA);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Ristorante ristorante = new Ristorante();
                    ristorante.setNome(rs.getString(NOME));

                    Posizione posizione= new Posizione();
                    posizione.setCitta(rs.getString(CITTA));
                    ristorante.setPosizione(posizione);

                    ristorante.setCucina(TipoCucina.tipoCucinaDaId(rs.getString(CUCINA)));

                    ristorante.setMediaStelle(BigDecimal.valueOf(rs.getFloat(MEDIA_STELLE)));

                    ristorantiTop.add(ristorante);
                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante il recupero dei top 4 ristoranti per media stelle", e);
        }

        return ristorantiTop;
    }


    @Override
    public Ristorante dettagliRistorante(Ristorante ristorante) throws EccezioneDAO {

        try {

            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(DETTAGLI_RISTORANTE)) {

                ps.setString(1, ristorante.getPartitaIVA());

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        Ristorante r = new Ristorante();
                        r.setTelefono(rs.getString(TELEFONO));

                        Posizione posizione = new Posizione();
                        posizione.indirizzoCompleto(
                                rs.getString(INDIRIZZO),
                                rs.getString(CIVICO)
                        );
                        posizione.setCap(rs.getString(CAP));
                        r.setPosizione(posizione);

                        GiorniEOrari giorniEOrari = new GiorniEOrari();

                        Time inizioPranzo = rs.getTime(INIZIO_PRANZO);
                        Time finePranzo   = rs.getTime(FINE_PRANZO);
                        Time inizioCena   = rs.getTime(INIZIO_CENA);
                        Time fineCena     = rs.getTime(FINE_CENA);

                        if (inizioPranzo != null) giorniEOrari.setInizioPranzo(inizioPranzo.toLocalTime());
                        if (finePranzo != null)   giorniEOrari.setFinePranzo(finePranzo.toLocalTime());
                        if (inizioCena != null)   giorniEOrari.setInizioCena(inizioCena.toLocalTime());
                        if (fineCena != null)     giorniEOrari.setFineCena(fineCena.toLocalTime());

                        r.setOrariApertura(giorniEOrari);

                        return r;
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO(
                    "Errore durante il recupero dei dettagli del ristorante",
                    e
            );
        }

        return null;
    }





}

