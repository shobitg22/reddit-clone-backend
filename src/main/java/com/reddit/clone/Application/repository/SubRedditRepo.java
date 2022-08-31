package com.reddit.clone.Application.repository;

import com.reddit.clone.Application.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubRedditRepo extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(String subRedditName);
}
