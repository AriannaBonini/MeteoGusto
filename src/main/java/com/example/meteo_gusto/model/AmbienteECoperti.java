package com.example.meteo_gusto.model;



import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Map;
import java.util.Set;

public class AmbienteECoperti {
    private Map<TipoAmbiente, Integer> ambienteECopertiRistorante;
    private Set<Extra> extra;

    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteECoperti(Map<TipoAmbiente, Integer> ambienteECopertiRistorante, Set<Extra> extra) {
        this.ambienteECopertiRistorante=ambienteECopertiRistorante;
        this.extra=extra;
    }



    /* METODI GETTER E SETTER */
    public Set<Extra> getExtra() {return extra;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public Map<TipoAmbiente, Integer> getAmbienteECopertiRistorante() {return ambienteECopertiRistorante;}
    public void setAmbienteECopertiRistorante(Map<TipoAmbiente, Integer> ambienteECopertiRistorante) {this.ambienteECopertiRistorante = ambienteECopertiRistorante;}
}

