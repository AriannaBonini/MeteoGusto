package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.GiornoChiusuraDAO;
import com.example.meteo_gusto.dao.memoria.GiornoChiusuraDAOMemoria;
import com.example.meteo_gusto.dao.mysql.GiornoChiusuraDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class GiornoChiusuraDAOFactory {

    public GiornoChiusuraDAO getGiornoChiusuraDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaGiornoChiusuraDAOMYSQL();
            case MEMORIA -> creaGiornoChiusuraDAOMEMORIA();
        };
    }

    private GiornoChiusuraDAO creaGiornoChiusuraDAOMYSQL(){return new GiornoChiusuraDAOMySql();}
    private GiornoChiusuraDAO creaGiornoChiusuraDAOMEMORIA(){return new GiornoChiusuraDAOMemoria();}
}
