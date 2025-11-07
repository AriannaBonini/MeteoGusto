package com.example.meteo_gusto.patterns.factory;


import com.example.meteo_gusto.dao.RecensioneDAO;
import com.example.meteo_gusto.dao.memoria.RecensioneDAOMemoria;
import com.example.meteo_gusto.dao.mysql.RecensioneDAOMySql;
import com.example.meteo_gusto.patterns.facade.TipoPersistenza;

public class RecensioneDAOFactory {
    public RecensioneDAO getRecensioneDAO(TipoPersistenza tipoPersistenza) {
        return switch (tipoPersistenza){
            case CSV, MYSQL -> creaRecensioneDAOMYSQL();
            case MEMORIA -> creaRecensioneDAOMEMORIA();
        };
    }

    public RecensioneDAO creaRecensioneDAOMYSQL(){return new RecensioneDAOMySql();}
    public RecensioneDAO creaRecensioneDAOMEMORIA(){return new RecensioneDAOMemoria();}
}
