package com.reddit.clone.Application.mapper;

import com.reddit.clone.Application.dto.PostRequest;
import com.reddit.clone.Application.dto.PostResponse;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T20:02:57+0530",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post mapDtoToPost(PostRequest postRequest, Subreddit subreddit, User user) {
        if ( postRequest == null && subreddit == null && user == null ) {
            return null;
        }

        Post post = new Post();

        if ( postRequest != null ) {
            post.setDescription( postRequest.getDescription() );
            post.setPostId( postRequest.getPostId() );
            post.setPostName( postRequest.getPostName() );
            post.setUrl( postRequest.getUrl() );
        }
        if ( subreddit != null ) {
            post.setSubreddit( subreddit );
            post.setUser( subreddit.getUser() );
            post.setCreatedDate( subreddit.getCreatedDate() );
        }
        post.setVoteCount( 0 );

        return post;
    }

    @Override
    public PostResponse mapPostToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setUserName( postUserUserName( post ) );
        postResponse.setSubRedditName( postSubredditName( post ) );
        postResponse.setId( post.getPostId() );
        postResponse.setVoteCount( post.getVoteCount() );
        postResponse.setPostName( post.getPostName() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setDescription( post.getDescription() );

        postResponse.setCommentCount( commentCount(post) );
        postResponse.setDuration( getDuration(post) );

        return postResponse;
    }

    private String postUserUserName(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String userName = user.getUserName();
        if ( userName == null ) {
            return null;
        }
        return userName;
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
