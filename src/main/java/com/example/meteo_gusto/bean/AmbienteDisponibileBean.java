package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Map;
import java.util.Objects;


public class AmbienteDisponibileBean {

    private RistoranteBean ristorante;
    private Map<TipoAmbiente, Integer> ambienteDisponibile;
    private AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile;


    public AmbienteDisponibileBean() { /* COSTRUTTORE VUOTO */ }


    /* SETTER CON VALIDAZIONE */
    public void setRistorante(RistoranteBean ristorante) throws ValidazioneException{
        if (ristorante == null) {
            throw new ValidazioneException("Il ristorante non può essere nullo.");
        }
        this.ristorante = ristorante;
    }

    public void setAmbienteDisponibile(Map<TipoAmbiente, Integer> ambienteDisponibile) throws ValidazioneException{
        if ((ambienteDisponibile == null || ambienteDisponibile.isEmpty()) && this.ambienteSpecialeDisponibile == null) {
            throw new ValidazioneException("Deve esserci almeno un ambiente disponibile o un ambiente speciale disponibile.");
        }

        for (Map.Entry<TipoAmbiente, Integer> entry : Objects.requireNonNull(ambienteDisponibile).entrySet()){
            Integer numeroCoperti = entry.getValue();
            if (numeroCoperti == null || numeroCoperti <= 0) {
                throw new ValidazioneException("Il numero di coperti per l'ambiente " + entry.getKey() + " deve essere maggiore di zero.");
            }
        }

        this.ambienteDisponibile = ambienteDisponibile;
    }

    public void setAmbienteSpecialeDisponibile(AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile) throws ValidazioneException {
        if ((this.ambienteDisponibile == null || this.ambienteDisponibile.isEmpty()) && ambienteSpecialeDisponibile == null) {
            throw new ValidazioneException("Deve esserci almeno un ambiente disponibile o un ambiente speciale disponibile.");
        }
        this.ambienteSpecialeDisponibile = ambienteSpecialeDisponibile;
    }

    /* GETTER */
    public Map<TipoAmbiente, Integer> getAmbienteDisponibile() { return ambienteDisponibile; }
    public RistoranteBean getRistorante() { return ristorante; }
    public AmbienteSpecialeDisponibileBean getAmbienteSpecialeDisponibile() { return ambienteSpecialeDisponibile; }
}
