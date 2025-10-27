package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.GiorniChiusuraDAO;
import com.example.meteo_gusto.dao.memoria.GiorniChiusuraDAOMemoria;
import com.example.meteo_gusto.dao.mysql.GiorniChiusuraDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class GiorniChiusuraDAOFactory {

    public GiorniChiusuraDAO getGiorniChiusuraDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV -> creaGiorniChiusuraDAOMYSQL();
            case MYSQL -> creaGiorniChiusuraDAOMYSQL();
            case MEMORIA -> creaGiorniChiusuraDAOMEMORIA();
        };
    }

    public GiorniChiusuraDAO creaGiorniChiusuraDAOMYSQL(){return new GiorniChiusuraDAOMySql();}
    public GiorniChiusuraDAO creaGiorniChiusuraDAOMEMORIA(){return new GiorniChiusuraDAOMemoria();}
}
