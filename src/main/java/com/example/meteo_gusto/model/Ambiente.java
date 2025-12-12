package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Set;

public class Ambiente {
    private TipoAmbiente tipoAmbiente;
    private Integer numeroCoperti;
    private Set<Extra> extra;
    private String ristorante;
    private Integer idAmbiente;

    public Ambiente() { /* COSTRUTTORE VUOTO*/ }
    public Ambiente(TipoAmbiente tipoAmbiente, String ristorante, Integer numeroCoperti, Set<Extra> extra, Integer idAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
        this.numeroCoperti = numeroCoperti;
        this.ristorante = ristorante;
        this.idAmbiente=idAmbiente;
        aggiungiExtra(extra);
    }

    /* METODI GETTER E SETTER */
    public TipoAmbiente getTipoAmbiente() { return tipoAmbiente; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
    public Set<Extra> getExtra() { return extra; }
    public String  getRistorante() { return ristorante; }
    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) { this.tipoAmbiente = tipoAmbiente; }
    public void setRistorante(String ristorante) { this.ristorante = ristorante; }
    public Integer getIdAmbiente() {return idAmbiente;}
    public void setIdAmbiente(Integer idAmbiente) {this.idAmbiente = idAmbiente;}
    public void setNumeroCoperti(Integer numeroCoperti) {this.numeroCoperti = numeroCoperti;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    private void aggiungiExtra(Set<Extra> extra) {
        if (tipoAmbiente == TipoAmbiente.ESTERNO_COPERTO) {
            this.extra=extra;
        }
    }

}
