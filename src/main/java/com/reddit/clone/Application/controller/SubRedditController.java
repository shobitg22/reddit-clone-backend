package com.reddit.clone.Application.controller;

import com.reddit.clone.Application.dto.SubRedditDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {
    private final SubRedditService subRedditService;

    @PostMapping
    public ResponseEntity<SubRedditDto> createSubReddit(@RequestBody SubRedditDto subRedditDto){
        SubRedditDto subReddit= null;
        try {
            subReddit = subRedditService.createSubReddit(subRedditDto);
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subReddit);
    }
    @GetMapping
    public ResponseEntity<List<SubRedditDto>> getAllSubReddit(){
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubRedditDto> getSubRedditById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getById(id));
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
