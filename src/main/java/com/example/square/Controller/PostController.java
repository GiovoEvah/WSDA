package com.example.square.Controller;

import com.example.square.Repository.UtenteRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import com.example.square.Model.Post;
import com.example.square.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;


import java.util.List;

@Controller
public class PostController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PostService postService;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/export/xml")
    public ResponseEntity<String> exportPostsToXml(Model model) {
        // Recupera tutti i post
        List<Post> posts = postService.getPosts();

        // Aggiunge i post al modello di Thymeleaf
        model.addAttribute("posts", posts);

        // Usa Thymeleaf per generare l'XML
        Context context = new Context();
        context.setVariables(model.asMap());
        String xmlContent = templateEngine.process("posts", context);

        // Configura le intestazioni della risposta
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

        // Restituisce la risposta XML
        return new ResponseEntity<>(xmlContent, headers, HttpStatus.OK);
    }

    @PostMapping("/createPost")
    public String createPost(@RequestParam String emailUtente, @RequestParam String postContent) {
        try {
            postService.savePost(emailUtente, postContent);
            return "redirect:/postspage";
        } catch (Exception e) {
            System.out.println(e + "Error creating post");
            return "redirect:/error";
        }
    }
}


