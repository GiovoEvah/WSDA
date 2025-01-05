package com.example.square.Controller;

import com.example.square.Model.Commento;
import com.example.square.Service.CommentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CommentoController {

    @Autowired
    private CommentoService commentoService;

    @PostMapping("/addComment")
    public String createComment(@RequestParam String emailUtenteCommento, @RequestParam int postId, @RequestParam String newComment) {
        try {
            commentoService.addCommento(emailUtenteCommento, postId, newComment);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/getPaged", produces = "application/json")
    @ResponseBody
    public List<Commento> loadMoreComments(@RequestParam int postId, @RequestParam int offset) {
        return commentoService.getAltriCommenti(postId, offset);
    }

}
