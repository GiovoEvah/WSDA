package com.example.square.Controller;

import com.example.square.Model.Utente;
import com.example.square.Repository.UtenteRepository;
import com.example.square.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registerForm")
    public String registraUtente(@RequestParam String nome, @RequestParam String cognome, @RequestParam String email, @RequestParam String password, Model model) {
        String hashedPassword = passwordEncoder.encode(password);
        Utente utente = new Utente(email, hashedPassword, nome, cognome);
        boolean isRegistered = utenteService.registraUtente(utente);

        if (!isRegistered) {
            model.addAttribute("error", "Errore: username già esistente.");
            return "redirect:/error";
        }

        return "redirect:/postspage";
    }
}
