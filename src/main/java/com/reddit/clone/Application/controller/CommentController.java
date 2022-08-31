package com.reddit.clone.Application.controller;


import com.reddit.clone.Application.dto.CommentDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(commentDto));
        } catch (NotFoundException | SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable("id") Long postId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/user/{name}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserName(@PathVariable String name){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUserName(name));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
