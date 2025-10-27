package com.example.meteo_gusto.bean;


import com.example.meteo_gusto.eccezione.ValidazioneException;

public class CredenzialiBean {

    private String email;
    private String password;

    public CredenzialiBean() { /* COSTRUTTORE VUOTO */ }

    /* SETTER CON VALIDAZIONE */
    public void setEmail(String email) throws ValidazioneException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidazioneException("L'email non può essere vuota.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidazioneException("Formato email non valido.");
        }
        this.email = email.trim();
    }

    public void setPassword(String password) throws ValidazioneException {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidazioneException("La password non può essere vuota.");
        }
        this.password = password;
    }

    /* GETTER */
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
