package com.example.meteo_gusto.controller;


import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.dao.*;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
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
     * Cerca i ristoranti disponibili secondo i filtri inseriti dall'utente
     */
    public List<RistoranteBean> cercaRistorantiDisponibili(FiltriBean filtriInseriti) throws EccezioneDAO, ValidazioneException {
        List<RistoranteBean> listaRistorantiPrenotabili = new ArrayList<>();
        ristorantiPrenotabili.clear();

        GiornoChiusuraDAO giornoChiusuraDAO = daoFactoryFacade.getGiornoChiusuraDAO();

        Filtro filtri = ConvertitoreFiltri.filtriBeanInModel(filtriInseriti);

        GiorniSettimana giornoPrenotazione = GiorniSettimanaHelper.dataInGiornoSettimana(filtriInseriti.getData());

        for (Ristorante ristorante : ristoranteDAO.filtraRistorantiPerCitta(filtri)) {

            List<Ambiente> ambientiRistoranteDisponibili= new ArrayList<>();

            if (!orarioDiPrenotazioneValido(ristorante, filtriInseriti.getOra()) ||
                    !giornoDiPrenotazioneValido(giornoChiusuraDAO.giorniChiusuraRistorante(ristorante), giornoPrenotazione)) {
                continue;
            }


            for (Ambiente ambiente : ambienteDAO.cercaAmbientiDelRistorante(ristorante)) {

                Prenotazione prenotazione = new Prenotazione(
                        filtriInseriti.getData(),
                        filtriInseriti.getOra(),
                        filtriInseriti.getNumeroPersone(),
                        ambiente,
                        null,
                        trovaFasciaOraria(ristorante, filtriInseriti.getOra())
                );

                int postiDisponibili = ambiente.getNumeroCoperti() -
                        prenotazioneDAO.postiOccupatiPerDataEFasciaOraria(prenotazione).getNumeroPersone();


                if (postiDisponibili >= filtriInseriti.getNumeroPersone()) {
                    ambientiRistoranteDisponibili.add(ambiente);

                }
            }
            if(!ambientiRistoranteDisponibili.isEmpty()) {
                ristorante.setAmbienteRistorante(ambientiRistoranteDisponibili);
                ristorantiPrenotabili.add(ristorante);
                listaRistorantiPrenotabili.add(ConvertitoreRistorante.ristoranteModelInBean(ristorante));
            }
        }
        return listaRistorantiPrenotabili;
    }



    public void validaDati(FiltriBean filtriInseriti) throws ValidazioneException {
        ConvertitoreFiltri.filtriBeanInModel(filtriInseriti);
    }


    /**
     * Controlla se l'orario di prenotazione è valido rispetto agli orari di apertura del ristorante
     */
    private boolean orarioDiPrenotazioneValido(Ristorante ristorante, LocalTime oraPrenotazione) {

        boolean pranzoValido = ristorante.getOrari().getInizioPranzo() != null &&
                ristorante.getOrari().getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getOrari().getFinePranzo());
        boolean cenaValida = ristorante.getOrari().getInizioCena() != null &&

                ristorante.getOrari().getFineCena() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioCena()) && //
                !oraPrenotazione.isAfter(ristorante.getOrari().getFineCena());

        return pranzoValido || cenaValida;
    }

    /**
     * Controlla se il giorno della prenotazione è valido rispetto ai giorni di chiusura del ristorante
     */
    private boolean giornoDiPrenotazioneValido(GiorniEOrari giorniEOrari, GiorniSettimana giornoPrenotazione) {
        return !giorniEOrari.getGiorniChiusura().contains(giornoPrenotazione);
    }


    /**
     * Determina la fascia oraria della prenotazione in base all'orario
     */
    private FasciaOraria trovaFasciaOraria(Ristorante ristorante, LocalTime oraPrenotazione) {
        if (ristorante.getOrari().getInizioPranzo() != null && ristorante.getOrari().getFinePranzo() != null &&
                !oraPrenotazione.isBefore(ristorante.getOrari().getInizioPranzo()) &&
                !oraPrenotazione.isAfter(ristorante.getOrari().getFinePranzo())) {

            return FasciaOraria.PRANZO;
        }

        return FasciaOraria.CENA;
    }

    public List<RistoranteBean> filtraRistorantiDisponibili(FiltriBean filtriBeanInseriti, MeteoBean meteoBean) throws EccezioneDAO, ValidazioneException,PrevisioniMeteoFuoriRangeException {
        if(filtriBeanInseriti==null) {
            return ConvertitoreRistorante.listaRistoranteModelInBean(ristorantiPrenotabili);
        }

        if (filtriBeanInseriti.getMeteo() && dataOltreGiorniMax(filtriBeanInseriti)) {
            throw  new PrevisioniMeteoFuoriRangeException("La prenotazione basata sulle previsioni meteo può essere effettuata solo entro le 2 settimane dalla data odierna. Modifica la data.");
        }

        List<RistoranteBean> listaRistorantiFiltrati = new ArrayList<>();
        Filtro filtriModel = ConvertitoreFiltri.filtriBeanInModel(filtriBeanInseriti);

        for (Ristorante ristorante : ristorantiPrenotabili) {
            if (rispettaFiltri(ristorante, filtriModel)) {

                List<Ambiente> ambientiCompatibili= controllaAmbientiCompatibiliMeteo(ristorante, meteoBean);

                if(!ambientiCompatibili.isEmpty()) {

                    List<AmbienteBean> ambientiCompatibiliBean= ConvertitoreAmbiente.listaAmbienteModelInBean(ambientiCompatibili);

                    RistoranteBean ristoranteBean= ConvertitoreRistorante.ristoranteModelInBean(ristorante);
                    ristoranteBean.setAmbiente(ambientiCompatibiliBean);

                    listaRistorantiFiltrati.add(ristoranteBean);
                }

            }
        }

        return listaRistorantiFiltrati;
    }

    private boolean dataOltreGiorniMax(FiltriBean filtriBean)  {

        LocalDate dataPrenotazione= filtriBean.getData();
        LocalDate oggi = LocalDate.now();

        long giorniDiDifferenza = ChronoUnit.DAYS.between(oggi, dataPrenotazione);

        return giorniDiDifferenza > 15;
    }


    private List<Ambiente> controllaAmbientiCompatibiliMeteo(Ristorante ristorante, MeteoBean previsioniMeteo) throws EccezioneDAO {

        if (previsioniMeteo != null) {
            if (ambientiCompatibiliConIlMeteo.isEmpty()) {
                ambientiCompatibiliConIlMeteo = generaAmbientiCompatibiliDaMeteo(previsioniMeteo);
            }

            return ristoranteCompatibile(ristorante);
        }

        return ristorantiPrenotabili.stream()
                .filter(r -> r.getPartitaIVA().equals(ristorante.getPartitaIVA()))
                .findFirst()
                .map(Ristorante::getAmbienteRistorante)
                .orElse(Collections.emptyList());
    }

    private boolean rispettaFiltri(Ristorante ristorante, Filtro filtri) throws EccezioneDAO {

        if (filtri.getFasciaPrezzoRistorante() != null &&
                !filtri.getFasciaPrezzoRistorante().equals(ristorante.getFasciaPrezzo())) {
            return false;
        }


        if (filtri.getTipoCucina() != null &&
                !filtri.getTipoCucina().isEmpty() &&
                !filtri.getTipoCucina().contains(ristorante.getCucina())) {
            return false;
        }



        if (filtri.getTipoDieta() != null && !filtri.getTipoDieta().isEmpty()) {

            Ristorante ristoranteDaControllare= new Ristorante(ristorante.getPartitaIVA());
            ristoranteDaControllare.setTipoDieta(filtri.getTipoDieta());
            Ristorante dieteRistoranteCompatibili = dietaDAO.controllaDieteDelRistorante(ristoranteDaControllare);


            if (dieteRistoranteCompatibili==null || dieteRistoranteCompatibili.getTipoDieta() == null || dieteRistoranteCompatibili.getTipoDieta().isEmpty()) {
                return false;
            }

            ristorante.setTipoDieta(dieteRistoranteCompatibili.getTipoDieta());

        }

        return true;
    }

    private List<Ambiente> ristoranteCompatibile(Ristorante ristorante) throws EccezioneDAO {
        List<Ambiente> ambientiCompatibili = new ArrayList<>();


        for (Ambiente ambienteRistorante : ristorante.getAmbienteRistorante()) {
            if (isAmbienteCompatibile(ambienteRistorante, ristorante.getPartitaIVA())) {

                ambientiCompatibili.add(ambienteRistorante);
            }
        }
        return ambientiCompatibili;
    }

    private boolean isAmbienteCompatibile(Ambiente ambienteRistorante, String partitaIVA) throws EccezioneDAO {
        TipoAmbiente tipo = ambienteRistorante.getTipoAmbiente();

        Ambiente ambienteCompatibile = ambientiCompatibiliConIlMeteo.stream()
                .filter(a -> a.getTipoAmbiente() == tipo)
                .findFirst()
                .orElse(null);

        if (ambienteCompatibile == null) return false;


        Set<Extra> extraRichiesti = ambienteCompatibile.getExtra();
        if (extraRichiesti == null || extraRichiesti.isEmpty()) {
            ambienteRistorante.setRistorante(partitaIVA);
            return true;
        }

        ambienteRistorante.setRistorante(partitaIVA);
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
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO, null, null, Set.of(),null));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici,null));
                }
            }

            case "pioggia" -> {
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of(),null));
                ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici,null));
            }

            case "nuvoloso" -> {
                if (temperaturaNormale) {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO, null, null, Set.of(),null));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, Set.of(),null));
                } else {
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of(),null));
                    ambientiCompatibili.add(new Ambiente(TipoAmbiente.ESTERNO_COPERTO, null, null, extraClimatici,null));
                }
            }

            default -> ambientiCompatibili.add(new Ambiente(TipoAmbiente.INTERNO, null, null, Set.of(),null));
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



    public MeteoBean previsioneMetereologiche(FiltriBean filtri) throws IOException {
        try {
            if(filtri.getMeteo()) {
                return BoundaryMeteoMockAPI.getMeteoDaMockAPI();
            }
        } catch (IOException e) {
            throw new IOException("Errore di comunicazione con il servizio meteo: " + e.getMessage());
        }
        return null;
    }

    public PersonaBean datiUtente() {return ConvertitorePersona.personaModelInBean(Sessione.getInstance().getPersona());}

    public boolean prenotaRistorante(PrenotazioneBean prenotazioneBean, RistoranteBean ristoranteBean) throws EccezioneDAO {

        Ristorante ristorante = ConvertitoreRistorante.ristoranteBeanInModel(ristoranteBean);
        Prenotazione prenotazione = ConvertitorePrenotazione.prenotazioneBeanInModel(prenotazioneBean);

        prenotazione.setFasciaOraria(trovaFasciaOraria(ristorante, prenotazione.getOra()));

        List<Ambiente> ambienti = Optional.ofNullable(ristorante.getAmbienteRistorante())
                .orElse(Collections.emptyList());

        ambienti.stream()
                .filter(a -> Objects.equals(a.getTipoAmbiente(), prenotazione.getAmbiente().getTipoAmbiente()))
                .findFirst()
                .ifPresent(a -> prenotazione.getAmbiente().setIdAmbiente(a.getIdAmbiente()));

        if (prenotazioneDAO.esistePrenotazione(prenotazione)) {
            throw new EccezioneDAO("Esiste già una prenotazione per la data e l'ora scelta");
        }

        return prenotazioneDAO.inserisciPrenotazione(prenotazione);
    }



    public PersonaBean prenotazioniUtente() throws ValidazioneException{
        Persona persona= Sessione.getInstance().getPersona();

        PersonaBean personaBean= ConvertitorePersona.personaModelInBean(persona);
        List<PrenotazioneBean> listaPrenotazioniBean= new ArrayList<>();
        try{
            List<Prenotazione> listaPrenotazioni=prenotazioneDAO.selezionaPrenotazioniUtente(persona);
            for(Prenotazione prenotazione: listaPrenotazioni) {

                prenotazione.setAmbiente(ambienteDAO.cercaNomeAmbienteERistorante(prenotazione.getAmbiente()));

                Ristorante ristorante= ristoranteDAO.selezionaInfoRistorante(prenotazione.getAmbiente());

                PrenotazioneBean prenotazioneBean= ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione);
                prenotazioneBean.getAmbiente().setNomeRistorante(ristorante.getNomeRistorante());
                prenotazioneBean.getAmbiente().setCittaRistorante(ristorante.getPosizione().getCitta());
                prenotazioneBean.getAmbiente().setIndirizzoCompletoRistorante(ristorante.getPosizione().getIndirizzoCompleto());


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
            List<Prenotazione> listaPrenotazioni= prenotazioneDAO.selezionaPrenotazioniRistoratore(Sessione.getInstance().getPersona().getRistorante().getAmbienteRistorante());

            for(Prenotazione prenotazione: listaPrenotazioni)  {
                Sessione.getInstance()
                        .getPersona()
                        .getRistorante()
                        .getAmbienteRistorante()
                        .stream()
                        .filter(a -> a.getIdAmbiente().equals(prenotazione.getAmbiente().getIdAmbiente()))
                        .findFirst().ifPresent(prenotazione::setAmbiente);

                prenotazione.setUtente(personaDAO.informazioniUtente(prenotazione.getUtente()));

                PrenotazioneBean prenotazioneBean= ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione);

                listaPrenotazioniBean.add(prenotazioneBean);
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
                prenotazione=prenotazioneDAO.contaNotificheRistoratore(Sessione.getInstance().getPersona().getRistorante().getAmbienteRistorante());
            }else {
                prenotazione = prenotazioneDAO.contaNotificheAttiveUtente(Sessione.getInstance().getPersona());
            }

            return ConvertitorePrenotazione.prenotazioneModelInBean(prenotazione);
        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
    }


    public void modificaStatoNotifica() throws ValidazioneException{
        try {
            if(Sessione.getInstance().getPersona().getTipoPersona().equals(TipoPersona.RISTORATORE)) {
                prenotazioneDAO.resettaNotificheRistoratore(Sessione.getInstance().getPersona().getRistorante().getAmbienteRistorante());
            }else {
                prenotazioneDAO.resettaNotificheUtente(Sessione.getInstance().getPersona());
            }
        }catch (EccezioneDAO e) {
            throw new ValidazioneException(e.getMessage());
        }
    }


}
