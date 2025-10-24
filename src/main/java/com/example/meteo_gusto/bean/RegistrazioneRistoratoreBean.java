package com.example.meteo_gusto.bean;


public class RegistrazioneRistoratoreBean {
    private RegistrazioneUtenteBean proprietarioRistorante;
    private RistoranteBean datiRistorante;
    private GiorniChiusuraBean giorniChiusuraBean;
    private DietaBean dietaRistorante;
    private AmbienteECopertiBean ambienteECoperti;

    /* COSTRUTTORE CON PARAMETRI */
    public RegistrazioneRistoratoreBean(RegistrazioneUtenteBean proprietarioRistorante, RistoranteBean datiRistorante, GiorniChiusuraBean giorniChiusuraBean, DietaBean dietaRistorante, AmbienteECopertiBean ambienteECoperti) {
        this.proprietarioRistorante = proprietarioRistorante;
        this.datiRistorante = datiRistorante;
        this.giorniChiusuraBean = giorniChiusuraBean;
        this.dietaRistorante = dietaRistorante;
        this.ambienteECoperti = ambienteECoperti;
    }

    /* METODI GETTER E SETTER */
    public RegistrazioneUtenteBean getDatiRistoratore() {return proprietarioRistorante;}
    public void setDatiRistoratore(RegistrazioneUtenteBean proprietarioRistorante) {this.proprietarioRistorante = proprietarioRistorante;}
    public RistoranteBean getDatiRistorante() {return datiRistorante;}
    public void setDatiRistorante(RistoranteBean datiRistorante) {this.datiRistorante = datiRistorante;}
    public GiorniChiusuraBean getGiorniChiusuraBean() {return giorniChiusuraBean;}
    public void setGiorniChiusuraBean(GiorniChiusuraBean giorniChiusuraBean) {this.giorniChiusuraBean = giorniChiusuraBean;}
    public DietaBean getDietaRistorante() {return dietaRistorante;}
    public void setDietaRistorante(DietaBean dietaRistorante) {this.dietaRistorante = dietaRistorante;}
    public AmbienteECopertiBean getAmbienteECoperti() {return ambienteECoperti;}
    public void setAmbienteECoperti(AmbienteECopertiBean ambienteECoperti) {this.ambienteECoperti = ambienteECoperti;}
}
