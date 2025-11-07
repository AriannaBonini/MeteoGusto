package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.FasciaPrezzoRistorante;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class FiltriBean {
    private LocalDate data;
    private LocalTime ora;
    private String citta;
    private Integer numeroPersone;
    private FasciaPrezzoRistorante fasciaPrezzoRistorante;
    private Set<TipoCucina> tipoCucina;
    private Set<TipoDieta> tipoDieta;
    private boolean meteo;

    public FiltriBean() { /* COSTRUTTORE VUOTO */ }

    public FiltriBean(LocalDate data, LocalTime ora, String citta, Integer numeroPersone) {
        this.data = data;
        this.ora = ora;
        this.citta = citta;
        this.numeroPersone = numeroPersone;
    }

    /* METODI GETTER E SETTER */
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getCitta() { return citta; }
    public Integer getNumeroPersone() { return numeroPersone; }
    public FasciaPrezzoRistorante getFasciaPrezzoRistorante() { return fasciaPrezzoRistorante; }
    public Set<TipoCucina> getTipoCucina() { return tipoCucina; }
    public Set<TipoDieta> getTipoDieta() { return tipoDieta; }
    public boolean getMeteo() { return meteo; }
    public void setFasciaPrezzoRistorante(FasciaPrezzoRistorante fasciaPrezzoRistorante) {this.fasciaPrezzoRistorante = fasciaPrezzoRistorante;}
    public void setTipoCucina(Set<TipoCucina> tipoCucina) {this.tipoCucina = tipoCucina;}
    public void setTipoDieta(Set<TipoDieta> tipoDieta) {this.tipoDieta = tipoDieta;}
    public void setMeteo(boolean meteo) {this.meteo = meteo;}


    /* METODI SETTER CON VALIDAZIONE */
    public void setData(LocalDate data) throws ValidazioneException {
        if (data == null) {
            throw new ValidazioneException("Data non valida. Usa il formato GG/MM/AAAA");
        }
        this.data = data;
    }

    public void setOra(LocalTime ora) throws ValidazioneException {
        if (ora == null) {
            throw new ValidazioneException("L'orario della prenotazione non può essere nullo.");
        }
        this.ora = ora;
    }

    public void setCitta(String citta) throws ValidazioneException {
        if (citta == null || citta.trim().isEmpty()) {
            throw new ValidazioneException("La città non può essere vuota.");
        }
        if (!citta.matches("^[a-zA-Zàèéìòùçñ' ]+$")) {
            throw new ValidazioneException("La città può contenere solo lettere e spazi.");
        }
        this.citta = citta;
    }

    public void setNumeroPersone(Integer numeroPersone) throws ValidazioneException {
        if (numeroPersone == null || numeroPersone <= 0) {
            throw new ValidazioneException("Il numero di persone deve essere maggiore di zero.");
        }
        this.numeroPersone = numeroPersone;
    }

}

