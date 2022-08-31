package com.reddit.clone.Application.repository;

import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.User;
import com.reddit.clone.Application.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
