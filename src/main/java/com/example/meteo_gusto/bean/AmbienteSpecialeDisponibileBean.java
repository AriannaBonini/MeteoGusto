package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbienteConExtra;
import java.util.Set;

public class AmbienteSpecialeDisponibileBean {
    private Set<Extra> extra;
    private TipoAmbienteConExtra tipoAmbienteConExtra;
    private Integer numeroCoperti;


    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteSpecialeDisponibileBean(Set<Extra> extra, TipoAmbienteConExtra tipoAmbienteConExtra, Integer numeroCoperti) throws ValidazioneException{
        validaCampi(tipoAmbienteConExtra,numeroCoperti);

        this.extra=extra;
        this.tipoAmbienteConExtra=tipoAmbienteConExtra;
        this.numeroCoperti=numeroCoperti;
    }


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampi(TipoAmbienteConExtra tipoAmbienteConExtra, Integer numeroCoperti) throws ValidazioneException {
        if(tipoAmbienteConExtra!=null) {
            if(numeroCoperti==null) {
                throw new ValidazioneException("Numero di coperti non impostato per l'ambiente " + tipoAmbienteConExtra);
            }
            if(numeroCoperti<=0) {
                throw new ValidazioneException("Il numero di coperti deve essere maggiore di zero");
            }
        }
    }


    /* METODI GETTER E SETTER */
    public Set<Extra> getExtra() {return extra;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public TipoAmbienteConExtra getTipoAmbienteConExtra() {return tipoAmbienteConExtra;}
    public void setTipoAmbienteConExtra(TipoAmbienteConExtra tipoAmbienteConExtra) {this.tipoAmbienteConExtra = tipoAmbienteConExtra;}
    public Integer getNumeroCoperti() {return numeroCoperti;}
    public void setNumeroCoperti(Integer numeroCoperti) {this.numeroCoperti = numeroCoperti;}
}
