package com.example.meteo_gusto.sessione;

public abstract class Sessione {
    private static boolean persistenza;
    private static boolean versione;

    public static void setPersistenza(boolean tipoPersistenza) { persistenza = tipoPersistenza; }
    public static void setVersione(boolean tipoVersione) { versione = tipoVersione; }
    public static boolean getPersistenza() {return persistenza;}
    public static boolean getVersione() {return versione;}

    public abstract void close();

}