package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.PrenotazioneDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Prenotazione;

import java.util.*;
import java.util.stream.Collectors;

public class PrenotazioneDAOMemoria implements PrenotazioneDAO {

    private final Map<Integer, List<Prenotazione>> prenotazioniPerAmbienteMap = new HashMap<>();
    private final Map<String, Integer> notificheUtenteMap = new HashMap<>();
    private final Map<Integer, Integer> notificheRistoratoreMap = new HashMap<>();

    private static final String UTENTE_NON_VALORIZZATO="Utente non valorizzato";


    @Override
    public Prenotazione postiOccupatiPerDataEFasciaOraria(Prenotazione prenotazione) throws EccezioneDAO {
        try {
            if (prenotazione == null || prenotazione.getAmbiente() == null || prenotazione.getAmbiente().getIdAmbiente() == null
                    || prenotazione.getData() == null || prenotazione.getFasciaOraria() == null) {
                throw new EccezioneDAO("Prenotazione o dati necessari non valorizzati");
            }

            Prenotazione prenotazioneTrovata = new Prenotazione();
            List<Prenotazione> prenotazioni = prenotazioniPerAmbienteMap.get(prenotazione.getAmbiente().getIdAmbiente());

            int totalePersone = 0;
            if (prenotazioni != null) {
                for (Prenotazione p : prenotazioni) {
                    if (p.getData().equals(prenotazione.getData())
                            && p.getFasciaOraria().equals(prenotazione.getFasciaOraria())) {
                        totalePersone += p.getNumeroPersone() != null ? p.getNumeroPersone() : 0;
                    }
                }
            }

            prenotazioneTrovata.setNumeroPersone(totalePersone);
            return prenotazioneTrovata;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il controllo disponibilità posti in memoria", e);
        }
    }

    @Override
    public boolean inserisciPrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        try {
            validaPrenotazione(prenotazione);

            List<Prenotazione> listaPrenotazioni = prenotazioniPerAmbienteMap
                    .computeIfAbsent(prenotazione.getAmbiente().getIdAmbiente(), k -> new ArrayList<>());

            listaPrenotazioni.add(prenotazione);

            notificheUtenteMap.merge(prenotazione.getUtente().getEmail(), 1, Integer::sum);
            notificheRistoratoreMap.merge(prenotazione.getAmbiente().getIdAmbiente(), 1, Integer::sum);

            return true;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante l'inserimento della prenotazione in memoria", e);
        }
    }


    @Override
    public boolean esistePrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        try {
            validaPrenotazione(prenotazione);

            List<Prenotazione> listaPrenotazioni = prenotazioniPerAmbienteMap
                    .get(prenotazione.getAmbiente().getIdAmbiente());

            if (listaPrenotazioni == null || listaPrenotazioni.isEmpty()) {
                return false;
            }

            return listaPrenotazioni.stream()
                    .anyMatch(p -> p.getUtente().getEmail().equals(prenotazione.getUtente().getEmail())
                            && p.getData().equals(prenotazione.getData())
                            && p.getFasciaOraria().equals(prenotazione.getFasciaOraria()));

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la verifica della prenotazione in memoria", e);
        }
    }

    private void validaPrenotazione(Prenotazione prenotazione) throws EccezioneDAO {
        if (prenotazione == null
                || prenotazione.getAmbiente() == null
                || prenotazione.getAmbiente().getIdAmbiente() == null
                || prenotazione.getUtente() == null
                || prenotazione.getUtente().getEmail() == null
                || prenotazione.getData() == null
                || prenotazione.getFasciaOraria() == null) {
            throw new EccezioneDAO("Dati della prenotazione non valorizzati correttamente");
        }
    }



    @Override
    public Prenotazione contaNotificheAttiveUtente(Persona utente) throws EccezioneDAO {
        try {
            if (utente == null || utente.getEmail() == null) {
                throw new EccezioneDAO(UTENTE_NON_VALORIZZATO);
            }

            int conteggio = notificheUtenteMap.getOrDefault(utente.getEmail(), 0);

            Prenotazione risultato = new Prenotazione();
            risultato.setNumeroNotifiche(conteggio);
            return risultato;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche utente", e);
        }
    }


    @Override
    public void resettaNotificheUtente(Persona utente) throws EccezioneDAO {
        try {
            if (utente == null || utente.getEmail() == null) {
                throw new EccezioneDAO(UTENTE_NON_VALORIZZATO);
            }

            notificheUtenteMap.put(utente.getEmail(), 0);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il reset delle notifiche utente in memoria", e);
        }
    }


    @Override
    public List<Prenotazione> selezionaPrenotazioniUtente(Persona utente) throws EccezioneDAO {
        try {
            if (utente == null || utente.getEmail() == null) {
                throw new EccezioneDAO(UTENTE_NON_VALORIZZATO);
            }

            return prenotazioniPerAmbienteMap.values().stream()
                    .flatMap(List::stream)
                    .filter(p -> p.getUtente() != null && utente.getEmail().equals(p.getUtente().getEmail()))
                    .toList();

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni dell'utente in memoria", e);
        }
    }


    @Override
    public List<Prenotazione> selezionaPrenotazioniRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        try {
            if (ambienti == null || ambienti.isEmpty()) {
                return List.of();
            }

            Set<Integer> idAmbienti = ambienti.stream()
                    .filter(a -> a != null && a.getIdAmbiente() != null)
                    .map(Ambiente::getIdAmbiente)
                    .collect(Collectors.toSet());

            return prenotazioniPerAmbienteMap.entrySet().stream()
                    .filter(e -> idAmbienti.contains(e.getKey()))
                    .flatMap(e -> e.getValue().stream())
                    .toList();

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero delle prenotazioni per più ambienti in memoria", e);
        }
    }


    @Override
    public Prenotazione contaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        try {
            Prenotazione risultato = new Prenotazione();

            if (ambienti == null || ambienti.isEmpty()) {
                risultato.setNumeroNotifiche(0);
                return risultato;
            }

            int conteggio = ambienti.stream()
                    .filter(a -> a != null && a.getIdAmbiente() != null)
                    .mapToInt(a -> notificheRistoratoreMap.getOrDefault(a.getIdAmbiente(), 0))
                    .sum();

            risultato.setNumeroNotifiche(conteggio);
            return risultato;

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il conteggio delle notifiche per il ristoratore in memoria", e);
        }
    }

    @Override
    public void resettaNotificheRistoratore(List<Ambiente> ambienti) throws EccezioneDAO {
        try {
            if (ambienti == null || ambienti.isEmpty()) {
                return;
            }

            for (Ambiente a : ambienti) {
                if (a != null && a.getIdAmbiente() != null) {
                    notificheRistoratoreMap.put(a.getIdAmbiente(), 0);
                }
            }

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante la reimpostazione delle notifiche del ristoratore in memoria", e);
        }
    }


}
