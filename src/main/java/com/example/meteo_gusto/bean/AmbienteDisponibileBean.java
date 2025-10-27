package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Map;


public class AmbienteDisponibileBean {

    private RistoranteBean ristorante;
    private Map<TipoAmbiente, Integer> ambienteDisponibile;
    private AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile;


    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteDisponibileBean(RistoranteBean ristorante, Map<TipoAmbiente, Integer> ambienteDisponibile, AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile) throws ValidazioneException{
        validaCampi(ambienteDisponibile, ambienteSpecialeDisponibile);

        this.ambienteDisponibile = ambienteDisponibile;
        this.ristorante=ristorante;
        this.ambienteSpecialeDisponibile=ambienteSpecialeDisponibile;
    }


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampi(Map<TipoAmbiente, Integer> ambienteECopertiRistorante, AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile) throws ValidazioneException {
        boolean haAmbientiNormali = ambienteECopertiRistorante != null && !ambienteECopertiRistorante.isEmpty();
        boolean haAmbienteSpeciale = ambienteSpecialeDisponibile != null;

        if (!haAmbientiNormali && !haAmbienteSpeciale) {
            throw new ValidazioneException("Deve esserci almeno un ambiente disponibile o un ambiente speciale disponibile");
        }

        if (haAmbientiNormali) {
            for (Map.Entry<TipoAmbiente, Integer> entry : ambienteECopertiRistorante.entrySet()) {
                Integer numeroCoperti = entry.getValue();
                if (numeroCoperti == null) {
                    throw new ValidazioneException("Numero di coperti non impostato per l'ambiente " + entry.getKey());
                }
                if (numeroCoperti <= 0) {
                    throw new ValidazioneException("Il numero di coperti per l'ambiente deve essere maggiore di zero");
                }
            }
        }
    }


    /* METODI GETTER E SETTER */
    public Map<TipoAmbiente, Integer> getAmbienteDisponibile() {return ambienteDisponibile;}
    public void setAmbienteDisponibile(Map<TipoAmbiente, Integer> ambienteDisponibile) {this.ambienteDisponibile = ambienteDisponibile;}
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public AmbienteSpecialeDisponibileBean getAmbienteSpecialeDisponibile() {return ambienteSpecialeDisponibile;}
    public void setAmbienteSpecialeDisponibile(AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile) {this.ambienteSpecialeDisponibile = ambienteSpecialeDisponibile;}
}
