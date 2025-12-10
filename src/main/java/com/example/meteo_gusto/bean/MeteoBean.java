package com.example.meteo_gusto.bean;


public class MeteoBean {
    private final Integer temperatura;
    private final String tempo;

    public MeteoBean(Integer temperatura, String tempo) {
        this.temperatura=temperatura;
        this.tempo=tempo;
    }


    /* METODI GETTER E SETTER */
    public int getTemperatura() {return temperatura;}
    public String getTempo() {return tempo;}
}
