package com.example.meteo_gusto.utilities.convertitore;


import com.example.meteo_gusto.bean.PrenotazioneBean;
import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Prenotazione;

import java.util.Collections;

public class ConvertitorePrenotazione {

    private ConvertitorePrenotazione(){ /* COSTRUTTORE VUOTO */ }


    /**
     * Attributi gestiti da questo convertitore : data, ora, numero persone, tipo ambiente, note
     */
    public static Prenotazione nuovaPrenotazioneInModel(PrenotazioneBean prenotazioneBean) {
        Ambiente ambiente = new Ambiente();
        ambiente.setCategoria(TipoAmbiente.tipoAmbienteDaId(prenotazioneBean.getAmbiente().getFirst()));

        return new Prenotazione(
                prenotazioneBean.getData(),
                prenotazioneBean.getOra(),
                prenotazioneBean.getNumeroPersone(),
                ambiente,
                prenotazioneBean.getNote());
    }

    /**
     * Attributi gestiti da questo convertitore : data, ora, numero persone, tipo ambiente, note
     */
    public static PrenotazioneBean datiPrenotazioneInBean(Prenotazione prenotazione) throws ValidazioneException {
        PrenotazioneBean prenotazioneBean= new PrenotazioneBean();

        prenotazioneBean.setData(prenotazione.dataPrenotazione());
        prenotazioneBean.setOra(prenotazione.oraPrenotazione());
        prenotazioneBean.setNumeroPersone(prenotazione.numeroPersone());
        prenotazioneBean.setNote(prenotazione.getNote());
        prenotazioneBean.setAmbiente(Collections.singletonList(prenotazione.getAmbiente().categoriaAmbiente().getId()));

        return prenotazioneBean;
    }


}
