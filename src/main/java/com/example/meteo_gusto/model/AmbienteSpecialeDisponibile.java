package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbienteConExtra;
import java.util.Set;

public class AmbienteSpecialeDisponibile {
    private Set<Extra> extra;
    private TipoAmbienteConExtra tipoAmbienteConExtra;
    private Integer numeroCoperti;


    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteSpecialeDisponibile(Set<Extra> extra, TipoAmbienteConExtra tipoAmbienteConExtra, Integer numeroCoperti) {
        this.extra=extra;
        this.tipoAmbienteConExtra=tipoAmbienteConExtra;
        this.numeroCoperti=numeroCoperti;
    }

    /* METODI GETTER E SETTER */
    public Set<Extra> getExtra() {return extra;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public TipoAmbienteConExtra getTipoAmbienteConExtra() {return tipoAmbienteConExtra;}
    public void setTipoAmbienteConExtra(TipoAmbienteConExtra tipoAmbienteConExtra) {this.tipoAmbienteConExtra = tipoAmbienteConExtra;}
    public Integer getNumeroCoperti() {return numeroCoperti;}
    public void setNumeroCoperti(Integer numeroCoperti) {this.numeroCoperti = numeroCoperti;}
}
