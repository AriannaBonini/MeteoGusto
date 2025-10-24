package com.example.meteo_gusto.bean;

import com.example.meteo_gusto.eccezione.ValidazioneException;

public class PersonaBean {
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private String password;

    /* COSTRUTTORE CON PARAMETRI */

    public PersonaBean(String nome, String cognome, String telefono, String email, String password) throws ValidazioneException {
        validaCampiNonVuoti(nome, cognome, telefono, email, password);
        validaFormatoEmail(email);
        validaPassword(password);
        validaNomeCognome(nome, cognome);
        validaTelefono(telefono);

        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    /* GETTER - SOLO LETTURA */
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getTelefono() {return telefono;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    /* METODI PRIVATI DI VALIDAZIONE */
    private void validaCampiNonVuoti(String nome, String cognome, String telefono,
                                     String email, String password) throws ValidazioneException {
        if (nome == null || nome.trim().isEmpty() ||
                cognome == null || cognome.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new ValidazioneException("Tutti i campi sono obbligatori");
        }
    }

    private void validaFormatoEmail(String email) throws ValidazioneException {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidazioneException("Formato email non valido");
        }
    }

    private void validaPassword(String password) throws ValidazioneException {
        if (password.length() < 8) {
            throw new ValidazioneException("La password deve avere almeno 8 caratteri");
        }
    }

    private void validaNomeCognome(String nome, String cognome) throws ValidazioneException {
        if (!nome.matches("^[a-zA-Zàèéìòùçñ']+$")) {
            throw new ValidazioneException("Il nome può contenere solo lettere (senza spazi)");
        }
        if (!cognome.matches("^[a-zA-Zàèéìòùçñ']+$")) {
            throw new ValidazioneException("Il cognome può contenere solo lettere (senza spazi)");
        }
    }

    private void validaTelefono(String telefono) throws ValidazioneException {
        if (!telefono.matches("^\\d{10,15}$")) {
            throw new ValidazioneException("Il telefono deve contenere solo numeri (10-15 cifre)");
        }
    }
}
