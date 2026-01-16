package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.*;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.model.*;
import java.util.*;
import static com.example.meteo_gusto.utilities.convertitore.ConvertitoreEnum.*;

public class ConvertitoreRistorante {

    private ConvertitoreRistorante() { /* COSTRUTTORE VUOTO */ }

    /**
     * Attributi gestiti da questo convertitore : nome, città, cucina, media stelle
     */
    public static List<RistoranteBean> miglioriQuattroRistorantiInBean(List<Ristorante> listaRistoranti) throws ValidazioneException {
        List<RistoranteBean> listaRistorantiBean= new ArrayList<>();

        for(Ristorante ristorante: listaRistoranti) {
            RistoranteBean ristoranteBean= new RistoranteBean();

            ristoranteBean.setNome(ristorante.getNome());
            ristoranteBean.setCitta(ristorante.posizioneRistorante().getCitta());
            ristoranteBean.setCucina(ristorante.getCucina().getId());
            ristoranteBean.setMediaStelle(ristorante.getMediaStelle());

            listaRistorantiBean.add(ristoranteBean);
        }
        return listaRistorantiBean;
    }

    /**
     * Attributi gestiti da questo convertitore : nome, città, cucina, media stelle, fascia prezzo
     */
    public static List<RistoranteBean> cardRistorantiInBean(List<Ristorante> listaRistoranti) throws ValidazioneException {
        List<RistoranteBean> listaRistorantiBean= new ArrayList<>();

        for(Ristorante ristorante: listaRistoranti) {
            RistoranteBean ristoranteBean= cardRistoranteInBean(ristorante);

            listaRistorantiBean.add(ristoranteBean);
        }
        return listaRistorantiBean;
    }



    /**
     * Attributi gestiti da questo convertitore : nome, città, cucina, media stelle, fascia prezzo, diete, ambienti
     */
    public static RistoranteBean cardRistoranteInBean(Ristorante ristorante) throws ValidazioneException {
        RistoranteBean ristoranteBean= new RistoranteBean();

        ristoranteBean.setPartitaIVA(ristorante.getPartitaIVA());
        ristoranteBean.setNome(ristorante.getNome());
        ristoranteBean.setCitta(ristorante.posizioneRistorante().getCitta());
        ristoranteBean.setCucina(ristorante.getCucina().getId());
        ristoranteBean.setMediaStelle(ristorante.getMediaStelle());
        ristoranteBean.setFasciaPrezzo(ristorante.fasciaPrezzoRistorante().getId());
        ristoranteBean.setDiete(dieteDaEnumAString(ristorante.getDiete()));

        ristoranteBean.setAmbiente(ambienteInBean(ristorante));
        return ristoranteBean;

    }

    public static List<AmbienteBean> ambienteInBean(Ristorante ristorante) throws ValidazioneException {
        List<AmbienteBean> ambientiRistorante= new ArrayList<>();

        for(Ambiente ambiente: ristorante.ambientiRistorante())  {
            AmbienteBean ambienteRistorante= new AmbienteBean();
            ambienteRistorante.setAmbiente(ambiente.categoriaAmbiente().getId());

            ambientiRistorante.add(ambienteRistorante);
        }
        return ambientiRistorante;
    }




    /**
     * Attributi gestiti da questo convertitore : nome, città, cucina, media stelle, fascia prezzo, diete, ambienti
     */
    public static Ristorante cardRistoranteInModel(RistoranteBean ristoranteBean) {
        Ristorante ristorante= new Ristorante();

        ristorante.setPartitaIVA(ristoranteBean.getPartitaIVA());
        ristorante.setNome(ristoranteBean.getNome());
        ristorante.setCucina(TipoCucina.tipoCucinaDaId(ristoranteBean.getCucina()));
        ristorante.setMediaStelle(ristoranteBean.getMediaStelle());
        ristorante.setFasciaPrezzo(FasciaPrezzoRistorante.fasciaPrezzoDaId(ristoranteBean.getFasciaPrezzo()));
        ristorante.setDiete(dieteDaStringAEnum(ristoranteBean.getDiete()));

        Posizione posizioneRistorante= new Posizione();
        posizioneRistorante.setCitta(ristoranteBean.getCitta());

        ristorante.setPosizione(posizioneRistorante);

        List<Ambiente> ambientiRistorante= new ArrayList<>();

        for(AmbienteBean ambienteBean: ristoranteBean.getAmbiente())  {
            Ambiente ambienteRistorante= new Ambiente();
            ambienteRistorante.setCategoria(TipoAmbiente.tipoAmbienteDaId(ambienteBean.getTipoAmbiente()));

            ambientiRistorante.add(ambienteRistorante);
        }

        ristorante.setAmbienti(ambientiRistorante);

        return ristorante;

    }

