package com.example.meteo_gusto.bean;


public class MeteoBean {
    private Integer temperatura;
    private String tempo;

    public MeteoBean(Integer temperatura, String tempo) {
        this.temperatura=temperatura;
        this.tempo=tempo;
    }


    /* METODI GETTER E SETTER */
    public int getTemperatura() {return temperatura;}
    public void setTemperatura(int temperatura) {this.temperatura = temperatura;}
    public String getTempo() {return tempo;}
    public void setTempo(String tempo) {this.tempo = tempo;}
}
