package com.example.meteo_gusto.model;


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
    private FasciaPrezzoRistorante fasciaPrezzo;
    private Set<TipoCucina> cucine;
    private Set<TipoDieta> diete;
    private boolean meteo;


    /* Costruttore per creare un oggetto Filtro con i dati della prenotazione */
    public Filtro(LocalDate data, LocalTime ora, String citta, Integer numeroPersone) {
        this.data=data;
        this.ora=ora;
        this.citta=citta;
        this.numeroPersone=numeroPersone;
    }


    /* Costruttore per creare un oggetto Filtro con i filtri per i ristoranti */
    public Filtro(FasciaPrezzoRistorante fasciaPrezzo, Set<TipoCucina> cucine, Set<TipoDieta> diete) {
        this.fasciaPrezzo=fasciaPrezzo;
        this.cucine=cucine;
        this.diete=diete;
    }


    /* METODI GETTER E SETTER */
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getCitta() { return citta; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public void setOra(LocalTime ora) { this.ora = ora; }
    public void setCitta(String citta) { this.citta = citta; }
    public FasciaPrezzoRistorante getFasciaPrezzo() {return fasciaPrezzo;}
    public Set<TipoCucina> getCucine() {return cucine;}
    public Set<TipoDieta> getDiete() {return diete;}
    public void setDiete(Set<TipoDieta> diete) {this.diete = diete;}
    public boolean getMeteo() {return meteo;}
    public void setMeteo(boolean meteo) {this.meteo = meteo;}
    public void setNumeroPersone(Integer numeroPersone) {this.numeroPersone = numeroPersone;}
    public void setData(LocalDate data) {this.data = data;}

}
