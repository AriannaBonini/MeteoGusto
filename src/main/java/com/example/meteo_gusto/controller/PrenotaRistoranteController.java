package com.example.meteo_gusto.controller;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrenotazioneEsistenteException;
import com.example.meteo_gusto.eccezione.PrevisioniMeteoFuoriRangeException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.*;
import com.example.meteo_gusto.mockapi.BoundaryMeteoMockAPI;
import com.example.meteo_gusto.model.*;
import com.example.meteo_gusto.patterns.facade.DAOFactoryFacade;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.GiorniSettimanaHelper;
import com.example.meteo_gusto.utilities.convertitore.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PrenotaRistoranteController {

    private final DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
    private final DietaDAO dietaDAO= daoFactoryFacade.getDietaDAO();
    private final RistoranteDAO ristoranteDAO = daoFactoryFacade.getRistoranteDAO();
    private final List<Ristorante> ristorantiPrenotabili = new ArrayList<>();
    private List<Ambiente> ambientiCompatibiliConIlMeteo=new ArrayList<>();
    private final AmbienteDAO ambienteDAO = daoFactoryFacade.getAmbienteDAO();
    private final PrenotazioneDAO prenotazioneDAO= daoFactoryFacade.getPrenotazioneDAO();

    /**
     * Cerca i ristoranti disponibili secondo i dati della prenotazione inseriti dall'utente ed applica le previsioni meteo
     */
    public List<RistoranteBean> cercaRistorantiDisponibili(FiltriBean filtriInseriti, MeteoBean meteoBean) throws EccezioneDAO, ValidazioneException {
        List<RistoranteBean> listaRistorantiPrenotabili = new ArrayList<>();
        ristorantiPrenotabili.clear();

        GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();

        Filtro filtri = ConvertitoreFiltri.datiPrenotazioneInModel(filtriInseriti);

        GiorniSettimana giornoPrenotazione = GiorniSettimanaHelper.dataInGiornoSettimana(filtriInseriti.getData());

        for (Ristorante ristorante : ristoranteDAO.filtraRistorantiPerCitta(filtri)) {

            List<Ambiente> ambientiRistoranteDisponibili= new ArrayList<>();

            if (!orarioDiPrenotazioneValido(ristorante, filtriInseriti.getOra()) ||
                    !giornoDiPrenotazioneValido(giornoChiusuraDAO.giorniChiusuraRistorante(ristorante), giornoPrenotazione)) {
                continue;
            }


            for (Ambiente ambiente : ambienteDAO.cercaAmbientiDelRistorante(ristorante)) {

                ambiente.setRistorante(ristorante.getPartitaIVA());

                Prenotazione prenotazione = new Prenotazione(
                        filtriInseriti.getData(),
                        filtriInseriti.getOra(),
                        filtriInseriti.getNumeroPersone(),
                        ambiente,
                        null,
                        trovaFasciaOraria(filtriInseriti.getOra())
                );

                int postiDisponibili = ambiente.numeroCoperti() -
                        prenotazioneDAO.postiOccupatiPerDataEFasciaOraria(prenotazione).numeroPersone();


                if (postiDisponibili >= filtriInseriti.getNumeroPersone()) {
                    ambientiRistoranteDisponibili.add(ambiente);

                }
            }
            if(!ambientiRistoranteDisponibili.isEmpty()) {
                ambientiRistoranteDisponibili= controllaAmbientiCompatibiliMeteo(ambientiRistoranteDisponibili,meteoBean);
                if(!ambientiRistoranteDisponibili.isEmpty()) {
                    ristorante.setAmbienti(ambientiRistoranteDisponibili);
                    ristorantiPrenotabili.add(ristorante);
                    listaRistorantiPrenotabili.add(ConvertitoreRistorante.cardRistoranteInBean(ristorante));
                }
            }
        }


        return listaRistorantiPrenotabili;
    }

    public void validaDati(FiltriBean filtri) throws ValidazioneException, PrevisioniMeteoFuoriRangeException {
        LocalDate oggi = LocalDate.now();
        LocalDate maxData = oggi.plusDays(15);

        if (filtri.getData().isBefore(oggi) || filtri.getData().isAfter(maxData)) {
            throw new PrevisioniMeteoFuoriRangeException("Il meteo non è disponibile per prenotazioni oltre i 15 giorni dalla data odierna.");
        }

        if (filtri.getData().isEqual(oggi) && filtri.getOra().isBefore(LocalTime.now())) {
            throw new ValidazioneException("L'orario non può essere nel passato per la data odierna.");
        }

        int p = filtri.getNumeroPersone();
        if (p < 1 || p > 30) {
            throw new ValidazioneException("Il numero di persone deve essere tra 1 e 30.");
        }
    }



    /**
     * Controlla se l'orario di prenotazione è valido rispetto agli orari di apertura del ristorante
     */
    private boolean orarioDiPrenotazioneValido(Ristorante ristorante, LocalTime oraPrenotazione) {

        boolean pranzoValido = ristorante.orariApertura().getInizioPranzo() != null &&
                ristorante.orariApertura().getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.orariApertura().getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.orariApertura().getFinePranzo());
        boolean cenaValida = ristorante.orariApertura().getInizioCena() != null &&

                ristorante.orariApertura().getFineCena() != null &&
                !oraPrenotazione.isBefore(ristorante.orariApertura().getInizioCena()) && //
                !oraPrenotazione.isAfter(ristorante.orariApertura().getFineCena());

        return pranzoValido || cenaValida;
    }

    /**
     * Controlla se il giorno della prenotazione è valido rispetto ai giorni di chiusura del ristorante
     */
    private boolean giornoDiPrenotazioneValido(GiorniEOrari giorniEOrari, GiorniSettimana giornoPrenotazione) {
        return !giorniEOrari.giorniChiusura().contains(giornoPrenotazione);
    }


    /**
     * Determina la fascia oraria della prenotazione in base all'orario
     */
    private FasciaOraria trovaFasciaOraria(LocalTime oraPrenotazione) {
        if (oraPrenotazione.isBefore(LocalTime.of(18,0))) {
            return FasciaOraria.PRANZO;
        }

        return FasciaOraria.CENA;
    }


    public List<RistoranteBean> filtraRistorantiDisponibili(FiltriBean filtriBeanInseriti) throws EccezioneDAO, ValidazioneException {
        if(filtriBeanInseriti==null) {
            return ConvertitoreRistorante.cardRistorantiInBean(ristorantiPrenotabili);
        }

        List<RistoranteBean> listaRistorantiFiltrati = new ArrayList<>();
        Filtro filtriRistorante = ConvertitoreFiltri.filtriCucinaEPrezzoInModel(filtriBeanInseriti);

        for (Ristorante ristorante : ristorantiPrenotabili) {
            if (rispettaFiltri(ristorante, filtriRistorante)) {

                RistoranteBean ristoranteBean= ConvertitoreRistorante.cardRistoranteInBean(ristorante);
                listaRistorantiFiltrati.add(ristoranteBean);

            }
        }

        return listaRistorantiFiltrati;
    }

    private void verificaDataMeteo(FiltriBean filtriBean) throws PrevisioniMeteoFuoriRangeException {
        LocalDate dataPrenotazione = filtriBean.getData();
        LocalDate oggi = LocalDate.now();

        long giorniDiDifferenza = ChronoUnit.DAYS.between(oggi, dataPrenotazione);

        if (giorniDiDifferenza > 15) {
            throw new PrevisioniMeteoFuoriRangeException(
                    "Il meteo non è disponibile per prenotazioni oltre i 15 giorni dalla data odierna."
            );
        }
    }



    private List<Ambiente> controllaAmbientiCompatibiliMeteo(List<Ambiente> ambientiDisponibiliRistorante, MeteoBean meteoBean) throws EccezioneDAO {
        if(ambientiCompatibiliConIlMeteo==null || ambientiCompatibiliConIlMeteo.isEmpty()) {
            ambientiCompatibiliConIlMeteo= generaAmbientiCompatibiliDaMeteo(meteoBean);
        }

        return ristoranteCompatibile(ambientiDisponibiliRistorante);
    }

    private boolean rispettaFiltri(Ristorante ristorante, Filtro filtri) throws EccezioneDAO {

        if (filtri.getFasciaPrezzo() != null &&
                !filtri.getFasciaPrezzo().equals(ristorante.fasciaPrezzoRistorante())) {
            return false;
        }


        if (filtri.getCucine() != null &&
                !filtri.getCucine().isEmpty() &&
                !filtri.getCucine().contains(ristorante.getCucina())) {
            return false;
        }



        if (filtri.getDiete() != null && !filtri.getDiete().isEmpty()) {

            Ristorante ristoranteDaControllare= new Ristorante(ristorante.getPartitaIVA());
            ristoranteDaControllare.setDiete(filtri.getDiete());
            Ristorante dieteRistoranteCompatibili = dietaDAO.controllaDieteDelRistorante(ristoranteDaControllare);

            if (dieteRistoranteCompatibili==null || dieteRistoranteCompatibili.getDiete() == null || dieteRistoranteCompatibili.getDiete().isEmpty()) {
                return false;
            }

            ristorante.setDiete(dieteRistoranteCompatibili.getDiete());

        }

        return true;
    }

    private List<Ambiente> ristoranteCompatibile(List<Ambiente> ambientiDisponibiliRistorante) throws EccezioneDAO {
        List<Ambiente> ambientiCompatibili = new ArrayList<>();


        for (Ambiente ambienteRistorante : ambientiDisponibiliRistorante) {
            if (isAmbienteCompatibile(ambienteRistorante)) {

                ambientiCompatibili.add(ambienteRistorante);
            }
        }
        return ambientiCompatibili;
    }

    private boolean isAmbienteCompatibile(Ambiente ambienteRistorante) throws EccezioneDAO {
        TipoAmbiente tipo = ambienteRistorante.categoriaAmbiente();

        Ambiente ambienteCompatibile = ambientiCompatibiliConIlMeteo.stream()
                .filter(a -> a.categoriaAmbiente() == tipo)
                .findFirst()
                .orElse(null);

        if (ambienteCompatibile == null) return false;

        Set<Extra> extraRichiesti = ambienteCompatibile.getExtra();
        if (extraRichiesti == null || extraRichiesti.isEmpty()) {
            return true;
        }

        Ambiente ambienteConExtra = ambienteDAO.cercaExtraPerAmbiente(ambienteRistorante);

        Set<Extra> extraDisponibili = ambienteConExtra != null ? ambienteConExtra.getExtra() : null;

        return extraDisponibili != null && extraDisponibili.containsAll(extraRichiesti);
    }




    /**
     * Restituisce la lista degli ambienti compatibili con le condizioni metereologiche fornite.
     * Gli ambienti vengono determinati in base al tipo di meteo (sole, pioggia, nuvoloso)
     * e alla temperatura, includendo eventuali extra necessari come riscaldamento o raffreddamento.
     *
     * @param meteo oggetto {@link MeteoBean} che rappresenta le condizioni metereologiche attuali.
     * @return lista di oggetti {@link Ambiente} compatibili con il meteo specificato.
     * <p>
     *
     * Restituisce ambienti compatibili con il meteo:
     * - Sole:
     *   - Temperatura normale → ESTERNO
     *   - Freddo/caldo → ESTERNO_COPERTO con riscaldamento/raffreddamento
     * <p>
     * - Pioggia:
     *   - Sempre → INTERNO
     *   - ESTERNO_COPERTO con riscaldamento/raffreddamento se freddo/caldo
     * <p>
     * - Nuvoloso:
     *   - Temperatura normale → ESTERNO + ESTERNO_COPERTO
     *   - Freddo/caldo → INTERNO + ESTERNO_COPERTO con riscaldamento/raffreddamento
     *
     * - Altro → INTERNO
     */
    private List<Ambiente> generaAmbientiCompatibiliDaMeteo(MeteoBean meteo) {
        List<Ambiente> ambientiCompatibili = new ArrayList<>();
        int temperatura = meteo.getTemperatura();
        String condizione = meteo.getTempo().toLowerCase();

        boolean temperaturaNormale = temperatura >= 15 && temperatura <= 25;
        boolean temperaturaFredda = temperatura < 15;

        Set<Extra> extraClimatici = temperaturaNormale ? Set.of() : getExtraClimatici(temperaturaFredda);

        switch (condizione) {
            case "sole" -> {
                if (temperaturaNormale) {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO,Set.of()));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO,extraClimatici));
                }
            }

            case "pioggia" -> {
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO,Set.of()));
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, extraClimatici));
            }

            case "nuvoloso" -> {
                if (temperaturaNormale) {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO,Set.of()));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, Set.of()));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, Set.of()));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, extraClimatici));
                }
            }

            default -> ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, Set.of()));
        }

        return ambientiCompatibili;
    }


    /**
     * Restituisce l'extra necessario in base alla temperatura.
     * - Se fa freddo → RISCALDAMENTO
     * - Se fa caldo → RAFFREDDAMENTO
     * - Se la temperatura è normale → nessun extra
     *
     * @param temperaturaFredda true se la temperatura è sotto i 15°C
     * @return Set di ExtraClimatico richiesto
     */
    private Set<Extra> getExtraClimatici(boolean temperaturaFredda) {
        return temperaturaFredda
                ? Set.of(Extra.RISCALDAMENTO)
                : Set.of(Extra.RAFFREDDAMENTO);
    }



    public MeteoBean previsioneMetereologiche(FiltriBean filtri) throws IOException, PrevisioniMeteoFuoriRangeException {
        try {
            verificaDataMeteo(filtri);

            MeteoBean meteo= BoundaryMeteoMockAPI.getMeteoDaMockAPI();
            ambientiCompatibiliConIlMeteo=generaAmbientiCompatibiliDaMeteo(meteo);
            return meteo;

        } catch (IOException e) {
            throw new IOException("Errore di comunicazione con il servizio meteo: " + e.getMessage());
        }
    }

    public boolean meteoDaModificare(FiltriBean filtriBean, FiltriBean nuoviFiltri) {
        if(!filtriBean.getOra().equals(nuoviFiltri.getOra())) {
            long differenzaOre = Math.abs(Duration.between(filtriBean.getOra(), nuoviFiltri.getOra()).toHours());

            if(differenzaOre >= 3) {
                return true;
            }
        }

        return !filtriBean.getData().equals(nuoviFiltri.getData()) || !filtriBean.getCitta().equals(nuoviFiltri.getCitta());

    }

    public RistoranteBean dettagliRistorante(RistoranteBean ristoranteBean) throws EccezioneDAO, ValidazioneException {
        Ristorante ristorante= ConvertitoreRistorante.cardRistoranteInModel(ristoranteBean);

        Ristorante dettagliRistorante= ristoranteDAO.dettagliRistorante(ristorante);

        ristorante.setTelefono(dettagliRistorante.getTelefono());
        ristorante.posizioneRistorante().setCap(dettagliRistorante.posizioneRistorante().getCap());
        ristorante.posizioneRistorante().indirizzoCompleto(dettagliRistorante.posizioneRistorante().via(),dettagliRistorante.posizioneRistorante().numeroCivico());

        GiorniEOrari aperturaRistorante= new GiorniEOrari();
        ristorante.setOrariApertura(aperturaRistorante);

        ristorante.orariApertura().setInizioPranzo(dettagliRistorante.orariApertura().getInizioPranzo());
        ristorante.orariApertura().setFinePranzo(dettagliRistorante.orariApertura().getFinePranzo());
        ristorante.orariApertura().setInizioCena(dettagliRistorante.orariApertura().getInizioCena());
        ristorante.orariApertura().setFineCena(dettagliRistorante.orariApertura().getFineCena());

        return ConvertitoreRistorante.profiloRistoranteInBean(ristorante);
    }


    public PersonaBean datiUtente() throws ValidazioneException {return ConvertitorePersona.dettagliUtentePrenotazioneInBean(Sessione.getInstance().getPersona());}

    public boolean prenotaRistorante(PrenotazioneBean prenotazioneBean) throws EccezioneDAO, PrenotazioneEsistenteException {

        Prenotazione prenotazione = ConvertitorePrenotazione.nuovaPrenotazioneInModel(prenotazioneBean);
        prenotazione.setPrenotante(Sessione.getInstance().getPersona().getEmail());
        prenotazione.setFasciaOraria(trovaFasciaOraria(prenotazione.oraPrenotazione()));


        Ambiente ambiente= new Ambiente();
        ambiente.setRistorante(prenotazioneBean.getRistorante().getPartitaIVA());
        ambiente.setCategoria(prenotazione.getAmbiente().categoriaAmbiente());
        Ambiente a= ambienteDAO.cercaIdAmbiente(ambiente);

        ambiente.setIdAmbiente(a.getIdAmbiente());
        prenotazione.aggiungiAmbiente(ambiente);

        if (prenotazioneDAO.esistePrenotazione(prenotazione)) {
            throw new PrenotazioneEsistenteException("Esiste già una prenotazione per la data e l'ora scelta");
        }

        return prenotazioneDAO.inserisciPrenotazione(prenotazione);
    }



    public PersonaBean prenotazioniUtente() throws ValidazioneException{
        Persona persona= Sessione.getInstance().getPersona();

        PersonaBean personaBean= ConvertitorePersona.dettagliUtentePrenotazioneInBean(persona);
        List<PrenotazioneBean> listaPrenotazioniBean= new ArrayList<>();
        try{
            List<Prenotazione> listaPrenotazioni=prenotazioneDAO.selezionaPrenotazioniUtente(persona);
            for(Prenotazione prenotazione: listaPrenotazioni) {

                prenotazione.aggiungiAmbiente(ambienteDAO.cercaNomeAmbienteERistorante(prenotazione.getAmbiente()));

                Ristorante ristorante= ristoranteDAO.selezionaInfoRistorante(prenotazione.getAmbiente());

                PrenotazioneBean prenotazioneBean= ConvertitorePrenotazione.datiPrenotazioneInBean(prenotazione);

                RistoranteBean ristoranteBean= new RistoranteBean();
                ristoranteBean.setNome(ristorante.getNome());
                ristoranteBean.setCitta(ristorante.posizioneRistorante().getCitta());
                ristoranteBean.setIndirizzoCompleto(ristorante.posizioneRistorante().getIndirizzoCompleto());

                prenotazioneBean.setRistorante(ristoranteBean);


                listaPrenotazioniBean.add(prenotazioneBean);
            }

            personaBean.setPrenotazioniAttive(listaPrenotazioniBean);

        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
        return personaBean;
    }


    public List<PrenotazioneBean> prenotazioniRistoratore() throws ValidazioneException {

        PersonaDAO personaDAO= daoFactoryFacade.getPersonaDAO();
        List<PrenotazioneBean> listaPrenotazioniBean= new ArrayList<>();

        try {
            List<Ambiente> ambientiRistorante= Sessione.getInstance().getPersona().getRistorante().ambientiRistorante();

            for(Ambiente ambiente: ambientiRistorante) {

                ambiente.aggiungiPrenotazioniAttive(prenotazioneDAO.selezionaPrenotazioniRistoratore(ambiente));

                for (Prenotazione prenotazione : ambiente.prenotazioniAttive()) {

                    Persona prenotante = personaDAO.informazioniUtente(new Persona(prenotazione.prenotante()));
                    prenotazione.aggiungiAmbiente(ambiente);

                    PrenotazioneBean prenotazioneBean = ConvertitorePrenotazione.datiPrenotazioneInBean(prenotazione);
                    prenotazioneBean.setNomePrenotante(prenotante.getNome());
                    prenotazioneBean.setCognomePrenotante(prenotante.getCognome());
                    prenotazioneBean.setTelefonoPrenotante(prenotante.numeroTelefonico());

                    listaPrenotazioniBean.add(prenotazioneBean);

                }
            }

        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
        listaPrenotazioniBean.sort(Comparator.comparing(PrenotazioneBean::getData).reversed());


        return listaPrenotazioniBean;
    }

    public PrenotazioneBean notificheNuovePrenotazioni() throws ValidazioneException{

        Prenotazione prenotazione;

        try {
            if(Sessione.getInstance().getPersona().getTipoPersona().equals(TipoPersona.RISTORATORE)) {
                prenotazione=prenotazioneDAO.contaNotificheAttiveRistoratore(Sessione.getInstance().getPersona().getRistorante().ambientiRistorante());
            }else {
                prenotazione = prenotazioneDAO.contaNotificheAttiveUtente(Sessione.getInstance().getPersona());
            }

            PrenotazioneBean prenotazioneBean= new PrenotazioneBean();
            prenotazioneBean.setNumeroNotifiche(prenotazione.getNumeroNotifiche());

            return prenotazioneBean;
        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
    }


    public void modificaStatoNotifica() throws ValidazioneException{
        try {
            if(Sessione.getInstance().getPersona().getTipoPersona().equals(TipoPersona.RISTORATORE)) {
                prenotazioneDAO.resettaNotificheRistoratore(Sessione.getInstance().getPersona().getRistorante().ambientiRistorante());
            }else {
                prenotazioneDAO.resettaNotificheUtente(Sessione.getInstance().getPersona());
            }
        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
    }


}
