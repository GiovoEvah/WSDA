package com.example.square.Model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "testo", nullable = false)
    private String contenuto;

    @Column(name = "creato_il", nullable = false, updatable = false)
    private Date creatoIl;

    @ManyToOne
    @JoinColumn(name = "fk_utente", referencedColumnName = "id")
    private Utente utente;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Commento> commenti;

    public Post(int id, Utente utente, String contenuto, Date creatoIl, List<Commento> commenti) {
        this.id = id;
        this.utente = utente;
        this.contenuto = contenuto;
        this.creatoIl = creatoIl;
        this.commenti = commenti;
    }

    public Post() {

    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public Date getCreatoIl() {
        return creatoIl;
    }

    public void setCreatoIl(Date creatoIl) {
        this.creatoIl = creatoIl;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Commento> getCommenti() {
        return commenti;
    }

    public void setCommenti(List<Commento> commenti) {
        this.commenti = commenti;
    }

}
