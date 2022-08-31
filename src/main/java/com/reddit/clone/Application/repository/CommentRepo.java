package com.reddit.clone.Application.repository;

import com.reddit.clone.Application.model.Comment;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
