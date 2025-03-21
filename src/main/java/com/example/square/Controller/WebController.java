package com.example.square.Controller;

import com.example.square.Model.Post;
import com.example.square.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private PostService PostService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/postspage")
    public String postspage(Model model) {
        List<Post> posts = PostService.getPosts();
        model.addAttribute("posts", posts);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean autenticated = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        model.addAttribute("autenticated", autenticated);

        if(autenticated) {
            String username = auth.getName();
            model.addAttribute("username", username);
        }
        else{
            model.addAttribute("username", "");
        }
        return "postspage";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/error")
    public String errore() {
        return "error";
    }

}
