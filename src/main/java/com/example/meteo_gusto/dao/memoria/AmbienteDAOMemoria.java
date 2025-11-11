package com.example.meteo_gusto.dao.memoria;

import com.example.meteo_gusto.dao.AmbienteDAO;
import com.example.meteo_gusto.eccezione.EccezioneDAO;
import com.example.meteo_gusto.model.Ambiente;
import com.example.meteo_gusto.model.Prenotazione;
import com.example.meteo_gusto.model.Ristorante;
import java.util.List;

public class AmbienteDAOMemoria implements AmbienteDAO {
    @Override
    public void registraDisponibilita(List<Ambiente> ambiente) throws EccezioneDAO {}
    public List<Ambiente> cercaAmbientiDelRistorante(Ristorante ristorante) throws EccezioneDAO {return null;}
    @Override
    public Ambiente cercaExtraPerAmbiente(Ambiente ambiente) throws EccezioneDAO {return null;}
    @Override
    public Ambiente cercaNomeAmbienteERistorante(Ambiente ambiente) throws EccezioneDAO {return null;}
}
