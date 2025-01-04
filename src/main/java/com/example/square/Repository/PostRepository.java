package com.example.square.Repository;

import com.example.square.Model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p ORDER BY p.creatoIl DESC")
    List<Post> findPosts(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Post findPostById(int postId);

}


