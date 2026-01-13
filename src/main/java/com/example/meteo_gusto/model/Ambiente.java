package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.Extra;
import com.example.meteo_gusto.enumerazione.TipoAmbiente;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ambiente {
    private TipoAmbiente categoria;
    private Integer coperti;
    private Set<Extra> extra;
    private String ristorante;
    private Integer idAmbiente;
    private List<Prenotazione> prenotazioniAttive;

    public Ambiente() { /* COSTRUTTORE VUOTO*/ }

    public Ambiente(TipoAmbiente categoria, Set<Extra> extra) {
        this.categoria = categoria;
        this.extra=extra;
        this.prenotazioniAttive=new ArrayList<>();
    }


    /* Costruttore per creare un oggetto Ambiente non speciale (senza extra) */
    public Ambiente(TipoAmbiente categoria, String ristorante, Integer coperti) {
        this.categoria = categoria;
        this.coperti = coperti;
        this.ristorante = ristorante;
        this.extra=Set.of();
        this.prenotazioniAttive=new ArrayList<>();
    }

    /* Costruttore per creare un oggetto Ambiente speciale (con extra opzionali) */
    public Ambiente(String ristorante, Integer coperti, Set<Extra> extra) {
        this.categoria = TipoAmbiente.ESTERNO_COPERTO;
        this.coperti = coperti;
        this.ristorante = ristorante;
        this.extra=extra;
        this.prenotazioniAttive=new ArrayList<>();
    }

    public Set<Extra> getExtra() { return extra; }
    public String  getRistorante() { return ristorante; }
    public void setCategoria(TipoAmbiente categoria) { this.categoria = categoria; }
    public void setRistorante(String ristorante) { this.ristorante = ristorante; }
    public Integer getIdAmbiente() {return idAmbiente;}
    public void setIdAmbiente(Integer idAmbiente) {this.idAmbiente = idAmbiente;}
    public void setCoperti(Integer coperti) {this.coperti = coperti;}
    public void setExtra(Set<Extra> extra) {this.extra = extra;}
    public List<Prenotazione> prenotazioniAttive() {return prenotazioniAttive;}
    public void aggiungiPrenotazioniAttive(List<Prenotazione> prenotazioniAttive) {this.prenotazioniAttive=prenotazioniAttive;}
    public TipoAmbiente categoriaAmbiente() { return categoria; }
    public Integer numeroCoperti() { return coperti; }
}
