package com.example.square.Service;

import com.example.square.Model.Utente;
import com.example.square.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public boolean registraUtente(Utente utente) {
        // Controlla se l'username esiste già
        if (utenteRepository.findByEmail(utente.getEmail()) != null) {
            return false;
        }

        // Salva l'utente nel database
        utenteRepository.save(utente);
        return true;
    }
}
