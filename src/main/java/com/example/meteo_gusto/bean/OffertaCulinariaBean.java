package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;

public class OffertaCulinariaBean {

    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;

    /* COSTRUTTORI CON PARAMETRI */
    public OffertaCulinariaBean(TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo) throws ValidazioneException{
        validaCampoCucinaNonVuoto(cucina);
        validaCampoPrezzoNonVuoto(fasciaPrezzo);

        this.cucina=cucina;
        this.fasciaPrezzo=fasciaPrezzo;
    }

    /* METODI GETTER E SETTER */
    public TipoCucina getCucina() {return cucina;}
    public void setCucina(TipoCucina cucina) {this.cucina = cucina;}
    public FasciaPrezzoRistorante getFasciaPrezzo() {return fasciaPrezzo;}
    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) {this.fasciaPrezzo = fasciaPrezzo;}


    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampoCucinaNonVuoto(TipoCucina cucina) throws ValidazioneException {
        if (cucina==null) {
            throw new ValidazioneException("Il campo cucina è obbligatorio");
        }
    }
    private void validaCampoPrezzoNonVuoto(FasciaPrezzoRistorante fasciaPrezzo) throws ValidazioneException {
        if (fasciaPrezzo==null) {
            throw new ValidazioneException("Il campo prezzo è obbligatorio");
        }
    }
}
