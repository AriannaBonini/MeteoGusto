package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.TipoAmbiente;

import java.util.Map;

public class AmbienteDisponibile {
    private Map<TipoAmbiente,Integer> ambienteDisponibile;
    private Ristorante ristorante;
    private AmbienteSpecialeDisponibile ambienteSpecialeDisponibile;


    /* COSTRUTTORE CON PARAMETRI */
    public AmbienteDisponibile(Map<TipoAmbiente,Integer> ambienteDisponibile, Ristorante ristorante, AmbienteSpecialeDisponibile ambienteSpecialeDisponibile) {
        this.ambienteDisponibile = ambienteDisponibile;
        this.ristorante=ristorante;
        this.ambienteSpecialeDisponibile=ambienteSpecialeDisponibile;
    }



    /* METODI GETTER E SETTER */
    public Map<TipoAmbiente,Integer> getAmbienteDisponibile() {return ambienteDisponibile;}
    public void setAmbienteDisponibile(Map<TipoAmbiente,Integer> ambienteDisponibile) {this.ambienteDisponibile = ambienteDisponibile;}
    public Ristorante getRistorante() {return ristorante;}
    public void setRistorante(Ristorante ristorante) {this.ristorante = ristorante;}
    public AmbienteSpecialeDisponibile getAmbienteSpecialeDisponibile() {return ambienteSpecialeDisponibile;}
    public void setAmbienteSpecialeDisponibile(AmbienteSpecialeDisponibile ambienteSpecialeDisponibile) {this.ambienteSpecialeDisponibile = ambienteSpecialeDisponibile;}
}

