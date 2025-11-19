package com.example.meteo_gusto.model;


import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class Filtro {
    private LocalDate data;
    private LocalTime ora;
    private String citta;
    private Integer numeroPersone;
    private final FasciaPrezzoRistorante fasciaPrezzoRistorante;
    private final Set<TipoCucina> tipoCucina;
    private Set<TipoDieta> tipoDieta;
    private boolean meteo;

    /* COSTRUTTORE SEMPLICE */
    public Filtro(LocalTime ora, String citta,FasciaPrezzoRistorante fasciaPrezzoRistorante, Set<TipoCucina> tipoCucina, Set<TipoDieta> tipoDieta, boolean meteo) {
        this.ora = ora;
        this.citta = citta;
        this.fasciaPrezzoRistorante=fasciaPrezzoRistorante;
        this.tipoCucina=tipoCucina;
        this.tipoDieta=tipoDieta;
        this.meteo=meteo;
    }

    /* METODI GETTER E SETTER */
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getCitta() { return citta; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public void setOra(LocalTime ora) { this.ora = ora; }
    public void setCitta(String citta) { this.citta = citta; }
    public FasciaPrezzoRistorante getFasciaPrezzoRistorante() {return fasciaPrezzoRistorante;}
    public Set<TipoCucina> getTipoCucina() {return tipoCucina;}
    public Set<TipoDieta> getTipoDieta() {return tipoDieta;}
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
    public boolean getMeteo() {return meteo;}
    public void setMeteo(boolean meteo) {this.meteo = meteo;}


    /* METODI PER LA LOGICA DI DOMINIO */
    public void aggiungiData(LocalDate data) throws ValidazioneException {
        if (data == null || data.isBefore(LocalDate.now())) {
            throw new ValidazioneException("La data della prenotazione deve essere presente e non nel passato.");
        }
        this.data=data;
    }


    public void aggiungiNumeroPersone(Integer numeroPersone) throws IllegalArgumentException {
        if (numeroPersone == null || numeroPersone <= 0 || numeroPersone > 30) {
            throw new IllegalArgumentException("Il numero di persone deve essere compreso tra 1 e 30.");
        }
        this.numeroPersone=numeroPersone;
    }


}
