package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Ristorante;

import java.util.*;

public class AmbienteDAOMemoria implements AmbienteDAO {

    private final Map<Integer, Ambiente> ambienti = new HashMap<>();
    private int nextId = 1; // per generare id univoci in memoria


    @Override
    public void registraDisponibilita(List<Ambiente> listaAmbiente) throws EccezioneDAO {
        if (listaAmbiente == null || listaAmbiente.isEmpty()) return;

        for (Ambiente ambiente : listaAmbiente) {

            String nomeAmbiente = ambiente.categoriaAmbiente().name();

            String ristoranteId = ambiente.getRistorante();
            if (ristoranteId == null) {
                throw new EccezioneDAO("Ristorante non valorizzato per ambiente: " + nomeAmbiente);
            }

            if (ambiente.getIdAmbiente() == null) {
                ambiente.setIdAmbiente(nextId++);
            }


            ambienti.put(ambiente.getIdAmbiente(), ambiente);
        }
    }

    @Override
    public List<Ambiente> cercaAmbientiDelRistorante(Ristorante ristorante) throws EccezioneDAO {
        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Ristorante o partita IVA non valorizzata");
        }

        List<Ambiente> risultati = new ArrayList<>();

        for (Ambiente ambiente : ambienti.values()) {
            if (ristorante.getPartitaIVA().equals(ambiente.getRistorante())) {
                risultati.add(ambiente);
            }
        }

        return risultati;
    }


    @Override
    public Ambiente cercaExtraPerAmbiente(Ambiente ambiente) throws EccezioneDAO {
        if (ambiente == null || ambiente.getRistorante() == null || ambiente.categoriaAmbiente() == null) {
            throw new EccezioneDAO("Ambiente o ristorante non valorizzato");
        }

        for (Ambiente a : ambienti.values()) {
            if (ambiente.getRistorante().equals(a.getRistorante())
                    && ambiente.categoriaAmbiente() == a.categoriaAmbiente()) {

                Set<Extra> extra = a.getExtra() != null ? new HashSet<>(a.getExtra()) : new HashSet<>();

                Ambiente ambienteDaRitornare = new Ambiente();
                ambienteDaRitornare.setCategoria(a.categoriaAmbiente());
                ambienteDaRitornare.setRistorante(a.getRistorante());
                ambienteDaRitornare.setExtra(extra);

                return ambienteDaRitornare;
            }
        }

        return null;
    }

    @Override
    public Ambiente cercaNomeAmbienteERistorante(Ambiente ambiente) throws EccezioneDAO {
        if (ambiente == null || ambiente.getIdAmbiente() == null) {
            throw new EccezioneDAO("ID ambiente non valorizzato");
        }

        Ambiente trovato = ambienti.get(ambiente.getIdAmbiente());
        if (trovato != null) {
            Ambiente ambienteTrovato = new Ambiente();
            ambienteTrovato.setCategoria(trovato.categoriaAmbiente());
            ambienteTrovato.setRistorante(trovato.getRistorante());
            return ambienteTrovato;
        }

        return null;
    }

    @Override
    public Ambiente cercaIdAmbiente(Ambiente ambiente) throws EccezioneDAO {
        for (Ambiente a : ambienti.values()) {
            if (a.categoriaAmbiente().getId().equals(ambiente.categoriaAmbiente().getId()) &&
                    a.getRistorante().equals(ambiente.getRistorante())) {
                return a;
            }
        }

        throw new EccezioneDAO("Ambiente non trovato in memoria per tipo e ristorante.");
    }



}
