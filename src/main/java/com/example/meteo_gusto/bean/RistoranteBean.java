package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RistoranteBean {
    private String partitaIVA;
    private String nomeRistorante;
    private String telefonoRistorante;
    private TipoCucina cucina;
    private FasciaPrezzoRistorante fasciaPrezzo;
    private BigDecimal mediaStelle;
    private PosizioneBean posizione;
    private GiorniEOrariBean giorniEOrari;
    private Set<TipoDieta> tipoDieta;
    private List<AmbienteBean> ambiente;

    public RistoranteBean() { /* COSTRUTTORE VUOTO */ }
    public RistoranteBean(String partitaIVA, String nomeRistorante, String telefonoRistorante, TipoCucina cucina, FasciaPrezzoRistorante fasciaPrezzo, GiorniEOrariBean giorniEOrari, List<AmbienteBean> ambiente) {
        this.partitaIVA = partitaIVA;
        this.nomeRistorante = nomeRistorante;
        this.telefonoRistorante = telefonoRistorante;
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.giorniEOrari = giorniEOrari;
        tipoDieta=new HashSet<>();
        mediaStelle= BigDecimal.valueOf(0.0);
        this.ambiente=ambiente;
    }


    /* GETTER */
    public String getPartitaIVA() { return partitaIVA; }
    public String getNomeRistorante() { return nomeRistorante; }
    public String getTelefonoRistorante() { return telefonoRistorante; }
    public TipoCucina getCucina() { return cucina; }
    public FasciaPrezzoRistorante getFasciaPrezzo() { return fasciaPrezzo; }
    public BigDecimal getMediaStelle() { return mediaStelle; }
    public PosizioneBean getPosizione() { return posizione; }
    public GiorniEOrariBean getGiorniEOrari() { return giorniEOrari; }
    public Set<TipoDieta> getTipoDieta() { return tipoDieta; }
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public List<AmbienteBean> getAmbiente() {return ambiente;}

    /* SETTER CON VALIDAZIONE */
    public void setPartitaIVA(String partitaIVA) throws ValidazioneException {
        if (partitaIVA == null || partitaIVA.trim().isEmpty()) {
            throw new ValidazioneException("La partita IVA non può essere vuota.");
        }
        if (!partitaIVA.matches("^0\\d{10}$")) {
            throw new ValidazioneException("La partita IVA deve contenere 11 cifre e iniziare con 0.");
        }
        this.partitaIVA = partitaIVA.trim();
    }


    public void setNomeRistorante(String nomeRistorante) throws ValidazioneException {
        if (nomeRistorante == null || nomeRistorante.trim().isEmpty()) {
            throw new ValidazioneException("Il nome del ristorante non può essere vuoto.");
        }
        if (!nomeRistorante.matches("^[a-zA-Zàèéìòùçñ'\\s]+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere e spazi.");
        }
        this.nomeRistorante = nomeRistorante.trim();
    }

    public void setTelefonoRistorante(String telefonoRistorante) throws ValidazioneException {
        if (telefonoRistorante == null || telefonoRistorante.trim().isEmpty()) {
            throw new ValidazioneException("Il numero di telefono non può essere vuoto.");
        }
        if (!telefonoRistorante.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre).");
        }
        this.telefonoRistorante = telefonoRistorante.trim();
    }

    public void setCucina(TipoCucina cucina) throws ValidazioneException {
        if (cucina == null) {
            throw new ValidazioneException("La cucina del ristorante è obbligatoria.");
        }
        this.cucina = cucina;
    }

    public void setFasciaPrezzo(FasciaPrezzoRistorante fasciaPrezzo) throws ValidazioneException {
        if (fasciaPrezzo == null) {
            throw new ValidazioneException("La fascia di prezzo è obbligatoria.");
        }
        this.fasciaPrezzo = fasciaPrezzo;
    }

    public void setGiorniEOrari(GiorniEOrariBean giorniEOrari) throws ValidazioneException {
        if (giorniEOrari == null) {
            throw new ValidazioneException("Gli orari non possono essere nulli.");
        }
        this.giorniEOrari = giorniEOrari;
    }

    public void setAmbiente(List<AmbienteBean> ambiente) throws ValidazioneException{
        if(ambiente ==null || ambiente.isEmpty()) {
            throw new ValidazioneException("Gli ambienti non possono essere nulli.");
        }
        this.ambiente = ambiente;}

    public void setPosizione(PosizioneBean posizione) {this.posizione = posizione;}


}
