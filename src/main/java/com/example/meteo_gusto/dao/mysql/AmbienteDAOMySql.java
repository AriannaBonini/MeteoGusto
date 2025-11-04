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
import java.util.List;

public class AmbienteDAOMySql extends QuerySQLAmbienteDAO implements AmbienteDAO {


    @Override
    public void registraDisponibilita(List<Ambiente> listaAmbiente) throws EccezioneDAO {
        if (listaAmbiente == null || listaAmbiente.isEmpty()) return;

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DISPONIBILITA)) {

                for (Ambiente ambiente : listaAmbiente) {

                    String nomeAmbiente = ambiente.getTipoAmbiente().name();
                    int numeroCoperti = ambiente.getNumeroCoperti() != null ? ambiente.getNumeroCoperti() : 0;

                    boolean riscaldamento = ambiente.getTipoAmbiente() == TipoAmbiente.ESTERNO_COPERTO
                            && ambiente.getExtra() != null
                            && ambiente.getExtra().contains(Extra.RISCALDAMENTO);
                    boolean raffreddamento = ambiente.getTipoAmbiente() == TipoAmbiente.ESTERNO_COPERTO
                            && ambiente.getExtra() != null
                            && ambiente.getExtra().contains(Extra.RAFFREDDAMENTO);

                    String ristoranteId = ambiente.getRistorante() != null ? ambiente.getRistorante().getPartitaIVA() : null;
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

                        Ambiente ambiente = new Ambiente(tipoAmbiente,
                                null,
                                numeroCoperti,
                                null);

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




}