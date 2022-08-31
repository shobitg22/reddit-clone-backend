package com.reddit.clone.Application.service;

import com.reddit.clone.Application.dto.PostRequest;
import com.reddit.clone.Application.dto.PostResponse;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.mapper.PostMapper;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import com.reddit.clone.Application.repository.PostRepo;
import com.reddit.clone.Application.repository.SubRedditRepo;
import com.reddit.clone.Application.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

        private final PostRepo postRepo;
        private final SubRedditRepo subRedditRepo;
        private final PostMapper postMapper;
        private final UserRepo userRepo;
        private final AuthService authService;
    @Transactional
    public PostResponse createPost(PostRequest postRequest) throws SpringRedditException {
        Subreddit subreddit = subRedditRepo.findByName(postRequest.getSubRedditName()).orElseThrow(
                ()->new SpringRedditException("Subreddit with the name "+postRequest.getSubRedditName()+" not exist")
        );

        Post post=  postMapper.mapDtoToPost(postRequest,subreddit,authService.getCurrentUser());
         post=postRepo.save(post);
        return postMapper.mapPostToDto(post);
    }

    public List<PostResponse> getAll() {
       return postRepo.findAll().stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }

    public PostResponse getById(Long id) throws SpringRedditException {
     Post post=   postRepo.findById(id).orElseThrow(
                ()->new SpringRedditException("Post with the id "+id+" not exist")
        );
     return postMapper.mapPostToDto(post);
    }

    public List<PostResponse> getBySubReddit(Long subRedditId) throws SpringRedditException {
        Subreddit subreddit = subRedditRepo.findById(subRedditId).orElseThrow(
                ()->new SpringRedditException("Subreddit with the id "+subRedditId+" not exist")
        );
        List<Post> posts = postRepo.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }

    public List<PostResponse> getByUserName(String name) throws SpringRedditException {
        User user = userRepo.findByUserName(name).orElseThrow(
                ()->new SpringRedditException("Subreddit with the name "+name+" not exist")
        );
        List<Post> posts = postRepo.findAllByUser(user);
        return posts.stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }
}
