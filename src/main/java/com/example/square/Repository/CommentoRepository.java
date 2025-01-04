package com.example.square.Repository;

import com.example.square.Model.Commento;
import com.example.square.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Integer> {
    @Query("SELECT c FROM Commento c WHERE c.post.id = :idPost ORDER BY c.creatoIl DESC")
    List<Commento> findCommentiById(@Param("idPost") int idPost, Pageable pageable);
}
