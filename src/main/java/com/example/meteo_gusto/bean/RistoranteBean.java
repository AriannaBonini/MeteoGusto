package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RistoranteBean {
    private String partitaIVA;
    private String nome;
    private String telefono;
    private String cucina;
    private String fasciaPrezzo;
    private BigDecimal mediaStelle;
    private GiorniEOrariBean orariApertura;
    private List<String > diete;
    private List<AmbienteBean> ambiente;
    private String citta;
    private String indirizzoCompleto;
    private String cap;

    public RistoranteBean() { /* COSTRUTTORE VUOTO */ }
    public RistoranteBean(String partitaIVA, String nome, String telefono, String cucina, String fasciaPrezzo, GiorniEOrariBean orariApertura, List<AmbienteBean> ambiente) {
        this.partitaIVA = partitaIVA;
        this.nome = nome;
        this.telefono = telefono;
        this.cucina = cucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.orariApertura = orariApertura;
        diete =new ArrayList<>();
        mediaStelle= BigDecimal.valueOf(0.0);
        this.ambiente=ambiente;
    }


    /* METODI GETTER E SETTER */
    public String getPartitaIVA() { return partitaIVA; }
    public String getNome() { return nome; }
    public String getTelefono() { return telefono; }
    public String getCucina() { return cucina; }
    public String getFasciaPrezzo() { return fasciaPrezzo; }
    public BigDecimal getMediaStelle() { return mediaStelle; }
    public GiorniEOrariBean getOrariApertura() { return orariApertura; }
    public List<String> getDiete() { return diete; }
    public void setDiete(List<String> diete) {this.diete = diete;}
    public void setMediaStelle(BigDecimal mediaStelle) {this.mediaStelle = mediaStelle;}
    public List<AmbienteBean> getAmbiente() {return ambiente;}
    public String getCitta() {return citta;}
    public String getIndirizzoCompleto() {return indirizzoCompleto;}
    public String getCap() {return cap;}


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


    public void setNome(String nome) throws ValidazioneException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidazioneException("Il nome del ristorante non può essere vuoto.");
        }
        if (!nome.matches("^[a-zA-Zàèéìòùçñ'\\s]+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere e spazi.");
        }
        this.nome = nome.trim();
    }

    public void setTelefono(String telefono) throws ValidazioneException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new ValidazioneException("Il numero di telefono non può essere vuoto.");
        }
        if (!telefono.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre).");
        }
        this.telefono = telefono.trim();
    }

    public void setCucina(String cucina) throws ValidazioneException {
        if (cucina == null) {
            throw new ValidazioneException("La cucina del ristorante è obbligatoria.");
        }
        this.cucina = cucina;
    }

    public void setFasciaPrezzo(String fasciaPrezzo) throws ValidazioneException {
        if (fasciaPrezzo == null) {
            throw new ValidazioneException("La fascia di prezzo è obbligatoria.");
        }
        this.fasciaPrezzo = fasciaPrezzo;
    }

    public void setOrariApertura(GiorniEOrariBean orariApertura) throws ValidazioneException {
        if (orariApertura == null) {
            throw new ValidazioneException("Gli orari non possono essere nulli.");
        }
        this.orariApertura = orariApertura;
    }

    public void setAmbiente(List<AmbienteBean> ambiente) throws ValidazioneException{
        if(ambiente ==null || ambiente.isEmpty()) {
            throw new ValidazioneException("Gli ambienti non possono essere nulli.");
        }
        this.ambiente = ambiente;}

    public void setIndirizzoCompleto(String indirizzoCompleto) throws ValidazioneException {
        if (indirizzoCompleto == null || indirizzoCompleto.trim().isEmpty()) {
            throw new ValidazioneException("Il campo indirizzo è obbligatorio.");
        }
        if (!indirizzoCompleto.matches(".+,\\s*\\d+")) {
            throw new ValidazioneException("L'indirizzo deve contenere una virgola seguita dal civico (es. Via Roma, 12).");
        }
        this.indirizzoCompleto = indirizzoCompleto.trim();
    }

    public void setCap(String cap) throws ValidazioneException {
        if (cap == null || cap.trim().isEmpty()) {
            throw new ValidazioneException("Il CAP non può essere vuoto.");
        }
        if (!cap.matches("^\\d{5}$")) {
            throw new ValidazioneException("Il CAP deve essere un numero di 5 cifre.");
        }
        this.cap = cap.trim();
    }

    public void setCitta(String citta) throws ValidazioneException {
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
        this.citta = citta.trim();
    }

}
