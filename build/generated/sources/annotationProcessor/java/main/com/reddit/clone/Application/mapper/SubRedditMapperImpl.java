package com.reddit.clone.Application.mapper;

import com.reddit.clone.Application.dto.SubRedditDto;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T12:30:07+0530",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class SubRedditMapperImpl implements SubRedditMapper {

    @Override
    public SubRedditDto mapSubRedditToDto(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubRedditDto subRedditDto = new SubRedditDto();

        subRedditDto.setId( subreddit.getId() );
        subRedditDto.setName( subreddit.getName() );
        subRedditDto.setDescription( subreddit.getDescription() );

        subRedditDto.setNumberOfPosts( mapPosts(subreddit.getPosts()) );

        return subRedditDto;
    }

    @Override
    public Subreddit mapDtoToSubReddit(SubRedditDto subRedditDto, User user) {
        if ( subRedditDto == null && user == null ) {
            return null;
        }

        Subreddit subreddit = new Subreddit();

        if ( subRedditDto != null ) {
            subreddit.setId( subRedditDto.getId() );
            subreddit.setName( subRedditDto.getName() );
            subreddit.setDescription( subRedditDto.getDescription() );
        }
        subreddit.setUser( user );
        subreddit.setCreatedDate( java.time.Instant.now() );

        return subreddit;
    }
}
