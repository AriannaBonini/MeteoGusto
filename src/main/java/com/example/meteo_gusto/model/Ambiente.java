package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Set;

public class Ambiente {
    private TipoAmbiente tipoAmbiente;
    private Integer numeroCoperti;
    private Set<Extra> extra;
    private Ristorante ristorante;
    private Integer idAmbiente;

    /* COSTRUTTORE SEMPLICE */
    public Ambiente(TipoAmbiente tipoAmbiente, Ristorante ristorante, Integer numeroCoperti, Set<Extra> extra) {
        this.tipoAmbiente = tipoAmbiente;
        this.numeroCoperti = numeroCoperti;
        this.ristorante = ristorante;
        aggiungiExtra(extra);
    }

    /* METODI GETTER E SETTER */
    public TipoAmbiente getTipoAmbiente() { return tipoAmbiente; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
    public Set<Extra> getExtra() { return extra; }
    public Ristorante getRistorante() { return ristorante; }
    public Integer getIdAmbiente() { return idAmbiente; }
    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) { this.tipoAmbiente = tipoAmbiente; }
    public void setNumeroCoperti(Integer numeroCoperti) { this.numeroCoperti = numeroCoperti; }
    public void setRistorante(Ristorante ristorante) { this.ristorante = ristorante; }
    public void setIdAmbiente(Integer idAmbiente) { this.idAmbiente = idAmbiente; }


    /* METODO DI LOGICA DI DOMINIO */
    private void aggiungiExtra(Set<Extra> extra) {
        if (tipoAmbiente == TipoAmbiente.ESTERNO_COPERTO) {
            this.extra=extra;
        }
    }

    public boolean supportaExtra() {
        return this.tipoAmbiente == TipoAmbiente.ESTERNO_COPERTO;
    }



}
