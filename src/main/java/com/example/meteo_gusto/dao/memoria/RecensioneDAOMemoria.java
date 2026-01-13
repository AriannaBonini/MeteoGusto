package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Recensione;
import com.example.meteo_gusto.model.Ristorante;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecensioneDAOMemoria implements RecensioneDAO {


    private final Map<String, List<Recensione>> recensioniPerRistoranteMap = new HashMap<>();
    @Override
    public void nuovaRecensione(Recensione recensione) throws EccezioneDAO {
        try {
            if (recensione == null || recensione.getRistorante() == null) {
                throw new EccezioneDAO("Recensione o ristorante non valorizzato");
            }


            recensioniPerRistoranteMap
                    .computeIfAbsent(recensione.getRistorante(), k -> new ArrayList<>())
                    .add(recensione);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante l'inserimento della recensione in memoria", e);
        }
    }

    @Override
    public boolean esisteRecensione(Recensione recensione) throws EccezioneDAO {
        try {
            controllaRecensioneValida(recensione);

            List<Recensione> recensioni = recensioniPerRistoranteMap.get(recensione.getRistorante());
            if (recensioni == null || recensioni.isEmpty()) {
                return false;
            }

            return recensioni.stream()
                    .anyMatch(r -> r.getUtente().equals(recensione.getUtente()));

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la verifica dell'esistenza della recensione in memoria", e);
        }
    }

    @Override
    public void aggiornaRecensione(Recensione recensione) throws EccezioneDAO {
        try {
            controllaRecensioneValida(recensione);

            List<Recensione> recensioni = recensioniPerRistoranteMap.get(recensione.getRistorante());
            if (recensioni != null) {
                for (Recensione r : recensioni) {
                    if (r.getUtente().equals(recensione.getUtente())) {
                        r.aggiungiStelle(recensione.getStelle());
                        r.setData(recensione.getData());
                        return;
                    }
                }
            }

            throw new EccezioneDAO("Recensione da aggiornare non trovata in memoria");

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante l'aggiornamento della recensione in memoria", e);
        }
    }

    private void controllaRecensioneValida(Recensione recensione) throws EccezioneDAO {
        if (recensione == null || recensione.getRistorante() == null || recensione.getUtente() == null) {
            throw new EccezioneDAO("Recensione, ristorante o utente non valorizzato");
        }
    }



    @Override
    public Ristorante calcolaNuovaMedia(Recensione recensione) throws EccezioneDAO {
        Ristorante ristoranteConMedia = new Ristorante();

        try {
            if (recensione == null || recensione.getRistorante() == null) {
                throw new EccezioneDAO("Recensione o ristorante non valorizzato");
            }

            String pIva = recensione.getRistorante();
            List<Recensione> recensioni = recensioniPerRistoranteMap.get(pIva);

            if (recensioni == null || recensioni.isEmpty()) {
                ristoranteConMedia.setPartitaIVA(pIva);
                ristoranteConMedia.setMediaStelle(BigDecimal.ZERO);
                return ristoranteConMedia;
            }

            BigDecimal somma = BigDecimal.ZERO;
            for (Recensione r : recensioni) {
                somma = somma.add(r.getStelle());
            }

            BigDecimal media = somma.divide(BigDecimal.valueOf(recensioni.size()), 2, RoundingMode.HALF_UP);

            ristoranteConMedia.setPartitaIVA(pIva);
            ristoranteConMedia.setMediaStelle(media);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il calcolo della nuova media stelle in memoria", e);
        }

        return ristoranteConMedia;
    }

}
