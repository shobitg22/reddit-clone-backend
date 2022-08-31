package com.reddit.clone.Application.repository;

import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
