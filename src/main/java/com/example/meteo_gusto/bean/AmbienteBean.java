package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Set;


public class AmbienteBean {
    private TipoAmbiente ambiente;
    private Integer numeroCoperti;
    private Set<Extra> extra;
    private String ristorante;
    private String nomeRistorante;
    private String indirizzoCompletoRistorante;
    private String cittaRistorante;
    private Integer ambienteId;

    public AmbienteBean() { /* COSTRUTTORE VUOTO */ }

    public AmbienteBean(TipoAmbiente ambiente, Integer numeroCoperti, Set<Extra> extra, String ristorante) {
        this.ambiente = ambiente;
        this.numeroCoperti = numeroCoperti;
        this.extra = extra;
        this.ristorante = ristorante;
    }

    /* METODI GETTER */
    public TipoAmbiente getAmbiente() { return ambiente; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
    public Set<Extra> getExtra() { return extra; }
    public String getRistorante() { return ristorante; }

    /* METODI SETTER CON VALIDAZIONE */
    public void setAmbiente(TipoAmbiente ambiente) throws ValidazioneException {
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

    public String getNomeRistorante() {return nomeRistorante;}
    public void setNomeRistorante(String nomeRistorante) {this.nomeRistorante = nomeRistorante;}
    public String getIndirizzoCompletoRistorante() {return indirizzoCompletoRistorante;}
    public void setIndirizzoCompletoRistorante(String indirizzoCompletoRistorante) {this.indirizzoCompletoRistorante = indirizzoCompletoRistorante;}
    public String getCittaRistorante() {return cittaRistorante;}
    public void setCittaRistorante(String cittaRistorante) {this.cittaRistorante = cittaRistorante;}
    public Integer getAmbienteId() {return ambienteId;}
    public void setAmbienteId(Integer ambienteId) {this.ambienteId = ambienteId;}
}
