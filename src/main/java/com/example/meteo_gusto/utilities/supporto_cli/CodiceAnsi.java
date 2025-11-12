package com.example.meteo_gusto.utilities.supporto_cli;

public class CodiceAnsi {

    private CodiceAnsi(){ /* costruttore privato */ }

    public static final String ANSI_GRASSETTO = "\u001B[1m";
    public static final String ANSI_ARANCIONE = "\u001B[38;2;230;115;51m";
    public static final String ANSI_BIANCO_OPACO= "\u001B[38;2;180;180;180m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String STELLINA_GIALLA = "\u2B50"; // ★ emoji gialla
    public static final String STELLINA_VUOTA = "\u2606";   // ☆ contorno grigio
    public static final String PUNTINO = "\u00B7";          // · puntino a metà altezza
    public static final String DOLLARO_ARANCIONE = "\u001B[38;2;233;118;39m$\u001B[0m"; // $ arancione
    public static final String ANSI_GIALLO = "\u001B[38;2;255;223;0m"; // giallo brillante
    public static final String CAMPANELLA_GIALLA = ANSI_GIALLO + "\uD83D\uDD14" + ANSI_RESET; //campanellina

}

