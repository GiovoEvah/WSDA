package com.example.square.Model;

import jakarta.persistence.*;

@Entity
public class Utente {
//load.html posts.html postcontroller
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    public Utente() {}

    public Utente(String email, String password, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String nome) {
        this.nome = nome;
    }
}
