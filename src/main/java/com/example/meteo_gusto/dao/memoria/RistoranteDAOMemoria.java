package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.RistoranteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RistoranteDAOMemoria implements RistoranteDAO {

    private final Map<String, Ristorante> ristorantiInMemoriaMap = new HashMap<>();
    private static final String RISTORANTE_NON_TROVATO_IN_MEMORIA ="Ristorante non trovato in memoria: ";

    @Override
    public void registraRistorante(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o partita IVA non valorizzati");
            }

            ristorante.setMediaStelle(BigDecimal.ZERO);
            ristorantiInMemoriaMap.put(ristorante.getPartitaIVA(), ristorante);

        } catch (Exception e) {
            throw new EccezioneDAO(
                    "Errore durante la registrazione del ristorante in memoria: "
                            + (ristorante != null ? ristorante.getNome() : "null"),
                    e
            );
        }
    }

    @Override
    public List<Ristorante> filtraRistorantiPerCitta(Filtro filtro) throws EccezioneDAO {
        try {
            if (filtro == null || filtro.getCitta() == null) {
                throw new EccezioneDAO("Filtro o città non valorizzati");
            }

            return ristorantiInMemoriaMap.values().stream()
                    .filter(r -> r.posizioneRistorante() != null
                            && filtro.getCitta().equalsIgnoreCase(r.posizioneRistorante().getCitta()))
                    .toList();

        } catch (Exception e) {
            throw new EccezioneDAO(
                    "Errore durante la ricerca dei ristoranti filtrati per città in memoria",
                    e
            );
        }
    }

    @Override
    public Ristorante mediaStelleRistorante(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o partita IVA non valorizzata");
            }

            Ristorante trovato = ristorantiInMemoriaMap.get(ristorante.getPartitaIVA());
            if (trovato == null) {
                throw new EccezioneDAO(RISTORANTE_NON_TROVATO_IN_MEMORIA + ristorante.getPartitaIVA());
            }

            Ristorante mediaRistorante = new Ristorante(ristorante.getPartitaIVA());
            mediaRistorante.setMediaStelle(trovato.getMediaStelle());

            return mediaRistorante;

        } catch (Exception e) {
            throw new EccezioneDAO(
                    "Errore durante il recupero della media stelle del ristorante in memoria",
                    e
            );
        }
    }

    @Override
    public void aggiornaMediaStelle(Ristorante ristorante) throws EccezioneDAO {
        try {
            if (ristorante == null || ristorante.getPartitaIVA() == null) {
                throw new EccezioneDAO("Ristorante o partita IVA non valorizzata");
            }

            Ristorante trovato = ristorantiInMemoriaMap.get(ristorante.getPartitaIVA());
            if (trovato == null) {
                throw new EccezioneDAO(RISTORANTE_NON_TROVATO_IN_MEMORIA + ristorante.getPartitaIVA());
            }

            trovato.setMediaStelle(ristorante.getMediaStelle());

        } catch (Exception e) {
            throw new EccezioneDAO(
                    "Errore durante l'aggiornamento della media stelle del ristorante in memoria",
                    e
            );
        }
    }

    @Override
    public Ristorante selezionaInfoRistorante(Ambiente ambiente) throws EccezioneDAO {
        try {
            if (ambiente == null || ambiente.getRistorante() == null) {
                throw new EccezioneDAO("Ambiente o ristorante non valorizzato");
            }

            Ristorante ristoranteMemoria = ristorantiInMemoriaMap.get(ambiente.getRistorante());
            if (ristoranteMemoria == null) {
                throw new EccezioneDAO(RISTORANTE_NON_TROVATO_IN_MEMORIA + ambiente.getRistorante());
            }

            return creaRistoranteConPosizione(ristoranteMemoria);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero delle informazioni del ristorante in memoria", e);
        }
    }

    /** Crea un nuovo Ristorante a partire da uno in memoria, copiando solo nome e posizione */
    private Ristorante creaRistoranteConPosizione(Ristorante ristoranteMemoria) {
        Ristorante ristorante = new Ristorante();
        ristorante.setNome(ristoranteMemoria.getNome());

        Posizione posizione = new Posizione();
        posizione.indirizzoCompleto(
                ristoranteMemoria.posizioneRistorante().via(),
                ristoranteMemoria.posizioneRistorante().numeroCivico()
        );
        posizione.setCitta(ristoranteMemoria.posizioneRistorante().getCitta());
        posizione.setCap(ristoranteMemoria.posizioneRistorante().getCap());

        ristorante.setPosizione(posizione);
        return ristorante;
    }


    @Override
    public Ristorante selezionaRistorantePerProprietario(Persona ristoratore) throws EccezioneDAO {
        try {
            if (ristoratore == null || ristoratore.getEmail() == null) {
                throw new EccezioneDAO("Ristoratore non valorizzato");
            }


            return ristorantiInMemoriaMap.values().stream()
                    .filter(r -> r.getRistoratore() != null && r.getRistoratore().equals(ristoratore.getEmail()))
                    .findFirst()
                    .map(r -> {

                        Ristorante copia = new Ristorante();
                        copia.setPartitaIVA(r.getPartitaIVA());
                        copia.setNome(r.getNome());
                        return copia;
                    })
                    .orElse(null);

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero del ristorante del ristoratore in memoria", e);
        }
    }


    @Override
    public List<Ristorante> selezionaTop4RistorantiPerMedia() throws EccezioneDAO {
        try {
            return ristorantiInMemoriaMap.values().stream()
                    .filter(r -> r.getMediaStelle() != null)
                    .sorted((r1, r2) -> r2.getMediaStelle().compareTo(r1.getMediaStelle()))
                    .limit(4)
                    .map(r -> {

                        Ristorante copia = new Ristorante();
                        copia.setNome(r.getNome());

                        Posizione posizione = new Posizione();
                        if (r.posizioneRistorante() != null) {
                            posizione.setCitta(r.posizioneRistorante().getCitta());
                        }
                        copia.setPosizione(posizione);

                        copia.setCucina(r.getCucina());
                        copia.setMediaStelle(r.getMediaStelle());

                        return copia;
                    })
                    .toList();

        } catch (Exception e) {
            throw new EccezioneDAO("Errore durante il recupero dei top 4 ristoranti per media stelle in memoria", e);
        }
    }

    @Override
    public Ristorante dettagliRistorante(Ristorante ristorante) throws EccezioneDAO {

        if (ristorante == null || ristorante.getPartitaIVA() == null) {
            throw new EccezioneDAO("Partita IVA non valida");
        }

        Ristorante r = ristorantiInMemoriaMap.get(ristorante.getPartitaIVA());

        if (r == null) {
            throw new EccezioneDAO(
                    RISTORANTE_NON_TROVATO_IN_MEMORIA + ristorante.getPartitaIVA()
            );
        }

        return r;
    }


}
