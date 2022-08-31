package com.reddit.clone.Application.service;

import com.reddit.clone.Application.dto.VoteDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Vote;
import com.reddit.clone.Application.repository.PostRepo;
import com.reddit.clone.Application.repository.VoteRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.reddit.clone.Application.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepo postRepo;
    private final VoteRepo voteRepo;
    private final AuthService authService;

    public void vote(VoteDto voteDto) throws NotFoundException, SpringRedditException {
        Post post = postRepo.findById(voteDto.getPostId()).orElseThrow(()->
                new NotFoundException("Post with the given id "+voteDto.getPostId()+" not exist")
        );
        Optional<Vote> voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepo.save(mapToVote(voteDto,post));
        postRepo.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
