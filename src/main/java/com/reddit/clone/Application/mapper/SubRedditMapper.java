package com.reddit.clone.Application.mapper;

import com.reddit.clone.Application.dto.SubRedditDto;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditDto mapSubRedditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }
    @InheritInverseConfiguration
    @Mapping(target = "posts",ignore = true)
    @Mapping(target ="createdDate",expression = "java(java.time.Instant.now())")
    Subreddit mapDtoToSubReddit(SubRedditDto subRedditDto, User user);
}
