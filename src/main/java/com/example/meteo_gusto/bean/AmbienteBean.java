package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Set;


public class AmbienteBean {

    private TipoAmbiente ambiente;
    private Integer numeroCoperti;
    private Set<Extra> extra;
    private RistoranteBean ristorante;

    public AmbienteBean() { /* COSTRUTTORE VUOTO */ }

    public AmbienteBean(TipoAmbiente ambiente, Integer numeroCoperti, Set<Extra> extra, RistoranteBean ristorante) throws ValidazioneException {
       validaAmbiente(ambiente);
       validaNumeroCoperti(numeroCoperti);
       validaRistorante(ristorante);

        this.ambiente=ambiente;
        this.numeroCoperti=numeroCoperti;
        this.extra=extra;
        this.ristorante=ristorante;
    }



    /* METODI DI VALIDAZIONE SINTATTICA */
    private void validaAmbiente(TipoAmbiente ambiente) throws ValidazioneException{
        if (ambiente == null) {
            throw new ValidazioneException("L'ambiente non può essere nullo.");
        }
    }

    private void validaRistorante(RistoranteBean ristorante) throws ValidazioneException{
        if (ristorante == null) {
            throw new ValidazioneException("Il ristorante non può essere nullo.");
        }
    }


    private void validaNumeroCoperti(Integer numeroCoperti) throws ValidazioneException, NumberFormatException{
        if (numeroCoperti == null || numeroCoperti <= 0) {
            throw new ValidazioneException("Il numero di coperti deve essere maggiore di zero.");
        }
    }



    /* METODI GETTER E SETTER  */
    public TipoAmbiente getAmbiente() { return ambiente; }
    public Integer getNumeroCoperti() { return numeroCoperti; }
    public Set<Extra> getExtra() { return extra; }
    public RistoranteBean getRistorante() {return ristorante;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public void setAmbiente(TipoAmbiente ambiente) {this.ambiente = ambiente;}
    public void setNumeroCoperti(Integer numeroCoperti){this.numeroCoperti = numeroCoperti;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
}
