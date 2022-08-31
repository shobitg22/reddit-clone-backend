package com.reddit.clone.Application.service;

import com.reddit.clone.Application.dto.CommentDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.mapper.CommentMapper;
import com.reddit.clone.Application.model.Comment;
import com.reddit.clone.Application.model.NotificationEmail;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.User;
import com.reddit.clone.Application.repository.CommentRepo;
import com.reddit.clone.Application.repository.PostRepo;
import com.reddit.clone.Application.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";

    private final CommentRepo commentRepo;
    private final AuthService authService;
    private final PostRepo postRepo;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    private final UserRepo userRepo;
    public CommentDto saveComment(CommentDto commentDto) throws NotFoundException, SpringRedditException {
        Post post = postRepo.findById(commentDto.getPostId()).orElseThrow(()->
           new NotFoundException("Post with the given id "+commentDto.getPostId()+" not exist")
        );
        Comment comment = commentMapper.mapDtoToComment(commentDto,post,authService.getCurrentUser());
        comment=commentRepo.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUserName() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

        return commentMapper.mapCommentToDto(comment);
    }

    public List<CommentDto> getCommentsByPost(Long postId) throws NotFoundException {
        Post post = postRepo.findById(postId).orElseThrow(()->
                new NotFoundException("Post with the given id "+postId+" not exist")
        );

       List<Comment> comments =  commentRepo.findByPost(post);
      return comments.stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUserName(String name) throws NotFoundException {
        User user = userRepo.findByUserName(name).orElseThrow(()->
                new NotFoundException("User with the given name "+name+" not exist")
        );
        List<Comment> comments = commentRepo.findByUser(user);
        return comments.stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) throws SpringRedditException {
        mailService.sendMail(new NotificationEmail(user.getUserName() + " Commented on your post", user.getEmail(), message));
    }
}
