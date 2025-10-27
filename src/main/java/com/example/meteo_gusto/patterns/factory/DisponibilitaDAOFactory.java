package com.example.meteo_gusto.patterns.factory;

import com.example.meteo_gusto.dao.DisponibilitaDAO;
import com.example.meteo_gusto.dao.memoria.DisponibilitaDAOMemoria;
import com.example.meteo_gusto.dao.mysql.DisponibilitaDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class DisponibilitaDAOFactory {
    public DisponibilitaDAO getDisponibilitaDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV -> creaDisponibilitaDAOMYSQL();
            case MYSQL -> creaDisponibilitaDAOMYSQL();
            case MEMORIA -> creaDisponibilitaDAOMEMORIA();
        };
    }

    public DisponibilitaDAO creaDisponibilitaDAOMYSQL(){return new DisponibilitaDAOMySql();}
    public DisponibilitaDAO creaDisponibilitaDAOMEMORIA(){return new DisponibilitaDAOMemoria();}
}
