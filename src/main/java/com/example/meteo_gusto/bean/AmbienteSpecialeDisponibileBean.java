package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbienteConExtra;
import java.util.Set;

public class AmbienteSpecialeDisponibileBean {
    private Set<Extra> extra;
    private TipoAmbienteConExtra tipoAmbienteConExtra;
    private Integer numeroCoperti;

    public AmbienteSpecialeDisponibileBean() { /* COSTRUTTORE VUOTO */}

    /* SETTER CON VALIDAZIONE */

    public void setTipoAmbienteConExtra(TipoAmbienteConExtra tipoAmbienteConExtra) throws ValidazioneException {
        if (tipoAmbienteConExtra == null && this.numeroCoperti != null) {
            throw new ValidazioneException("Tipo ambiente non può essere nullo se sono stati specificati coperti");
        }
        this.tipoAmbienteConExtra = tipoAmbienteConExtra;
    }

    public void setNumeroCoperti(Integer numeroCoperti) throws ValidazioneException {
        if (tipoAmbienteConExtra != null) {
            if (numeroCoperti == null) {
                throw new ValidazioneException("Numero di coperti non impostato per l'ambiente " + tipoAmbienteConExtra);
            }
            if (numeroCoperti <= 0) {
                throw new ValidazioneException("Il numero di coperti deve essere maggiore di zero");
            }
        }
        this.numeroCoperti = numeroCoperti;
    }

    public void setExtra(Set<Extra> extra) {
        this.extra = extra;
    }

    /* GETTER */
    public Set<Extra> getExtra() { return extra; }
    public TipoAmbienteConExtra getTipoAmbienteConExtra() { return tipoAmbienteConExtra; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
}
