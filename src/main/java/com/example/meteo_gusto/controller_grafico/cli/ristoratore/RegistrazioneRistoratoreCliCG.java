package com.example.meteo_gusto.controller_grafico.cli.ristoratore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.controller.RegistrazioneController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreInput;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoPersona;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;

public class RegistrazioneRistoratoreCliCG implements InterfacciaCLI {

    private static final Logger logger = LoggerFactory.getLogger(RegistrazioneRistoratoreCliCG.class.getName());
    RegistrazioneController registrazioneController= new RegistrazioneController();
    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");
    RistoranteBean ristoranteBean= new RistoranteBean();
    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> registrazione();
                    case 2 -> GestoreScenaCLI.vaiAllaSceltaRegistrazione();
                    case 3 -> esci=true;
                    default -> throw new ValidazioneException(CodiceAnsi.SCELTA_NON_VALIDA);
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza("Errore ",e.getMessage());
            }
        }
        GestoreScenaCLI.login();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("REGISTRAZIONE RISTORATORE ");

        GestoreOutput.mostraGraficaMenu("Prosegui con la registrazione", "Torna alla scelta del tipo di registrazione " ,"Torna al login");
        return opzioneScelta(1,3);
    }

    private void registrazione() {
        try {
            registrazioneController.registraRistoratore(Objects.requireNonNull(prendiDatiRegistrazione()));
            GestoreOutput.mostraAvvertenza("Successo", "La registrazione è andata a buon fine!");

        } catch (EccezioneDAO | ValidazioneException e) {
            GestoreOutput.mostraAvvertenza("Errore " , e.getMessage());
            logger.error("Errore : ",e);
        }
    }

    private RegistrazioneUtenteBean prendiDatiRegistrazione() throws ValidazioneException{

        GestoreOutput.stampaTitolo("INFORMAZIONI PERSONALI");

        PersonaBean personaBean= new PersonaBean();

        RegistrazioneUtenteBean registrazioneUtenteBean = new RegistrazioneUtenteBean();


        personaBean.setTipoPersona(TipoPersona.UTENTE);
        personaBean.setNome(GestoreInput.leggiStringaDaInput("Inserisci il nome :"));
        personaBean.setCognome(GestoreInput.leggiStringaDaInput("Inserisci il cognome :"));
        personaBean.setTelefono(GestoreInput.leggiStringaDaInput("Inserisci il telefono :"));
        personaBean.setEmail(GestoreInput.leggiStringaDaInput("Inserisci la email : "));
        personaBean.setPassword(GestoreInput.leggiStringaDaInput("Inserisci la password : "));

        GestoreOutput.stampaMessaggio("Accetti i termini e la privacy ? ");
        GestoreOutput.stampaMessaggio("1) Si " + "2) No");
        if(opzioneScelta(1,2)==2) {
            return null;
        }

        GestoreOutput.stampaMessaggio("Confermi di essere maggiorenne ? ");
        GestoreOutput.stampaMessaggio("1) Si " + "2) No");
        if(opzioneScelta(1,2)==2) {
            return null;
        }




        prossimoPasso();
        datiRistorante();

        personaBean.setRistoranteBean(ristoranteBean);
        registrazioneUtenteBean.setPersona(personaBean);

        return registrazioneUtenteBean;
    }

    private void prossimoPasso() throws ValidazioneException {
        GestoreOutput.mostraGraficaMenu("Prosegui con il prossimo modulo della registrazione", "Torna alla scelta del tipo di registrazione " ,"Torna al login");
        switch(opzioneScelta(1,3)) {
            case 1 -> {/* ritorna al metodo chiamante */}
            case 2 -> GestoreScenaCLI.vaiAllaSceltaRegistrazione();
            case 3 -> GestoreScenaCLI.login();
            default -> throw new ValidazioneException(CodiceAnsi.SCELTA_NON_VALIDA);

        }
    }


    private void datiRistorante() throws ValidazioneException {
        PosizioneBean posizioneBean= new PosizioneBean();
        GiorniEOrariBean giorniEOrariBean= new GiorniEOrariBean();

        GestoreOutput.stampaTitolo("INFORMAZIONI RISTORANTE");

        ristoranteBean.setPartitaIVA(GestoreInput.leggiStringaDaInput("Inserisci la partita IVA :"));
        ristoranteBean.setNomeRistorante(GestoreInput.leggiStringaDaInput("Inserisci il nome :"));

        posizioneBean.setIndirizzoCompleto(GestoreInput.leggiStringaDaInput("Inserisci l'indirizzo completo (via, civico) : "));
        posizioneBean.setCitta(GestoreInput.leggiStringaDaInput("Inserisci la città : "));
        posizioneBean.setCap(GestoreInput.leggiStringaDaInput("Inserisci il cap : "));

        ristoranteBean.setTelefonoRistorante(GestoreInput.leggiStringaDaInput("Inserisci il telefono :"));
        ristoranteBean.setFasciaPrezzo(GestoreInput.leggiFasciaPrezzoSceltaDaInput());
        ristoranteBean.setPosizione(posizioneBean);

        giorniEOrariBean.setGiorniChiusura(GestoreInput.giorniChiusuraScelteDaInput());
        GestoreOutput.stampaMessaggio("Il tuo ristorante effettua :  1) Pranzo   2) Cena   3) Entrambi");
        switch(opzioneScelta(1,3)) {
            case 1 -> inserisciPranzo(giorniEOrariBean);
            case 2 -> inserisciCena(giorniEOrariBean);
            case 3 -> inserisciPranzoCena(giorniEOrariBean);
            default -> throw new ValidazioneException(CodiceAnsi.SCELTA_NON_VALIDA);

        }

        ristoranteBean.setGiorniEOrari(giorniEOrariBean);



        prossimoPasso();
        datiCucina(ristoranteBean);

        List<AmbienteBean> listaAmbientiBean= new ArrayList<>();

        prossimoPasso();
        datiAmbiente(listaAmbientiBean);

        ristoranteBean.setAmbiente(listaAmbientiBean);

    }

    private void datiAmbiente(List<AmbienteBean> listaAmbientiBean) throws  ValidazioneException{
        GestoreOutput.stampaTitolo("INFORMAZIONE COPERTI");

        Set<TipoAmbiente> ambienti= GestoreInput.leggiAmbientiSceltiDaInput();
        for(TipoAmbiente tipoAmbiente : ambienti) {
            AmbienteBean ambienteBean= new AmbienteBean();
            ambienteBean.setNumeroCoperti(Integer.parseInt(GestoreInput.leggiStringaDaInput("Inserisci il numero di coperti per l'ambiente " + tipoAmbiente.getId())));
            ambienteBean.setAmbiente(tipoAmbiente);

        if(tipoAmbiente.equals(TipoAmbiente.ESTERNO_COPERTO)) {
                ambienteBean.setExtra(GestoreInput.leggiExtraSceltiDaInput());
            }

            listaAmbientiBean.add(ambienteBean);
        }
    }

    private void datiCucina(RistoranteBean ristoranteBean) throws ValidazioneException {
        GestoreOutput.stampaTitolo("INFORMAZIONI CUCINA ");

        ristoranteBean.setCucina(GestoreInput.leggiCucineScelteDaInput(false).iterator().next());
        ristoranteBean.setTipoDieta(GestoreInput.leggiDieteScelteDaInput(true));
    }

    private void inserisciPranzoCena(GiorniEOrariBean giorniEOrariBean) throws ValidazioneException{
        inserisciPranzo(giorniEOrariBean);
        inserisciCena(giorniEOrariBean);

    }


    private void inserisciPranzo(GiorniEOrariBean giorniEOrariBean) throws ValidazioneException {
        String inizio = GestoreInput.leggiStringaDaInput("Inserisci l'orario di inizio pranzo");
        String fine = GestoreInput.leggiStringaDaInput("Inserisci l'orario di fine pranzo");

        try {

            giorniEOrariBean.setInizioPranzo(LocalTime.parse(inizio, formatoOrario));
            giorniEOrariBean.setFinePranzo(LocalTime.parse(fine, formatoOrario));
        }catch (DateTimeParseException e) {
            GestoreOutput.mostraAvvertenza("Attenzione","L'orario inserito non è valido. E' necessario rispettare il formato HH:mm");
        }
    }

    private void inserisciCena(GiorniEOrariBean giorniEOrariBean) throws ValidazioneException {
        String inizio = GestoreInput.leggiStringaDaInput("Inserisci l'orario di inizio cena");
        String fine = GestoreInput.leggiStringaDaInput("Inserisci l'orario di fine cena");

        try {
            giorniEOrariBean.setInizioCena(LocalTime.parse(inizio, formatoOrario));
            giorniEOrariBean.setFineCena(LocalTime.parse(fine, formatoOrario));
        }catch (DateTimeParseException e) {
            GestoreOutput.mostraAvvertenza("Attenzione","L'orario inserito non è valido. E' necessario rispettare il formato HH:mm");
        }
    }

}
