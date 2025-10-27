package com.example.meteo_gusto.dao.mysql;

import com.example.meteo_gusto.dao.DisponibilitaDAO;
import com.example.meteo_gusto.dao.mysql.query_sql.QuerySQLDisponibilitaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.FasciaOraria;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoAmbienteConExtra;
import com.example.meteo_gusto.model.AmbienteDisponibile;
import com.example.meteo_gusto.model.AmbienteSpecialeDisponibile;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DisponibilitaDAOMySql extends QuerySQLDisponibilitaDAO implements DisponibilitaDAO {
    @Override
    public void registraDisponibilita(AmbienteDisponibile ambienteDisponibile) throws EccezioneDAO {
        if (ambienteDisponibile == null) return;

        String partitaIva = ambienteDisponibile.getRistorante().getPartitaIVA();

        try {
            GestoreConnessioneDB gestoreConn = new GestoreConnessioneDB();
            try (Connection conn = gestoreConn.creaConnessione();
                 PreparedStatement ps = conn.prepareStatement(REGISTRA_DISPONIBILITA)) {

                if (ambienteDisponibile.getAmbienteDisponibile() != null) {
                    for (Map.Entry<TipoAmbiente, Integer> entry : ambienteDisponibile.getAmbienteDisponibile().entrySet()) {
                        preparaRecord(ps, entry.getKey(), partitaIva, entry.getValue());
                    }
                }

                AmbienteSpecialeDisponibile ambienteSpeciale = ambienteDisponibile.getAmbienteSpecialeDisponibile();
                if (ambienteSpeciale != null) {

                    boolean riscaldamento = ambienteSpeciale.getExtra().contains(Extra.RISCALDAMENTO);
                    boolean raffreddamento = ambienteSpeciale.getExtra().contains(Extra.RAFFREDDAMENTO);
                    preparaRecord(ps, ambienteSpeciale.getTipoAmbienteConExtra(), partitaIva,
                            ambienteSpeciale.getNumeroCoperti(), riscaldamento, raffreddamento);
                }

                int[] risultati = ps.executeBatch();
                if (risultati.length == 0) {
                    throw new EccezioneDAO("Nessuna disponibilità inserita.");
                }
            }

        } catch (SQLException | IOException e) {
            throw new EccezioneDAO("Errore durante la registrazione della disponibilità.", e);
        }
    }

    private void preparaRecord(PreparedStatement ps, TipoAmbiente tipoAmbiente, String partitaIva, int numeroCoperti) throws SQLException {
        preparaRecordInterno(ps, tipoAmbiente.getId(), partitaIva, numeroCoperti, false, false);
    }

    private void preparaRecord(PreparedStatement ps, TipoAmbienteConExtra tipoAmbienteConExtra, String partitaIva, int numeroCoperti, boolean riscaldamento, boolean raffreddamento) throws SQLException {
        preparaRecordInterno(ps, tipoAmbienteConExtra.getId(), partitaIva, numeroCoperti, riscaldamento, raffreddamento);
    }

    private void preparaRecordInterno(PreparedStatement ps, String nomeAmbiente, String partitaIva, int numeroCoperti, boolean riscaldamento, boolean raffreddamento) throws SQLException {
        ps.setString(1, nomeAmbiente);
        ps.setString(3, partitaIva);
        ps.setBoolean(4, riscaldamento);
        ps.setBoolean(5, raffreddamento);
        ps.setInt(6, numeroCoperti);

        for (FasciaOraria fascia : FasciaOraria.values()) {
            ps.setString(2, fascia.name());
            ps.addBatch();
        }
    }


}