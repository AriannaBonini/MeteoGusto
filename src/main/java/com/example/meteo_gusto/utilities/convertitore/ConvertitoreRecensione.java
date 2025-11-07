package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.RecensioneBean;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Recensione;
import java.time.LocalDate;

public class ConvertitoreRecensione {

    private ConvertitoreRecensione() { /* COSTRUTTORE VUOTO */ }

    public static Recensione recensioneBeanInModel(RecensioneBean recensioneBean, Persona utente, LocalDate data) {
        if (recensioneBean == null || utente == null || data == null) return null;

        return new Recensione(
                utente,
                ConvertitoreRistorante.ristoranteBeanInModel(recensioneBean.getRistorante()),
                recensioneBean.getStelle(),
                data
        );
    }

    public static RecensioneBean recensioneModelInBean(Recensione recensioneModel) {
        if (recensioneModel == null) return null;

        return new RecensioneBean(
                recensioneModel.getStelle(),
                ConvertitoreRistorante.ristoranteModelInBean(recensioneModel.getRistorante())
        );
    }
}
