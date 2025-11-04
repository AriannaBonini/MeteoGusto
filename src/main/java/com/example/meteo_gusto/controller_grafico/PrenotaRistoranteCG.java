package com.example.meteo_gusto.controller_grafico;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.bean.RistoranteBean;
import com.example.meteo_gusto.controller.PrenotaRistoranteController;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.utilities.SupportoComponentiGUISchedaRistorante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

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
    private Label infoErrore;
    @FXML
    private HBox boxMeteo;
    @FXML
    private ImageView immagineMeteo;
    @FXML
    private ImageView immagineTemperatura;
    @FXML
    private Label meteo;
    @FXML
    private ComboBox fasciaPrezzo;
    @FXML
    private CheckBox checkBoxHalal;
    @FXML
    private CheckBox checkBoxKosher;
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
    private VBox vBoxSchedaRistorante;
    @FXML
    private ImageView immagineRistorante;
    @FXML
    private HBox hBoxInfoRistorante1;
    @FXML
    private Label campoNomeRistorante;
    @FXML
    private HBox hBoxInfoFasciaPrezzo;
    @FXML
    private ImageView immagineDollaro;
    @FXML
    private HBox hBoxInfoRistorante2;
    @FXML
    private HBox hBoxInfoRistorante3;
    @FXML
    private Label campoCittaRistorante;
    @FXML
    private Label campoTipoCucinaRistorante;
    @FXML
    private HBox hBoxInfoStelle;
    @FXML
    private Label campoMediaRecensione;
    @FXML
    private Button bottoneScopriDiPiu;
    @FXML
    private VBox vBoxRistoranti;


    private static final Logger logger = LoggerFactory.getLogger(PrenotaRistoranteCG.class.getName());
    PrenotaRistoranteController prenotaRistoranteController = new PrenotaRistoranteController();
    private FiltriBean filtriBean;

    public void setFiltriBean(FiltriBean filtriBean) throws EccezioneDAO {
        this.filtriBean = filtriBean;
        impostaCampiIniziali();
    }

    private void impostaCampiIniziali() throws EccezioneDAO {
        campoData.setValue(filtriBean.getData());
        campoOra.setText(String.valueOf(filtriBean.getOra()));
        campoCitta.setText(filtriBean.getCitta());
        campoNumeroPersone.setText(String.valueOf(filtriBean.getNumeroPersone()));

        popolaListaRistoranti();
    }


    private void popolaListaRistoranti() throws EccezioneDAO {
        List<PrenotazioneBean> listaRistorantiPrenotabili = prenotaRistoranteController.cercaRistorantiDisponibili(filtriBean);
        if (listaRistorantiPrenotabili == null) {
            GestoreScena.mostraAlertSenzaConferma("Nessun ristorante disponibile", "Prova a modificare i criteri di ricerca.");
        } else {
            popolaHBox(listaRistorantiPrenotabili);
        }
    }

    private void popolaHBox(List<PrenotazioneBean> listaRistorantiPrenotabili) {
        vBoxRistoranti.getChildren().clear();

        HBox rigaCorrente = creaNuovaRiga();
        int count = 0;

        for (PrenotazioneBean ristorantePrenotabile : listaRistorantiPrenotabili) {

            VBox vBoxSchedaRistorante = SupportoComponentiGUISchedaRistorante.creaVBoxSchedaRistorante();
            HBox hBoxInfoRistorante1 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante1();
            HBox hBoxInfoRistorante2 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante2();
            HBox hBoxInfoRistorante3 = SupportoComponentiGUISchedaRistorante.creaHBoxInfoRistorante3();
            HBox hBoxInfoStelle = SupportoComponentiGUISchedaRistorante.creaHBoxInfoStelle();
            HBox hBoxInfoFasciaPrezzo = SupportoComponentiGUISchedaRistorante.creaHBoxInfoFasciaPrezzo();

            Label campoNomeRistorante = SupportoComponentiGUISchedaRistorante.creaLabelNomeRistorante();
            Label campoCittaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelCittaRistorante();
            Label campoTipoCucinaRistorante = SupportoComponentiGUISchedaRistorante.creaLabelTipoCucinaRistorante();
            Label campoMediaRecensione = SupportoComponentiGUISchedaRistorante.creaLabelMediaRecensione();
            Button bottoneScopriDiPiu = SupportoComponentiGUISchedaRistorante.creaBottoneScopriDiPiu();

            RistoranteBean ristorante = ristorantePrenotabile.getAmbiente().getRistorante();


            vBoxSchedaRistorante.getChildren().add(tipologiaRistorante(ristorante));


            campoNomeRistorante.setText(ristorante.getNomeRistorante());
            popolaInfoFasciaPrezzo(ristorante, hBoxInfoFasciaPrezzo);
            hBoxInfoRistorante1.getChildren().addAll(campoNomeRistorante, hBoxInfoFasciaPrezzo);
            vBoxSchedaRistorante.getChildren().add(hBoxInfoRistorante1);


            campoCittaRistorante.setText(ristorante.getCitta());
            campoTipoCucinaRistorante.setText(ristorante.getCucina().getId());
            hBoxInfoRistorante2.getChildren().addAll(campoCittaRistorante, campoTipoCucinaRistorante);
            vBoxSchedaRistorante.getChildren().add(hBoxInfoRistorante2);


            campoMediaRecensione.setText(String.valueOf(ristorante.getMediaStelle()));
            hBoxInfoStelle.getChildren().addAll(
                    SupportoComponentiGUISchedaRistorante.creaImmagineStella("/Foto/stellinaColorata.png"),
                    campoMediaRecensione
            );
            hBoxInfoRistorante3.getChildren().addAll(hBoxInfoStelle, bottoneScopriDiPiu);
            vBoxSchedaRistorante.getChildren().add(hBoxInfoRistorante3);


            rigaCorrente.getChildren().add(vBoxSchedaRistorante);
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
            case ITALIANA -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteItaliano.png");
            }
            case SUSHI -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteGiapponese.png");
            }
            case FAST_FOOD -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoPaninoteca.png");
            }
            case GRECA -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteGreco.png");
            }
            case CINESE -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteCinese.png");
            }
            case TURCA -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteTurco.png");
            }
            case PIZZA -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoPizzeria.png");
            }
            case MESSICANA -> {
                return SupportoComponentiGUISchedaRistorante.creaImmagineRistorante("/Foto/FotoRistoranteMessicano.png");
            }
        }
        return null;
    }


    private ImageView prendiImmagine(String percorso) {
        Image immagine = new Image(Objects.requireNonNull(getClass().getResourceAsStream(percorso)));
        return new ImageView(immagine);
    }


    @FXML
    public void clickCerca(ActionEvent evento) {}
    @FXML
    public void clickFiltra(ActionEvent evento) {}
    @FXML
    public void clickScopriDiPiu(ActionEvent evento) {}



}
