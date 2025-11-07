package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;
import com.example.meteo_gusto.enumerazione.TipoPersona;

public class PersonaBean {

    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private String password;
    private TipoPersona tipoPersona;

    public PersonaBean() { /* COSTRUTTORE VUOTO */ }

    public PersonaBean(String nome, String cognome, String telefono, String email, String password, TipoPersona tipoPersona) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.tipoPersona = tipoPersona;
    }


    /* METODI GETTER */
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public TipoPersona getTipoPersona() { return tipoPersona; }

    /* METODI SETTER CON VALIDAZIONE */
    public void setNome(String nome) throws ValidazioneException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidazioneException("Il nome non può essere vuoto");
        }
        if (!nome.matches("^[a-zA-Zàèéìòùçñ']+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere (senza spazi)");
        }
        this.nome = nome;
    }

    public void setCognome(String cognome) throws ValidazioneException {
        if (cognome == null || cognome.trim().isEmpty()) {
            throw new ValidazioneException("Il cognome non può essere vuoto");
        }
        if (!cognome.matches("^[a-zA-Zàèéìòùçñ']+$")) {
            throw new ValidazioneException("Il cognome può contenere solo lettere (senza spazi)");
        }
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) throws ValidazioneException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new ValidazioneException("Il telefono non può essere vuoto");
        }
        if (!telefono.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre)");
        }
        this.telefono = telefono;
    }

    public void setEmail(String email) throws ValidazioneException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidazioneException("L'email non può essere vuota");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidazioneException("Formato email non valido");
        }
        this.email = email;
    }

    public void setPassword(String password) throws ValidazioneException {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidazioneException("La password non può essere vuota");
        }
        if (password.length() < 8) {
            throw new ValidazioneException("La password deve avere almeno 8 caratteri");
        }
        this.password = password;
    }

    public void setTipoPersona(TipoPersona tipoPersona) throws ValidazioneException {
        if (tipoPersona == null) {
            throw new ValidazioneException("Il tipo persona non può essere nullo");
        }
        this.tipoPersona = tipoPersona;
    }
}

