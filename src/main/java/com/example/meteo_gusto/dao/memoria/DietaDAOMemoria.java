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


            if (ristorante.dieteOfferte() == null) {
                ristorante.setDiete(new HashSet<>());
            }

            Set<TipoDieta> diete = ristorante.dieteOfferte();

            if (diete.isEmpty()) {
                return;
            }

            dieteRistoranti.putIfAbsent(ristorante.getPartitaIVA(), new HashSet<>());
            dieteRistoranti.get(ristorante.getPartitaIVA()).addAll(diete);


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
                    ristoranteDaControllare.dieteOfferte() == null ||
                    ristoranteDaControllare.dieteOfferte().isEmpty()) {
                return null;
            }

            Set<TipoDieta> dieteRegistrate = dieteRistoranti.get(ristoranteDaControllare.getPartitaIVA());
            if (dieteRegistrate == null || dieteRegistrate.isEmpty()) {
                return null;
            }

            Set<TipoDieta> dieteValide = ristoranteDaControllare.dieteOfferte().stream()
                    .filter(dieteRegistrate::contains)
                    .collect(Collectors.toSet());

            if (dieteValide.isEmpty()) {
                return null;
            }

            Ristorante ristoranteValido = new Ristorante();
            ristoranteValido.setPartitaIVA(ristoranteDaControllare.getPartitaIVA());
            ristoranteValido.setDiete(dieteValide);

            return ristoranteValido;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la verifica delle diete del ristorante in memoria", e);
        }
    }

}
