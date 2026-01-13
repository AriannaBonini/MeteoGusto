package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.bean.AmbienteBean;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.model.Ambiente;

public class ConvertitoreAmbiente {

    private ConvertitoreAmbiente(){ /* COSTRUTTORE VUOTO */ }

    /**
     * Attributi gestiti da questo convertitore : ambiente, numero coperti, extra, partita iva
     */
    public static Ambiente registrazioneAmbienteInModel(AmbienteBean ambienteBean) {
        return new Ambiente(
                TipoAmbiente.tipoAmbienteDaId(ambienteBean.getTipoAmbiente()),
                ambienteBean.getRistorante(),
                ambienteBean.getNumeroCoperti()
        );

    }

    /**
     * Attributi gestiti da questo convertitore : ambiente, numero coperti, extra, partita iva
     */
    public static Ambiente registrazioneAmbienteSpecialeInModel(AmbienteBean ambienteBean) {
        return new Ambiente(
                ambienteBean.getRistorante(),
                ambienteBean.getNumeroCoperti(),
                ambienteBean.getExtra()
        );
    }

}
