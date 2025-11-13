package com.example.meteo_gusto.utilities.supporto_cli;

public class CodiceAnsi {

    private CodiceAnsi(){ /* costruttore privato */ }

    public static final String ANSI_GRASSETTO = "\u001B[1m";
    public static final String ANSI_ARANCIONE = "\u001B[38;2;230;115;51m";
    public static final String ANSI_BIANCO_OPACO= "\u001B[38;2;180;180;180m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String STELLINA_GIALLA = "⭐"; // ★ emoji gialla
    public static final String STELLINA_VUOTA = "☆";   // ☆ contorno grigio
    public static final String PUNTINO = "·";          // · puntino a metà altezza
    public static final String ANSI_GIALLO = "\u001B[38;2;255;223;0m"; // giallo brillante
    public static final String CAMPANELLA_GIALLA = ANSI_GIALLO + "\uD83D\uDD14" + ANSI_RESET; //campanellina
    public static final String ATTENZIONE="Attenzione ";

    public static final String DOLLARO ="💲";

    public static final String ANGOLO_ALTO_SX = "╭";  // ╭
    public static final String ANGOLO_ALTO_DX = "╮";  // ╮
    public static final String ANGOLO_BASSO_SX = "╰"; // ╰
    public static final String ANGOLO_BASSO_DX = "╯"; // ╯
    public static final String BORDO_ORIZZONTALE = "─";    // ─
    public static final String BORDO_VERTICALE = "│";
    public static final String SOLE = "☀️";
    public static final String GIORNATA_NUVOLOSA = "🌤️";
    public static final String PIOGGIA = "🌧️";
    public static final String LUNA = "🌙";
    public static final String LUNA_CON_NUVOLE = "🌙☁️";
    public static final String TEMPERATURA_FREDDA = "🌡️❄️";
    public static final String TEMPERATURA_NORMALE = "🌡️";
    public static final String TEMPERATURA_CALDA = "🌡️🔥";










}

