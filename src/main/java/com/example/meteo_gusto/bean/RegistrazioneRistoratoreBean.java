package com.example.meteo_gusto.bean;


import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.util.List;
import java.util.Set;


public class RegistrazioneRistoratoreBean {

    private Set<GiorniSettimana> giorniChiusura;
    private Set<TipoDieta> dieta;
    private boolean maggiorenne;
    private boolean accettaTermini;
    private List<AmbienteBean> ambiente;
    private RistoranteBean ristorante;

    public RegistrazioneRistoratoreBean() {   /* COSTRUTTORE VUOTO */ }


    /* METODI SETTER CON VALIDAZIONE */

    public void setGiorniChiusura(Set<GiorniSettimana> giorniChiusura) {this.giorniChiusura = giorniChiusura;}
    public void setDieta(Set<TipoDieta> dieta) {this.dieta = dieta;}

    public void setMaggiorenne(boolean maggiorenne) throws ValidazioneException {
        if (!maggiorenne) {
            throw new ValidazioneException("È necessario essere maggiorenni per registrarsi.");
        }
        this.maggiorenne = true;
    }

    public void setAccettaTermini(boolean accettaTermini) throws ValidazioneException {
        if (!accettaTermini) {
            throw new ValidazioneException("È necessario accettare i termini e le condizioni.");
        }
        this.accettaTermini = true;
    }

    /* METODI GETTER E SETTER */

    public Set<GiorniSettimana> getGiorniChiusura() { return giorniChiusura; }
    public Set<TipoDieta> getDieta() { return dieta; }
    public boolean getMaggiorenne() { return maggiorenne; }
    public boolean getAccettaTermini() { return accettaTermini; }
    public List<AmbienteBean> getAmbiente() {return ambiente;}
    public void setAmbiente(List<AmbienteBean> ambiente) {this.ambiente = ambiente;}
    public RistoranteBean getRistorante() {return ristorante;}
    public void setRistorante(RistoranteBean ristorante) {this.ristorante = ristorante;}
}
