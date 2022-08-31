package com.reddit.clone.Application.controller;

import com.reddit.clone.Application.dto.VoteDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/votes")
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        try {
            voteService.vote(voteDto);
        } catch (NotFoundException | SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
