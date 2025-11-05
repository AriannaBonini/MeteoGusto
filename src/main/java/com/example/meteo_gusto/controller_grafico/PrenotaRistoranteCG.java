package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.MeteoBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.SupportoComponentiGUISchedaRistorante;
import com.example.meteo_gusto.utilities.SupportoGUILogout;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

public class PrenotaRistoranteCG {
    @FXML
    private DatePicker campoData;
    @FXML
    private TextField campoOra;
    @FXML
    private TextField campoNumeroPersone;
    @FXML
    private TextField campoCitta;
    @FXML
    private Button cerca;
    @FXML
    private Button filtra;
    @FXML
    private ImageView immagineMeteo;
    @FXML
    private ImageView immagineTemperatura;
    @FXML
    private Label meteo;
    @FXML
    private ComboBox<FasciaPrezzoRistorante> filtroFasciaPrezzo;
    @FXML
    private CheckBox checkBoxHalal;
    @FXML
    private CheckBox checkBoxKosher;
    @FXML
    private CheckBox checkBoxPizza;
    @FXML
    private CheckBox checkBoxVegano;
    @FXML
    private CheckBox checkBoxVegetariano;
    @FXML
    private CheckBox checkBoxPescetariano;
    @FXML
    private CheckBox checkBoxCeliaco;
    @FXML
    private CheckBox checkBoxSenzaLattosio;
    @FXML
    private CheckBox checkBoxItaliana;
    @FXML
    private CheckBox checkBoxSushi;
    @FXML
    private CheckBox checkBoxCinese;
    @FXML
    private CheckBox checkBoxGreca;
    @FXML
    private CheckBox checkBoxTurca;
    @FXML
    private CheckBox checkBoxMessicana;
    @FXML
    private CheckBox checkBoxFastFood;
    @FXML
    private HBox hBoxRigaRistoranti;
    @FXML
    private VBox vBoxRistoranti;
    @FXML
    private CheckBox checkBoxMeteo;
    @FXML
    private Label infoErrore;
    @FXML
    private ImageView esci;
    @FXML
    private ImageView prenotaRistorante;


    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private FiltriBean filtriBean;
    private List<PrenotazioneBean> listaRistorantiPrenotabili= new ArrayList<>();
    private MeteoBean meteoBean;

    public void initialize() {
        filtroFasciaPrezzo.getItems().setAll(FasciaPrezzoRistorante.values());
        filtra.setDisable(true);

        ChangeListener<Object> listenerCerca = (obs, o, n) -> {
            boolean abilitato = !campoCitta.getText().trim().isEmpty() &&
                    campoData.getValue() != null &&
                    !campoOra.getText().trim().isEmpty() &&
                    !campoNumeroPersone.getText().trim().isEmpty();
            cerca.setDisable(!abilitato);
        };

        campoCitta.textProperty().addListener(listenerCerca);
        campoData.valueProperty().addListener(listenerCerca);
        campoOra.textProperty().addListener(listenerCerca);
        campoNumeroPersone.textProperty().addListener(listenerCerca);

        ChangeListener<Object> listenerFiltra = (obs, o, n) -> controllaCampi();

        List<CheckBox> checkBoxes = List.of(
                checkBoxHalal, checkBoxKosher, checkBoxVegano, checkBoxVegetariano,
                checkBoxPescetariano, checkBoxCeliaco, checkBoxSenzaLattosio,
                checkBoxItaliana, checkBoxSushi, checkBoxCinese, checkBoxGreca,
                checkBoxTurca, checkBoxMessicana, checkBoxFastFood, checkBoxMeteo, checkBoxPizza
        );

        filtroFasciaPrezzo.valueProperty().addListener(listenerFiltra);
        checkBoxes.forEach(cb -> cb.selectedProperty().addListener(listenerFiltra));
    }

