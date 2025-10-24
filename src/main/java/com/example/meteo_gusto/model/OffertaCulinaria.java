package com.example.meteo_gusto.model;

import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;

public class OffertaCulinaria {
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;

    /* COSTRUTTORI CON PARAMETRI */
    public OffertaCulinaria(TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo) {
        this.cucina=cucina;
        this.fasciaPrezzo=fasciaPrezzo;
    }

    /* METODI GETTER E SETTER */
    public TipoCucina getCucina() {return cucina;}
    public void setCucina(TipoCucina cucina) {this.cucina = cucina;}
    public FasciaPrezzoRistorante getFasciaPrezzo() {return fasciaPrezzo;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}

}
