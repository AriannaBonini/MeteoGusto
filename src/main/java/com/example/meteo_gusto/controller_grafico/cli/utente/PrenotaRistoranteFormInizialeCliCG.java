package com.example.meteo_gusto.controller_grafico.cli.utente;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.cli.GestoreInput;
import com.example.meteo_gusto.controller_grafico.cli.GestoreScenaCLI;
import com.example.meteo_gusto.controller_grafico.cli.InterfacciaCLI;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.utilities.supporto_cli.CodiceAnsi;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static com.example.meteo_gusto.controller_grafico.cli.GestoreInput.opzioneScelta;


public class PrenotaRistoranteFormInizialeCliCG implements InterfacciaCLI {

    private final DateTimeFormatter formatoOrario = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteFormInizialeCliCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController= new PrenotaRistoranteController();

    @Override
    public void start() {
        GestoreOutput.separatoreArancione();

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> popolaForm();
                    case 2 -> GestoreScenaCLI.vaiAllaListaPrenotazioniUtente();
                    case 3 -> GestoreScenaCLI.viaAllaHomeUtente();
                    case 4 -> esci=true;
                    default -> throw new ValidazioneException("Scelta non valida");
                }
            } catch (ValidazioneException e) {
                GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,e.getMessage());
            }
        }
        GestoreScenaCLI.logout();
    }

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaTitolo("PRENOTA RISTORANTE ! ");
        popolaNotifiche();

        GestoreOutput.mostraGraficaMenu("Compila il Form di prenotazione", "Visualizza la tua lista delle prenotazioni", "Vai alla Home","Logout");
        return opzioneScelta(1,4);
    }

    private void popolaNotifiche() {
        try {
            GestoreOutput.visualizzazioneNotifiche(prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle notifiche : ", e);
        }
    }


    private void popolaForm() {
        GestoreOutput.stampaTitolo("PRENOTA IL TUO TAVOLO IN POCHI SECONDI ! ");
        GestoreOutput.stampaMessaggio("Scegli la città, la data, l'ora e quante persone sarete");

        try {
            FiltriBean filtriBean = new FiltriBean();

            String inputData = GestoreInput.leggiStringaDaInput("Inserisci una data (G/M/AAAA) :");
            filtriBean.setData(LocalDate.parse(inputData, formatoData));

            String inputOra = GestoreInput.leggiStringaDaInput("Inserisci un orario (HH:mm) :");
            filtriBean.setOra(LocalTime.parse(inputOra, formatoOrario));

            filtriBean.setCitta(GestoreInput.leggiStringaDaInput("Inserisci una città :"));
            filtriBean.setNumeroPersone(Integer.parseInt(GestoreInput.leggiStringaDaInput("Inserisci il numero di persone :")));

            prenotaRistoranteController.validaDati(filtriBean);
            cerca(filtriBean);

        } catch (ValidazioneException e) {
            GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE,"Dati inseriti non validi");
            logger.error("Errore di validazione dei dati inseriti", e );
        } catch (DateTimeParseException e) {
            GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE, "Orario o data non valida. Usa il formato HH:mm e GG/MM/AAAA");
        } catch (NumberFormatException e) {
            GestoreOutput.mostraAvvertenza(CodiceAnsi.ATTENZIONE, "Numero persone non valido. Riempire il campo");
        }
    }


    private void cerca(FiltriBean filtriBean) {
        GestoreScenaCLI.cambiaVistaConParametri(
                PrenotaRistoranteCliCG.class,
                vista -> vista.setFiltriBean(filtriBean)
        );
    }



}
