package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.RecensioneBean;
import com.example.meteo_gusto.model.Persona;
import com.example.meteo_gusto.model.Recensione;
import java.time.LocalDate;

public class ConvertitoreRecensione {

    private ConvertitoreRecensione() { /* COSTRUTTORE VUOTO */ }

    /**
     * Attributi gestiti da questo convertitore : email, partita iva, data, stelle
     */
    public static Recensione recensioneInModel(RecensioneBean recensioneBean, Persona utente, LocalDate data) {
        if (recensioneBean == null || utente == null || data == null) return null;

        return new Recensione(
                utente.getEmail(),
                recensioneBean.getRistorante(),
                recensioneBean.getStelle(),
                data
        );
    }


}
