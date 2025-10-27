package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import java.util.Map;

public class AmbienteDisponibile {
    private Map<TipoAmbiente,Integer> ambienteECoperti;
    private Ristorante ristorante;
    private AmbienteSpecialeDisponibile ambienteSpecialeDisponibile;


    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteDisponibile(Map<TipoAmbiente,Integer> ambienteECoperti, Ristorante ristorante, AmbienteSpecialeDisponibile ambienteSpecialeDisponibile) {
        this.ambienteECoperti = ambienteECoperti;
        this.ristorante=ristorante;
        this.ambienteSpecialeDisponibile=ambienteSpecialeDisponibile;
    }



    /* METODI GETTER E SETTER */
    public Map<TipoAmbiente,Integer> getAmbienteECoperti() {return ambienteECoperti;}
    public void setAmbienteECoperti(Map<TipoAmbiente,Integer> ambienteECoperti) {this.ambienteECoperti = ambienteECoperti;}
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public AmbienteSpecialeDisponibile getAmbienteSpecialeDisponibile() {return ambienteSpecialeDisponibile;}
    public void setAmbienteSpecialeDisponibile(AmbienteSpecialeDisponibile ambienteSpecialeDisponibile) {this.ambienteSpecialeDisponibile = ambienteSpecialeDisponibile;}
}

