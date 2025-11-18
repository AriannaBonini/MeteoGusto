package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLAmbienteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Ristorante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AmbienteDAOMySql extends QuerySQLAmbienteDAO implements AmbienteDAO {


    @Override
    public void registraDisponibilita(List<Ambiente> listaAmbiente) throws EccezioneDAO {
        if (listaAmbiente == null || listaAmbiente.isEmpty()) return;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DISPONIBILITA)) {

                for (Ambiente ambiente : listaAmbiente) {

                    String nomeAmbiente = ambiente.getTipoAmbiente().getId();
                    int numeroCoperti = ambiente.getNumeroCoperti() != null ? ambiente.getNumeroCoperti() : 0;

                    boolean riscaldamento = ambiente.getTipoAmbiente() == TipoAmbiente.ESTERNO_COPERTO
                            && ambiente.getExtra() != null
                            && ambiente.getExtra().contains(Extra.RISCALDAMENTO);
                    boolean raffreddamento = ambiente.getTipoAmbiente() == TipoAmbiente.ESTERNO_COPERTO
                            && ambiente.getExtra() != null
                            && ambiente.getExtra().contains(Extra.RAFFREDDAMENTO);

                    String ristoranteId = ambiente.getRistorante() != null ? ambiente.getRistorante() : null;
                    if (ristoranteId == null) {
                        throw new EccezioneDAO("Ristorante non valorizzato per ambiente: " + nomeAmbiente);
                    }

                    ps.setString(1, nomeAmbiente);
                    ps.setBoolean(2, riscaldamento);
                    ps.setBoolean(3, raffreddamento);
                    ps.setString(4, ristoranteId);
                    ps.setInt(5, numeroCoperti);

                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione della disponibilità.", e);
        }
    }

    @Override
    public List<Ambiente> cercaAmbientiDelRistorante(Ristorante ristorante) throws EccezioneDAO {
        List<Ambiente> risultati = new ArrayList<>();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CERCA_AMBIENTI)) {

                ps.setString(1, ristorante.getPartitaIVA());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int idAmbiente = rs.getInt(ID_AMBIENTE);
                        int numeroCoperti = rs.getInt(NUMERO_COPERTI);
                        TipoAmbiente tipoAmbiente= TipoAmbiente.tipoAmbienteDaId(rs.getString(AMBIENTE));

                        Ambiente ambiente = new Ambiente();
                        ambiente.setTipoAmbiente(tipoAmbiente);
                        ambiente.setNumeroCoperti(numeroCoperti);
                        ambiente.setIdAmbiente(idAmbiente);


                        risultati.add(ambiente);
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO(
                    "Errore durante la ricerca degli ambienti per il ristorante: " + ristorante.getPartitaIVA(), e);
        }

        return risultati;
    }


    @Override
    public Ambiente cercaExtraPerAmbiente(Ambiente ambiente) throws EccezioneDAO {
        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CERCA_EXTRA_PER_AMBIENTE)) {

                ps.setString(1, ambiente.getRistorante());
                ps.setString(2, ambiente.getTipoAmbiente().toString());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Set<Extra> extra = new HashSet<>();

                        boolean riscaldamento = rs.getBoolean(RISCALDAMENTO);
                        boolean raffreddamento = rs.getBoolean(RAFFREDDAMENTO);

                        if (riscaldamento) {
                            extra.add(Extra.RISCALDAMENTO);
                        }
                        if (raffreddamento) {
                            extra.add(Extra.RAFFREDDAMENTO);
                        }

                        Ambiente ambienteDaRitornare= new Ambiente();
                        ambienteDaRitornare.setTipoAmbiente(ambiente.getTipoAmbiente());
                        ambienteDaRitornare.setRistorante(ambiente.getRistorante());
                        ambienteDaRitornare.setExtra(extra);

                        return ambienteDaRitornare;
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO(
                    "Errore durante la ricerca degli extra per l’ambiente " + ambiente.getTipoAmbiente() +
                            " del ristorante: " + ambiente.getRistorante(), e);
        }

        return null;
    }

    @Override
    public Ambiente cercaNomeAmbienteERistorante(Ambiente ambiente) throws EccezioneDAO {
        Ambiente ambienteTrovato= new Ambiente();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();

            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(CERCA_NOME_AMBIENTE_E_RISTORANTE_PER_ID)) {

                ps.setInt(1, ambiente.getIdAmbiente());


                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String tipo = rs.getString(AMBIENTE);
                        if(tipo != null) {
                            ambienteTrovato.setTipoAmbiente(TipoAmbiente.tipoAmbienteDaId(tipo));
                            ambienteTrovato.setRistorante(rs.getString(RISTORANTE));
                        }
                    }

                }

            }
        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la ricerca del nome dell'ambiente per ID", e);
        }

        return ambienteTrovato;
    }







}