    private void controllaCampi() {
        boolean almenoUnFiltroAttivo = filtroFasciaPrezzo.getValue() != null ||
                Stream.of(
                        checkBoxHalal, checkBoxKosher, checkBoxVegano, checkBoxVegetariano,
                        checkBoxPescetariano, checkBoxCeliaco, checkBoxSenzaLattosio,
                        checkBoxItaliana, checkBoxSushi, checkBoxCinese, checkBoxGreca,
                        checkBoxTurca, checkBoxMessicana, checkBoxFastFood, checkBoxMeteo, checkBoxPizza
                ).anyMatch(CheckBox::isSelected);

        filtra.setDisable(!almenoUnFiltroAttivo);
    }



    public void setFiltriBean(FiltriBean filtriBean) {
        this.filtriBean = filtriBean;
        impostaCampiIniziali();
    }

    private void impostaCampiIniziali()  {
        campoData.setValue(filtriBean.getData());
        campoOra.setText(String.valueOf(filtriBean.getOra()));
        campoCitta.setText(filtriBean.getCitta());
        campoNumeroPersone.setText(String.valueOf(filtriBean.getNumeroPersone()));

        cerca.setDisable(true);

        popolaListaRistoranti();
    }

    private void popolaListaRistoranti() {
        try {
            listaRistorantiPrenotabili= prenotaRistoranteController.cercaRistorantiDisponibili(filtriBean);

            listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean, meteoBean);

            if (listaRistorantiPrenotabili == null || listaRistorantiPrenotabili.isEmpty()) {
                GestoreScena.mostraAlertSenzaConferma(
                        "Nessun ristorante disponibile", "Prova a modificare i criteri di ricerca o i filtri applicati.");
                popolaHBox();
                return;
            }

            popolaHBox();

        } catch (EccezioneDAO | ValidazioneException e) {
            logger.error("Errore durante la ricerca dei ristoranti filtrati: ", e);
            infoErrore.setText("Errore durante la ricerca dei ristoranti");
        }
    }


    private void popolaHBox() {
        vBoxRistoranti.getChildren().clear();

        HBox rigaCorrente = creaNuovaRiga();
        int count = 0;

        for (PrenotazioneBean ristorantePrenotabile : listaRistorantiPrenotabili) {

            VBox schedaRistorante = SupportoComponentiGUISchedaRistorante.creaVBoxSchedaRistorante();
            HBox infoRistorante1 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante1();
            HBox infoRistorante2 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante2();
            HBox infoRistorante3 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante3();
            HBox infoStelle = SupportoComponentiGUISchedaRistorante.creaHBoxInfoStelle();
            HBox fasciaPrezzo = SupportoComponentiGUISchedaRistorante.creaHBoxInfoFasciaPrezzo();

            Label nomeRistorante = SupportoComponentiGUISchedaRistorante.creaLabelNomeRistorante();
            Label cittaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelCittaRistorante();
            Label tipoCucinaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelTipoCucinaRistorante();
            Label mediaRecensione = SupportoComponentiGUISchedaRistorante.creaLabelMediaRecensione();
            Button scopriDiPiu = SupportoComponentiGUISchedaRistorante.creaBottoneScopriDiPiu();

            RistoranteBean ristorante = ristorantePrenotabile.getAmbiente().getRistorante();


            schedaRistorante.getChildren().add(tipologiaRistorante(ristorante));


            nomeRistorante.setText(ristorante.getNomeRistorante());
            popolaInfoFasciaPrezzo(ristorante, fasciaPrezzo);
            infoRistorante1.getChildren().addAll(nomeRistorante, fasciaPrezzo);
            schedaRistorante.getChildren().add(infoRistorante1);


            cittaRistorante.setText(" • " + ristorante.getCitta());
            tipoCucinaRistorante.setText(" • " + ristorante.getCucina().getId());
            infoRistorante2.getChildren().addAll(cittaRistorante, tipoCucinaRistorante);
            schedaRistorante.getChildren().add(infoRistorante2);


            mediaRecensione.setText(String.valueOf(ristorante.getMediaStelle()));
            infoStelle.getChildren().addAll(
                    SupportoComponentiGUISchedaRistorante.creaImmagineStella("/Foto/stellinaColorata.png"),
                    mediaRecensione
            );
            infoRistorante3.getChildren().addAll(infoStelle, scopriDiPiu);
            schedaRistorante.getChildren().add(infoRistorante3);


            rigaCorrente.getChildren().add(schedaRistorante);
            count++;


            if (count % 2 == 0) {
                vBoxRistoranti.getChildren().add(rigaCorrente);
                rigaCorrente = creaNuovaRiga();
            }
        }

        if (!rigaCorrente.getChildren().isEmpty()) {
            vBoxRistoranti.getChildren().add(rigaCorrente);
        }
    }

    private HBox creaNuovaRiga() {return SupportoComponentiGUISchedaRistorante.creaHBoxRigaRistoranti(hBoxRigaRistoranti);}


    private void popolaInfoFasciaPrezzo(RistoranteBean ristorante, HBox hBoxInfoFasciaPrezzo) {
        hBoxInfoFasciaPrezzo.getChildren().clear();
        switch (ristorante.getFasciaPrezzo()) {
            case FasciaPrezzoRistorante.ECONOMICO -> ripetiImmagineDollaro(1, hBoxInfoFasciaPrezzo);
            case FasciaPrezzoRistorante.MODERATO -> ripetiImmagineDollaro(2, hBoxInfoFasciaPrezzo);
            case FasciaPrezzoRistorante.COSTOSO -> ripetiImmagineDollaro(3, hBoxInfoFasciaPrezzo);
            case FasciaPrezzoRistorante.LUSSO -> ripetiImmagineDollaro(4, hBoxInfoFasciaPrezzo);
        }
    }

    private void ripetiImmagineDollaro(int numeroRipetizioni, HBox hBoxInfoFasciaPrezzo) {
        for (int i = 0; i < numeroRipetizioni; i++) {
            hBoxInfoFasciaPrezzo.getChildren().add(SupportoComponentiGUISchedaRistorante.creaImmagineDollaro("/Foto/Prezzo.png"));
        }
    }

    private ImageView tipologiaRistorante(RistoranteBean ristorante) {
        switch (ristorante.getCucina()) {
            case ITALIANA -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteItaliano.png");}
            case SUSHI -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteGiapponese.png");}
            case FAST_FOOD -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoPaninoteca.png");}
            case GRECA -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteGreco.png");}
            case CINESE -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteCinese.png");}
            case TURCA -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteTurco.png");}
            case PIZZA -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoPizzeria.png");}
            case MESSICANA -> {return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteMessicano.png");}
        }
        return null;
    }

    @FXML
    private void clickCerca()  {
        cerca.setDisable(true);
        try {
            aggiornaFiltri();

            popolaListaRistoranti();
        } catch (NumberFormatException e) {
            mostraErroreTemporaneamenteNellaLabel("Il campo numero persone non può essere vuoto");
        }catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Oraio non valido. Usa il formato HH:mm");
        }
    }


    @FXML
    private void clickFiltra() {
        try {
            filtra.setDisable(true);

            aggiornaFiltri();
            if(meteoBean==null) {
                meteoBean = prenotaRistoranteController.previsioneMetereologiche(filtriBean);

            }
            if(meteoBean!=null) {
                mostraPrevisioniMetereologiche();
            }

            listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean,meteoBean);
            popolaHBox();

        } catch (ValidazioneException e) {
            logger.error("Errore di validazione: {}", e.getMessage());
            mostraErroreTemporaneamenteNellaLabel("Errore durante il filtraggio dei dati");
        } catch (EccezioneDAO e) {
            logger.error("Errore di accesso ai dati: {}", e.getMessage());
            mostraErroreTemporaneamenteNellaLabel("La persistenza non è al momento disponibile");
        } catch (IOException e) {
            logger.error("Errore di comunicazione con il servizio meteo: {}", e.getMessage());
            mostraErroreTemporaneamenteNellaLabel("Il servizio meteo non è disponibile");
        }
    }


    private void aggiornaFiltri() {
        if (filtriBean == null) {
            filtriBean = new FiltriBean();
        }

        filtriBean.setData(campoData.getValue());
        filtriBean.setOra(LocalTime.parse(campoOra.getText()));
        filtriBean.setCitta(campoCitta.getText());
        filtriBean.setNumeroPersone(Integer.parseInt(campoNumeroPersone.getText()));

        filtriBean.setFasciaPrezzoRistorante(filtroFasciaPrezzo.getValue());
        filtriBean.setMeteo(checkBoxMeteo.isSelected());
        filtriBean.setTipoCucina(filtriTipoCucinaSelezionati());
        filtriBean.setTipoDieta(filtriTipoDietaSelezionati());
    }


    private Set<TipoCucina> filtriTipoCucinaSelezionati() {
        Set<TipoCucina> tipoCucina= new HashSet<>();

        if (checkBoxItaliana.isSelected()) tipoCucina.add(TipoCucina.ITALIANA);
        if (checkBoxSushi.isSelected()) tipoCucina.add(TipoCucina.SUSHI);
        if (checkBoxCinese.isSelected()) tipoCucina.add(TipoCucina.CINESE);
        if (checkBoxGreca.isSelected()) tipoCucina.add(TipoCucina.GRECA);
        if (checkBoxTurca.isSelected()) tipoCucina.add(TipoCucina.TURCA);
        if (checkBoxPizza.isSelected()) tipoCucina.add(TipoCucina.PIZZA);
        if (checkBoxMessicana.isSelected()) tipoCucina.add(TipoCucina.MESSICANA);
        if (checkBoxFastFood.isSelected()) tipoCucina.add(TipoCucina.FAST_FOOD);

        return tipoCucina;
    }

    private Set<TipoDieta> filtriTipoDietaSelezionati() {
        Set<TipoDieta> tipoDieta = new HashSet<>();

        if (checkBoxKosher.isSelected()) tipoDieta.add(TipoDieta.KOSHER);
        if (checkBoxHalal.isSelected()) tipoDieta.add(TipoDieta.HALAL);
        if (checkBoxVegano.isSelected()) tipoDieta.add(TipoDieta.VEGANO);
        if (checkBoxSenzaLattosio.isSelected()) tipoDieta.add(TipoDieta.SENZA_LATTOSIO);
        if (checkBoxVegetariano.isSelected()) tipoDieta.add(TipoDieta.VEGETARIANO);
        if (checkBoxCeliaco.isSelected()) tipoDieta.add(TipoDieta.CELIACO);
        if (checkBoxPescetariano.isSelected()) tipoDieta.add(TipoDieta.PESCETARIANO);

        return tipoDieta;
    }



    @FXML
    public void clickScopriDiPiu(ActionEvent evento) {
        /* CARICA INTERFACCIA SCHEDA PERSONALE RISTORATORE*/
    }

    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

    private void mostraPrevisioniMetereologiche() {
        InputStream is;
        Image immagine;
        if(meteoBean.getTempo().equals("Sole")) {
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/Sole.png");
        }else if(meteoBean.getTempo().equals("Pioggia")){
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/Pioggia.png");
        }else {
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/Nuvoloso.png");

        }
        assert is != null;
        immagine= new Image(is);
        immagineMeteo.setImage(immagine);

        meteo.setText( meteoBean.getTempo() + " • " + meteoBean.getTemperatura());
        if((meteoBean.getTemperatura()>=5) && (meteoBean.getTemperatura()<=14)) {
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/Freddo.png");
        }else if((meteoBean.getTemperatura()>=26) && (meteoBean.getTemperatura()<=35)) {
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/Caldo.png");
        }else {
            is = PrenotaRistoranteCG.class.getResourceAsStream("/Foto/TemperaturaOttimale.png");
        }

        assert is != null;
        immagine= new Image(is);
        immagineTemperatura.setImage(immagine);
    }


    @FXML
    private void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,prenotaRistorante,"/Foto/ClocheSelezionata.png","/Foto/ClocheNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }


}
