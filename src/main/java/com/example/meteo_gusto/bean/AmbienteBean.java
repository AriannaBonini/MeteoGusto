package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import java.util.Set;


public class AmbienteBean {
    private String ambiente;
    private Integer numeroCoperti;
    private Set<Extra> extra;
    private String ristorante;

    public AmbienteBean() { /* COSTRUTTORE VUOTO */ }

    /* METODI GETTER E SETTER*/
    public String getTipoAmbiente() { return ambiente; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
    public Set<Extra> getExtra() { return extra; }
    public String getRistorante() { return ristorante; }


    /* METODI SETTER CON VALIDAZIONE */
    public void setAmbiente(String ambiente) throws ValidazioneException {
        if (ambiente == null) {
            throw new ValidazioneException("L'ambiente non può essere nullo.");
        }
        this.ambiente = ambiente;
    }

    public void setNumeroCoperti(Integer numeroCoperti) throws ValidazioneException {
        if (numeroCoperti == null || numeroCoperti <= 0) {
            throw new ValidazioneException("Il numero di coperti deve essere maggiore di zero.");
        }
        this.numeroCoperti = numeroCoperti;
    }

    public void setExtra(Set<Extra> extra) {
        this.extra = extra;
    }

    public void setRistorante(String ristorante) throws ValidazioneException {
        if (ristorante == null) {
            throw new ValidazioneException("Il ristorante non può essere nullo.");
        }
        this.ristorante = ristorante;
    }

}
