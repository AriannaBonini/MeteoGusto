package com.example.meteo_gusto.utilities.convertitore;

import com.example.meteo_gusto.enumerazione.GiorniSettimana;
import com.example.meteo_gusto.enumerazione.TipoCucina;
import com.example.meteo_gusto.enumerazione.TipoDieta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConvertitoreEnum {

    private ConvertitoreEnum() { /* COSTRUTTORE VUOTO */ }


    /* CONVERTITORE GIORNI SETTIMANA*/
    public static Set<GiorniSettimana> giorniSettimanaDaStringAEnum(List<String> listaStringhe) {

        if (listaStringhe == null || listaStringhe.isEmpty()) {
            return Set.of();
        }
        return listaStringhe.stream()
                .map(GiorniSettimana::giorniSettimanaDaId)
                .collect(Collectors.toSet());
    }


    /* CONVERTITORE CUCINE */

    public static Set<TipoCucina> cucineDaStringAEnum(List<String> listaStringhe) {

        if (listaStringhe == null || listaStringhe.isEmpty()) {
            return Set.of();
        }
        return listaStringhe.stream()
                .map(TipoCucina::tipoCucinaDaId)
                .collect(Collectors.toSet());
    }


    /* CONVERTITORE DIETA */
    public static Set<TipoDieta> dieteDaStringAEnum(List<String> listaStringhe) {

        if (listaStringhe == null || listaStringhe.isEmpty()) {
            return Set.of();
        }
        return listaStringhe.stream()
                .map(TipoDieta::tipoDietaDaId)
                .collect(Collectors.toSet());
    }

    public static List<String> dieteDaEnumAString(Set<TipoDieta> diete) {
        if (diete == null || diete.isEmpty()) {
            return List.of();
        }

        return diete.stream()
                .map(TipoDieta::getId)
                .toList();
    }

}
