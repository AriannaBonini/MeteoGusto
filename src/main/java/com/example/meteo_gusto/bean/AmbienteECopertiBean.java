package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Map;
import java.util.Set;


public class AmbienteECopertiBean {

    private Map<TipoAmbiente, Integer> ambienteECopertiRistorante;
    private Set<Extra> extra;

    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteECopertiBean(Map<TipoAmbiente, Integer> ambienteECopertiRistorante, Set<Extra> extra) throws ValidazioneException{
        validaCampi(ambienteECopertiRistorante);

        this.ambienteECopertiRistorante=ambienteECopertiRistorante;
        this.extra=extra;
    }


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampi(Map<TipoAmbiente, Integer> ambienteECopertiRistorante) throws ValidazioneException {
        if (ambienteECopertiRistorante == null || ambienteECopertiRistorante.isEmpty()) {
            throw new ValidazioneException("Deve esserci almeno un ambiente con il relativo numero di coperti");
        }
        for (Map.Entry<TipoAmbiente, Integer> entry : ambienteECopertiRistorante.entrySet()) {
            Integer numeroCoperti = entry.getValue();
            if (numeroCoperti == null) {
                throw new ValidazioneException("Numero di coperti non impostato per l'ambiente " + entry.getKey());
            }
            if (numeroCoperti <= 0) {
                throw new ValidazioneException("Il numero di coperti per l'ambiente " + entry.getKey() + " deve essere maggiore di zero");
            }
        }

    }


    /* METODI GETTER E SETTER */
    public Set<Extra> getExtra() {return extra;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public Map<TipoAmbiente, Integer> getAmbienteECopertiRistorante() {return ambienteECopertiRistorante;}
    public void setAmbienteECopertiRistorante(Map<TipoAmbiente, Integer> ambienteECopertiRistorante) {this.ambienteECopertiRistorante = ambienteECopertiRistorante;}
}
