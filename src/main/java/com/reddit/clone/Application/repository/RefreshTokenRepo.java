package com.reddit.clone.Application.repository;

import com.reddit.clone.Application.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken , Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
