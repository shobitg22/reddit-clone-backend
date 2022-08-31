package com.reddit.clone.Application.controller;

import com.reddit.clone.Application.dto.PostRequest;
import com.reddit.clone.Application.dto.PostResponse;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequest));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(postService.getById(id));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getAllPostBySubReddit(@PathVariable("id") Long subRedditId){
        try {
            return ResponseEntity.ok(postService.getBySubReddit(subRedditId));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/username/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUserName(@PathVariable("name") String name){
        try {
            return ResponseEntity.ok(postService.getByUserName(name));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
