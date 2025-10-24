package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.util.Set;

public class DietaBean {
    private RistoranteBean ristorante;
    private Set<TipoDieta> dieta;

    /* COSTRUTTORI CON PARAMETRI */
    public DietaBean(RistoranteBean ristorante, Set<TipoDieta> dieta) {
        this.ristorante=ristorante;
        this.dieta=dieta;
    }

    /* METODI GETTER E SETTER */
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public Set<TipoDieta> getDieta() {return dieta;}
    public void setDieta(Set<TipoDieta> dieta) {this.dieta = dieta;}
}
