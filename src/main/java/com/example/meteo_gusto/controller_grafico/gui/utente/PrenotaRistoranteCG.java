package com.example.meteo_gusto.controller_grafico.gui.utente;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.MeteoBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.controller_grafico.gui.GestoreScena;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.eccezione.PrevisioniMeteoFuoriRangeException;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import com.example.meteo_gusto.sessione.Sessione;
import com.example.meteo_gusto.utilities.supporto_cli.GestoreOutput;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoComponentiGUISchedaRistorante;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoGUILogout;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoNotificheGUI;
import com.example.meteo_gusto.utilities.supporto_gui.SupportoScrollPaneCss;
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
    @FXML
    private HBox boxMeteo;
    @FXML
    private Label notifichePrenotazione;
    @FXML
    private ScrollPane scrollPane;


    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteCG.class.getName());
    private final PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private FiltriBean filtriBean;
    private List<RistoranteBean> listaRistorantiPrenotabili= new ArrayList<>();
    private MeteoBean meteoBean;

    public void initialize() {
        filtroFasciaPrezzo.getItems().setAll(FasciaPrezzoRistorante.values());
        filtra.setDisable(true);

        SupportoScrollPaneCss.inizializzaScrollPane(scrollPane);

        ChangeListener<Object> listenerCerca = (obs, o, n) -> {
            boolean abilitato =
                    campoCitta.getText() != null && !campoCitta.getText().trim().isEmpty() &&
                            campoData.getValue() != null &&
                            campoOra.getText() != null && !campoOra.getText().trim().isEmpty() &&
                            campoNumeroPersone.getText() != null && !campoNumeroPersone.getText().trim().isEmpty();

            cerca.setDisable(!abilitato);
        };


        campoCitta.textProperty().addListener(listenerCerca);
        campoData.valueProperty().addListener(listenerCerca);
        campoOra.textProperty().addListener(listenerCerca);
        campoNumeroPersone.textProperty().addListener(listenerCerca);

        ChangeListener<Object> listenerFiltra = (obs, o, n) -> filtra.setDisable(false);

        filtroFasciaPrezzo.valueProperty().addListener(listenerFiltra);

        List<CheckBox> checkBoxes = List.of(
                checkBoxHalal, checkBoxKosher, checkBoxVegano, checkBoxVegetariano,
                checkBoxPescetariano, checkBoxCeliaco, checkBoxSenzaLattosio,
                checkBoxItaliana, checkBoxSushi, checkBoxCinese, checkBoxGreca,
                checkBoxTurca, checkBoxMessicana, checkBoxFastFood, checkBoxMeteo, checkBoxPizza
        );

        checkBoxes.forEach(cb -> cb.selectedProperty().addListener(listenerFiltra));
        popolaNotifiche();

    }

    private void popolaNotifiche() {
        try {
            SupportoNotificheGUI.supportoNotifiche(notifichePrenotazione, prenotaRistoranteController.notificheNuovePrenotazioni().getNumeroNotifiche());

        }catch (ValidazioneException e) {
            logger.error("Errore durante il conteggio delle motifiche : ",e );
        }
    }

    public void impostaFiltriSelezionati(FiltriBean filtriBean, MeteoBean meteoBean) {
        if (filtriBean == null) return;

        this.filtriBean = filtriBean;

        impostaCheckBoxTipoCucina(filtriBean.getTipoCucina());
        impostaCheckBoxTipoDieta(filtriBean.getTipoDieta());

        filtroFasciaPrezzo.setValue(filtriBean.getFasciaPrezzoRistorante());

        checkBoxMeteo.setSelected(filtriBean.getMeteo());
        if (checkBoxMeteo.isSelected()) {
            this.meteoBean = meteoBean;
            mostraPrevisioniMetereologiche();
        }

        impostaCampiIniziali();
    }

    private void impostaCheckBoxTipoCucina(Set<TipoCucina> cucine) {
        checkBoxItaliana.setSelected(cucine != null && cucine.contains(TipoCucina.ITALIANA));
        checkBoxSushi.setSelected(cucine != null && cucine.contains(TipoCucina.SUSHI));
        checkBoxCinese.setSelected(cucine != null && cucine.contains(TipoCucina.CINESE));
        checkBoxGreca.setSelected(cucine != null && cucine.contains(TipoCucina.GRECA));
        checkBoxTurca.setSelected(cucine != null && cucine.contains(TipoCucina.TURCA));
        checkBoxPizza.setSelected(cucine != null && cucine.contains(TipoCucina.PIZZA));
        checkBoxMessicana.setSelected(cucine != null && cucine.contains(TipoCucina.MESSICANA));
        checkBoxFastFood.setSelected(cucine != null && cucine.contains(TipoCucina.FAST_FOOD));
    }

    private void impostaCheckBoxTipoDieta(Set<TipoDieta> diete) {
        checkBoxHalal.setSelected(diete != null && diete.contains(TipoDieta.HALAL));
        checkBoxKosher.setSelected(diete != null && diete.contains(TipoDieta.KOSHER));
        checkBoxVegano.setSelected(diete != null && diete.contains(TipoDieta.VEGANO));
        checkBoxVegetariano.setSelected(diete != null && diete.contains(TipoDieta.VEGETARIANO));
        checkBoxPescetariano.setSelected(diete != null && diete.contains(TipoDieta.PESCETARIANO));
        checkBoxCeliaco.setSelected(diete != null && diete.contains(TipoDieta.CELIACO));
        checkBoxSenzaLattosio.setSelected(diete != null && diete.contains(TipoDieta.SENZA_LATTOSIO));
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
                        "Nessun ristorante disponibile", "Prova a modificare uno o più dati della prenotazione : \n   (data, ora, città, numero di persone)"
                );
                popolaHBox();
                return;
            }

            popolaHBox();

        } catch (EccezioneDAO | ValidazioneException e) {
            logger.error("Errore durante la ricerca dei ristoranti filtrati: ", e);
        }catch (PrevisioniMeteoFuoriRangeException e) {
            GestoreOutput.mostraAvvertenza("Attenzione ", e.getMessage());
        }
    }


    private void popolaHBox() {
        vBoxRistoranti.getChildren().clear();

        HBox rigaCorrente = creaNuovaRiga();
        int count = 0;

        for (RistoranteBean ristorantePrenotabile : listaRistorantiPrenotabili) {

            VBox schedaRistorante = SupportoComponentiGUISchedaRistorante.creaVBoxSchedaRistorante();
            HBox infoRistorante1 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante1();
            HBox infoRistorante2 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante2();
            HBox infoRistorante3 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante3();
            HBox infoStelle = SupportoComponentiGUISchedaRistorante.creaHBoxInfoStelle();
            HBox fasciaPrezzo = SupportoComponentiGUISchedaRistorante.creaHBoxInfoFasciaPrezzo();

            Label nomeRistorante = SupportoComponentiGUISchedaRistorante.creaLabelNomeRistorante();
            Label cittaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelCittaETipoCucinaRistorante();
            Label tipoCucinaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelCittaETipoCucinaRistorante();
            Label mediaRecensione = SupportoComponentiGUISchedaRistorante.creaLabelMediaRecensione();
            Button scopriDiPiu = SupportoComponentiGUISchedaRistorante.creaBottoneScopriDiPiu();


            schedaRistorante.getChildren().add(SupportoComponentiGUISchedaRistorante.creaImmagineRistorante(ristorantePrenotabile));


            nomeRistorante.setText(ristorantePrenotabile.getNomeRistorante());
            popolaInfoFasciaPrezzo(ristorantePrenotabile, fasciaPrezzo);
            infoRistorante1.getChildren().addAll(nomeRistorante, fasciaPrezzo);
            schedaRistorante.getChildren().add(infoRistorante1);


            cittaRistorante.setText(" • " + ristorantePrenotabile.getPosizione().getCitta());
            tipoCucinaRistorante.setText(" • " + ristorantePrenotabile.getCucina().getId());
            infoRistorante2.getChildren().addAll(cittaRistorante, tipoCucinaRistorante);
            schedaRistorante.getChildren().add(infoRistorante2);

            mediaRecensione.setText(ristorantePrenotabile.getMediaStelle()+"/5");
            infoStelle.getChildren().addAll(
                    SupportoComponentiGUISchedaRistorante.creaImmagineStella("/Foto/stellinaColorata.png"),
                    mediaRecensione
            );
            infoRistorante3.getChildren().addAll(infoStelle, scopriDiPiu);
            schedaRistorante.getChildren().add(infoRistorante3);


            rigaCorrente.getChildren().add(schedaRistorante);
            count++;

            scopriDiPiu.setUserData(ristorantePrenotabile);
            scopriDiPiu.setOnAction(this::clickScopriDiPiu);


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
        SupportoComponentiGUISchedaRistorante.immagineFasciaPrezzoRistorante(ristorante,hBoxInfoFasciaPrezzo,true);
    }


    @FXML
    public void clickCerca()  {
        cerca.setDisable(true);
        try {
            aggiornaFiltri();

            popolaListaRistoranti();
        } catch (NumberFormatException e) {
            mostraErroreTemporaneamenteNellaLabel("Il campo numero persone non può essere vuoto");
        }catch (DateTimeParseException e) {
            mostraErroreTemporaneamenteNellaLabel("Oraio non valido. Usa il formato HH:mm");
        }catch (ValidazioneException e) {
            mostraErroreTemporaneamenteNellaLabel("I filtri inseriti non sono validi");
        }
    }


    @FXML
    public void clickFiltra() {
        try {
            filtra.setDisable(true);

            aggiornaFiltri();
            if(checkBoxMeteo.isSelected()) {
                if(meteoBean==null) {
                    meteoBean = prenotaRistoranteController.previsioneMetereologiche(filtriBean);
                }
                mostraPrevisioniMetereologiche();
                listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean,meteoBean);

            }else {
                boxMeteo.setVisible(false);
                listaRistorantiPrenotabili = prenotaRistoranteController.filtraRistorantiDisponibili(filtriBean,null);
            }

            popolaHBox();

        } catch (ValidazioneException e) {
            logger.error("Errore di validazione: {}", e.getMessage());
        } catch (EccezioneDAO e) {
            logger.error("Errore di accesso ai dati: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Errore di comunicazione con il servizio meteo: {}", e.getMessage());
            mostraErroreTemporaneamenteNellaLabel("Il servizio meteo non è disponibile");
        }catch (PrevisioniMeteoFuoriRangeException e) {
            GestoreScena.mostraAlertSenzaConferma("Attenzione ", e.getMessage());
        }
    }


    private void aggiornaFiltri() throws ValidazioneException{
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
        Button scopriDiPiu= (Button)evento.getSource();
        RistoranteBean ristoranteSelezionato= (RistoranteBean)scopriDiPiu.getUserData();

        GestoreScena.cambiaScenaConParametri("/ProfiloRistorante.fxml", evento,
                (ProfiloRistoranteCG controller) -> {
                    controller.setFiltriCorrenti(filtriBean, meteoBean);
                    controller.setRistoranteSelezionato(ristoranteSelezionato);
                });
    }

    private void mostraErroreTemporaneamenteNellaLabel(String messaggio) {
        String testoIniziale = "";
        infoErrore.setText(messaggio);

        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(event -> infoErrore.setText(testoIniziale));
        pausa.play();
    }

    private void mostraPrevisioniMetereologiche() {
        boxMeteo.setVisible(true);
        InputStream is;
        Image immagine;

        LocalTime oraCorrente = LocalTime.now();
        boolean giorno = oraCorrente.isBefore(LocalTime.of(18, 0));


        String percorsoMeteo = switch (meteoBean.getTempo()) {
            case "Sole" -> giorno ? "/Foto/Sole.png" : "/Foto/Luna.png";
            case "Pioggia" -> "/Foto/Pioggia.png";
            default -> giorno ? "/Foto/Nuvoloso.png" : "/Foto/LunaNuvoloso.png";
        };

        is = PrenotaRistoranteCG.class.getResourceAsStream(percorsoMeteo);
        assert is != null;
        immagine = new Image(is);
        immagineMeteo.setImage(immagine);

        // Testo meteo
        meteo.setText(meteoBean.getTempo() + " • " + meteoBean.getTemperatura());

        // Selezione immagine temperatura
        String percorsoTemperatura;
        int temperatura = meteoBean.getTemperatura();

        if (temperatura >= 5 && temperatura <= 14) {
            percorsoTemperatura = "/Foto/Freddo.png";
        } else if (temperatura >= 26 && temperatura <= 35) {
            percorsoTemperatura = "/Foto/Caldo.png";
        } else {
            percorsoTemperatura = "/Foto/TemperaturaOttimale.png";
        }

        is = PrenotaRistoranteCG.class.getResourceAsStream(percorsoTemperatura);
        assert is != null;
        immagine = new Image(is);
        immagineTemperatura.setImage(immagine);
    }

    @FXML
    public void clickEsci(MouseEvent evento){
        boolean risposta= SupportoGUILogout.gestisciLogoutCompleto(esci,prenotaRistorante,"/Foto/ClocheSelezionata.png","/Foto/ClocheNonSelezionata.png");

        if (risposta) {
            Sessione.getInstance().logout();
            GestoreScena.cambiaScena("/Login.fxml", evento);
        }
    }

    @FXML
    public void clickHome(MouseEvent evento) {GestoreScena.cambiaScena("/HomeUtente.fxml",evento);}

    @FXML
    public void clickListaPrenotazioni(MouseEvent evento) {GestoreScena.cambiaScena("/ListaPrenotazioniUtente.fxml",evento);}


}
