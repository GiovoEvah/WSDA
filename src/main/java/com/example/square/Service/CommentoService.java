package com.example.square.Service;

import com.example.square.Model.Commento;
import com.example.square.Model.Post;
import com.example.square.Model.Utente;
import com.example.square.Repository.CommentoRepository;
import com.example.square.Repository.PostRepository;
import com.example.square.Repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentoService {

    @Autowired
    private CommentoRepository commentoRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public List<Commento> getCommentiByPostId(int postId) {
        return commentoRepository.findCommentiById(postId, Pageable.unpaged());
    }

    public List<Commento> getAltriCommenti(int postId, int offset) {
        Pageable pageable = PageRequest.of(offset, 4);
        return commentoRepository.findCommentiById(postId, pageable);
    }

    public List<Commento> getAllComments() {
        return commentoRepository.findAll();
    }

    public void addCommento(int idUtente, int postId, String contenuto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post non trovato"));
        Utente utente = utenteRepository.findById(idUtente).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
        Commento commento = new Commento();
        commento.setTesto(contenuto);
        commento.setPost(post);
        commento.setUtente(utente);
        commento.setCreatoIl(new Date());
        commentoRepository.save(commento);
    }
}
