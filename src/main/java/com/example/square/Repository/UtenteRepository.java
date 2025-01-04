package com.example.square.Repository;

import com.example.square.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Utente findByEmail(String email); // Metodo per recuperare un utente tramite nome utente

    Utente findByid(int id);
}
