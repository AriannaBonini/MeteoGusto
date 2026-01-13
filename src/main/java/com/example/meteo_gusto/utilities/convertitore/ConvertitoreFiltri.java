package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.FiltriBean;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.model.Filtro;
import static com.example.meteo_gusto.utilities.convertitore.ConvertitoreEnum.cucineDaStringAEnum;
import static com.example.meteo_gusto.utilities.convertitore.ConvertitoreEnum.dieteDaStringAEnum;

public class ConvertitoreFiltri {

    private ConvertitoreFiltri(){ /* COSTRUTTORE VUOTO */ }


    /**
     * Attributi gestiti da questo convertitore : data, ora, città, numero persone
     */
    public static Filtro datiPrenotazioneInModel(FiltriBean filtriBean) {
        return new Filtro(
                filtriBean.getData(),
                filtriBean.getOra(),
                filtriBean.getCitta(),
                filtriBean.getNumeroPersone()
        );
    }


    /**
     * Attributi gestiti da questo convertitore : cucina, dieta, fascia prezzo
     */
    public static Filtro filtriCucinaEPrezzoInModel(FiltriBean filtriBean) {
        return new Filtro(
                FasciaPrezzoRistorante.fasciaPrezzoDaId(filtriBean.getFasciaPrezzo()),
                cucineDaStringAEnum(filtriBean.getCucine()),
                dieteDaStringAEnum(filtriBean.getDiete())
        );
    }

}
