package com.example.square.Config;

import com.example.square.Model.Utente;
import com.example.square.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    public MyUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Trova l'utente in base all'email
        Utente utente = utenteRepository.findByEmail(email);

        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }

        // Crea un'istanza di UserDetails basata sull'utente trovato
        return User.builder()
                .username(utente.getEmail())
                .password(utente.getPassword())
                .roles("USER") // Modificato per assegnare un ruolo generico se non specificato
                .build();
    }
}
