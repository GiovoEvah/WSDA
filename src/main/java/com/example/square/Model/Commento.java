package com.example.square.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "commento")
public class Commento {

    public Commento(int id, String testo, java.sql.Timestamp creatoIl, Utente utente, Post post) {
        this.id = id;
        this.testo = testo;
        this.creatoIl = creatoIl;
        this.utente = utente;
        this.post = post;
    }

    public Commento() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "testo", nullable = false)
    private String testo;

    @Column(name = "creato_il", nullable = false, updatable = false)
    private Date creatoIl;

    @ManyToOne
    @JoinColumn(name = "fk_utente", referencedColumnName = "id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "fk_post", referencedColumnName = "id")
    @JsonBackReference
    private Post post;

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
