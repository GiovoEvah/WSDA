package com.example.square.Service;

import com.example.square.Model.Commento;
import com.example.square.Model.Post;
import com.example.square.Model.Utente;
import com.example.square.Repository.CommentoRepository;
import com.example.square.Repository.PostRepository;
import com.example.square.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private CommentoRepository commentoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPosts() {

        // Crea il PageRequest con limite massimo di 150 post
        Pageable pageable = PageRequest.of(0, 150);  // pagina 0 e 150 post per pagina
        Pageable pageableCommenti = PageRequest.of(0, 4); // Limita a 4 commenti
        // Recupera i post degli ultimi 7 giorni
        List<Post> posts = postRepository.findPosts(pageable);

        // Crea una lista di PostWithComments
        List<Post> postsWithComments = new ArrayList<>();

        for (Post post : posts) {

            // Recupera i primi commenti associati al post
            List<Commento> commenti = commentoRepository.findCommentiById(post.getId(), pageableCommenti);
            // Crea un oggetto PostWithComments che include anche il conteggio dei like
            Post postWithComments = new Post(
                    post.getId(),
                    post.getUtente(),
                    post.getContenuto(),
                    post.getCreatoIl(),
                    commenti
            );

            // Aggiungi il PostWithComments alla lista
            postsWithComments.add(postWithComments);
        }

        return postsWithComments;
    }

    public void savePost(String username, String testo) {
        Post post = new Post();
        post.setUtente(utenteRepository.findByEmail(username));
        post.setCreatoIl(new Date());
        post.setContenuto(testo);
        postRepository.save(post);
    }

}
