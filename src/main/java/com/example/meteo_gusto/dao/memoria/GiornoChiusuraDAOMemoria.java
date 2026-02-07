package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.model.GiorniEOrari;
import com.example.meteo_gusto.model.Ristorante;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GiornoChiusuraDAOMemoria implements GiornoChiusuraDAO {

    private final Map<String, Set<GiorniSettimana>> giorniChiusuraMap = new HashMap<>();


    @Override
    public void registraGiorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
            }

            Set<GiorniSettimana> giorni = ristorante.aperturaRistorante().giorniChiusura();
            if (giorni == null || giorni.isEmpty()) {
                return;
            }

            giorniChiusuraMap.put(ristorante.getPartitaIVA(), new HashSet<>(giorni));

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la registrazione dei giorni di chiusura in memoria", e);
        }
    }

    @Override
    public GiorniEOrari giorniChiusuraRistorante(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o Partita IVA non valorizzata");
            }

            GiorniEOrari ristoranteChiuso = new GiorniEOrari();
            Set<GiorniSettimana> giorniChiusura = giorniChiusuraMap.getOrDefault(
                    ristorante.getPartitaIVA(), new HashSet<>());

            ristoranteChiuso.setGiorniChiusura(new HashSet<>(giorniChiusura));
            return ristoranteChiuso;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero dei giorni di chiusura in memoria", e);
        }
    }

}
