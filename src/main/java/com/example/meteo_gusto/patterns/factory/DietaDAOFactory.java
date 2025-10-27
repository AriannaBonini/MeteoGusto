package com.example.meteo_gusto.patterns.factory;


import com.example.meteo_gusto.dao.DietaDAO;
import com.example.meteo_gusto.dao.memoria.DietaDAOMemoria;
import com.example.meteo_gusto.dao.mysql.DietaDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class DietaDAOFactory {
    public DietaDAO getDietaDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV -> creaDietaDAOMYSQL();
            case MYSQL -> creaDietaDAOMYSQL();
            case MEMORIA -> creaDietaDAOMEMORIA();
        };
    }

    public DietaDAO creaDietaDAOMYSQL(){return new DietaDAOMySql();}
    public DietaDAO creaDietaDAOMEMORIA(){return new DietaDAOMemoria();}
}