    /**
     * Attributi gestiti da questo convertitore : nome, cucina, media stelle, fascia prezzo, città,
     * indirizzo completo, cap, telefono, orari di inizio e fine pranzo e cena, diete, ambienti
     */
    public static RistoranteBean profiloRistoranteInBean(Ristorante ristorante) throws ValidazioneException {
        RistoranteBean ristoranteBean = new RistoranteBean();

        ristoranteBean.setPartitaIVA(ristorante.getPartitaIVA());
        ristoranteBean.setNome(ristorante.getNome());
        ristoranteBean.setCucina((ristorante.getCucina()).getId());
        ristoranteBean.setMediaStelle(ristorante.getMediaStelle());
        ristoranteBean.setFasciaPrezzo((ristorante.fasciaPrezzoRistorante()).getId());
        ristoranteBean.setCitta(ristorante.posizioneRistorante().getCitta());
        ristoranteBean.setIndirizzoCompleto(ristorante.posizioneRistorante().getIndirizzoCompleto());
        ristoranteBean.setCap(ristorante.posizioneRistorante().getCap());
        ristoranteBean.setTelefono(ristorante.getTelefono());
        ristoranteBean.setDiete(dieteDaEnumAString(ristorante.getDiete()));

        GiorniEOrariBean giorniEOrariBean= new GiorniEOrariBean();
        giorniEOrariBean.setInizioPranzo(ristorante.orariApertura().getInizioPranzo());
        giorniEOrariBean.setFinePranzo(ristorante.orariApertura().getFinePranzo());
        giorniEOrariBean.setInizioCena(ristorante.orariApertura().getInizioCena());
        giorniEOrariBean.setFineCena(ristorante.orariApertura().getFineCena());

        ristoranteBean.setOrariApertura(giorniEOrariBean);
        ristoranteBean.setAmbiente(ambienteInBean(ristorante));

        return ristoranteBean;

    }


    /**
     * Attributi gestiti da questo convertitore : partita iva, nome, indirizzo, città, cap, telefono,
     * fascia prezzo, giorni chisura, cucina, dieta, ora pranzo (inizio e fine), ora cena (inizio e fine),
     */

    public static Ristorante registrazioneRistoranteInModel(RistoranteBean ristoranteBean) {

        GiorniEOrari aperturaRistorante= new GiorniEOrari(
                ristoranteBean.getOrariApertura().getInizioPranzo(),
                ristoranteBean.getOrariApertura().getFinePranzo(),
                ristoranteBean.getOrariApertura().getInizioCena(),
                ristoranteBean.getOrariApertura().getFineCena(),
                giorniSettimanaDaStringAEnum(ristoranteBean.getOrariApertura().getGiorniChiusura())
        );

        Posizione posizioneRistorante= new Posizione(
                ristoranteBean.getIndirizzoCompleto(),
                ristoranteBean.getCitta(),
                ristoranteBean.getCap());

        Ristorante ristorante= new Ristorante(
                ristoranteBean.getPartitaIVA(),
                ristoranteBean.getNome(),
                ristoranteBean.getTelefono(),
                TipoCucina.tipoCucinaDaId(ristoranteBean.getCucina()),
                FasciaPrezzoRistorante.fasciaPrezzoDaId(ristoranteBean.getFasciaPrezzo()),
                posizioneRistorante,
                aperturaRistorante);

        ristorante.setDiete(dieteDaStringAEnum(ristoranteBean.getDiete()));
        return ristorante;

    }

}
