package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.model.Ristorante;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class DietaDAOMemoria implements DietaDAO {

    private final Map<String, Set<TipoDieta>> dieteRistoranti = new HashMap<>();

    @Override
    public void registraDieta(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
            }
            System.out.println("Sono dentro il metodo della memoria");

            if (ristorante.getTipoDieta() == null) {
                ristorante.setTipoDieta(new HashSet<>());
            }

            Set<TipoDieta> diete = ristorante.getTipoDieta();

            if (diete.isEmpty()) {
                return;
            }

            dieteRistoranti.putIfAbsent(ristorante.getPartitaIVA(), new HashSet<>());
            dieteRistoranti.get(ristorante.getPartitaIVA()).addAll(diete);

            // Dopo aver aggiornato la mappa
            Set<TipoDieta> dieteRegistrate = dieteRistoranti.get(ristorante.getPartitaIVA());
            System.out.println("Diete registrate per il ristorante " + ristorante.getPartitaIVA() + ": " + dieteRegistrate);


        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la registrazione delle diete in memoria per il ristorante: "
                    + (ristorante != null ? ristorante.getPartitaIVA() : "null"), e);
        }
    }



    @Override
    public Ristorante controllaDieteDelRistorante(Ristorante ristoranteDaControllare) throws EccezioneDAO {
        try {
            if (ristoranteDaControllare == null ||
                    ristoranteDaControllare.getPartitaIVA() == null ||
                    ristoranteDaControllare.getTipoDieta() == null ||
                    ristoranteDaControllare.getTipoDieta().isEmpty()) {
                return null;
            }

            Set<TipoDieta> dieteRegistrate = dieteRistoranti.get(ristoranteDaControllare.getPartitaIVA());
            if (dieteRegistrate == null || dieteRegistrate.isEmpty()) {
                return null;
            }

            Set<TipoDieta> dieteValide = ristoranteDaControllare.getTipoDieta().stream()
                    .filter(dieteRegistrate::contains)
                    .collect(Collectors.toSet());

            if (dieteValide.isEmpty()) {
                return null;
            }

            Ristorante ristoranteValido = new Ristorante();
            ristoranteValido.setPartitaIVA(ristoranteDaControllare.getPartitaIVA());
            ristoranteValido.setTipoDieta(dieteValide);

            return ristoranteValido;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la verifica delle diete del ristorante in memoria", e);
        }
    }

}
