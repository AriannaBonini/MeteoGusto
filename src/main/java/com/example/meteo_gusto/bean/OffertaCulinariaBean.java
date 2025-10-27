package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;

public class OffertaCulinariaBean {

    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;

    /* COSTRUTTORE VUOTO */
    public OffertaCulinariaBean() { /* COSTRUTTORE VUOTO */ }

    /* SETTER CON VALIDAZIONE */
    public void setCucina(TipoCucina cucina) throws ValidazioneException {
        if (cucina == null) {
            throw new ValidazioneException("Il campo cucina è obbligatorio.");
        }
        this.cucina = cucina;
    }

    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) throws ValidazioneException {
        if (fasciaPrezzo == null) {
            throw new ValidazioneException("Il campo prezzo è obbligatorio.");
        }
        this.fasciaPrezzo = fasciaPrezzo;
    }

    /* GETTER */
    public TipoCucina getCucina() { return cucina; }
    public FasciaPrezzoRistorante getFasciaPrezzo() { return fasciaPrezzo; }
}

