package com.example.meteo_gusto.patterns.factory;


import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.dao.memoria.DietaDAOMemoria;
import com.example.meteo_gusto.dao.mysql.DietaDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class DietaDAOFactory {
    public DietaDAO getDietaDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaDietaDAOMYSQL();
            case MEMORIA -> creaDietaDAOMEMORIA();
        };
    }

    private DietaDAO creaDietaDAOMYSQL(){return new DietaDAOMySql();}
    private DietaDAO creaDietaDAOMEMORIA(){return new DietaDAOMemoria();}
}
