package com.reddit.clone.Application.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.clone.Application.dto.PostRequest;
import com.reddit.clone.Application.dto.PostResponse;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import com.reddit.clone.Application.repository.CommentRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepo commentRepo;
    @Mapping(target = "description",source = "postRequest.description")
    @Mapping(target = "subreddit",source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapDtoToPost(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "userName",source = "user.userName")
    @Mapping(target = "subRedditName",source = "subreddit.name")
    @Mapping(target = "id",source = "postId")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapPostToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepo.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
