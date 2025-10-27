package com.example.meteo_gusto.bean;


import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.util.Map;
import java.util.Set;


public class RegistrazioneRistoratoreBean {

    private RegistrazioneUtenteBean proprietarioRistorante;
    private RistoranteBean ristorante;
    private Set<GiorniSettimana> giorniChiusura;
    private Set<TipoDieta> dieta;
    private Map<TipoAmbiente, Integer> disponibilita;
    private AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibileBean;

    /* COSTRUTTORI CON PARAMETRI */
    public RegistrazioneRistoratoreBean( RegistrazioneUtenteBean proprietarioRistorante, RistoranteBean ristorante, Set<GiorniSettimana> giorniChiusura, Set<TipoDieta> dieta, Map<TipoAmbiente, Integer> disponibilita, AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibileBean) {
        this.proprietarioRistorante = proprietarioRistorante;
        this.ristorante = ristorante;
        this.giorniChiusura = giorniChiusura;
        this.dieta = dieta;
        this.disponibilita = disponibilita;
        this.ambienteSpecialeDisponibileBean=ambienteSpecialeDisponibileBean;
    }

    /* GETTER E SETTER */
    public RegistrazioneUtenteBean getProprietarioRistorante() {return proprietarioRistorante;}
    public void setProprietarioRistorante(RegistrazioneUtenteBean proprietarioRistorante) {this.proprietarioRistorante = proprietarioRistorante;}
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public Set<GiorniSettimana> getGiorniChiusura() {return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}
    public Set<TipoDieta> getDieta() {return dieta;}
    public void setDieta(Set<TipoDieta> dieta) {this.dieta = dieta;}
    public Map<TipoAmbiente, Integer> getDisponibilita() {return disponibilita;}
    public void setDisponibilita(Map<TipoAmbiente, Integer> disponibilita) {this.disponibilita = disponibilita;}
    public AmbienteSpecialeDisponibileBean getAmbienteSpecialeDisponibileBean() {return ambienteSpecialeDisponibileBean;}
    public void setAmbienteSpecialeDisponibileBean(AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibileBean) {this.ambienteSpecialeDisponibileBean = ambienteSpecialeDisponibileBean;}
}

