package com.example.meteo_gusto.bean;


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
    private Map<TipoAmbiente, Integer> ambienteECoperti;
    private AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile;

    /* COSTRUTTORI CON PARAMETRI */
    public RegistrazioneRistoratoreBean() { /* COSTRUTTORE VUOTO */ }

    /* GETTER E SETTER */
    public RegistrazioneUtenteBean getProprietarioRistorante() {return proprietarioRistorante;}
    public void setProprietarioRistorante(RegistrazioneUtenteBean proprietarioRistorante) {this.proprietarioRistorante = proprietarioRistorante;}
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
    public Set<GiorniSettimana> getGiorniChiusura() {return giorniChiusura;}
    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}
    public Set<TipoDieta> getDieta() {return dieta;}
    public void setDieta(Set<TipoDieta> dieta) {this.dieta = dieta;}
    public Map<TipoAmbiente, Integer> getAmbienteECoperti() {return ambienteECoperti;}
    public void setAmbienteECoperti(Map<TipoAmbiente, Integer> ambienteECoperti) {this.ambienteECoperti = ambienteECoperti;}
    public AmbienteSpecialeDisponibileBean getAmbienteSpecialeDisponibile() {return ambienteSpecialeDisponibile;}
    public void setAmbienteSpecialeDisponibile(AmbienteSpecialeDisponibileBean ambienteSpecialeDisponibile) {this.ambienteSpecialeDisponibile = ambienteSpecialeDisponibile;}
}

