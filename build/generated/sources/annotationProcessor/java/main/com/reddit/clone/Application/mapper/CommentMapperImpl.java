package com.reddit.clone.Application.mapper;

import com.reddit.clone.Application.dto.CommentDto;
import com.reddit.clone.Application.model.Comment;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T12:30:07+0530",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment mapDtoToComment(CommentDto commentDto, Post post, User user) {
        if ( commentDto == null && post == null && user == null ) {
            return null;
        }

        Comment comment = new Comment();

        if ( commentDto != null ) {
            comment.setId( commentDto.getId() );
            comment.setText( commentDto.getText() );
        }
        comment.setPost( post );
        comment.setUser( user );
        comment.setCreatedDate( java.time.Instant.now() );

        return comment;
    }

    @Override
    public CommentDto mapCommentToDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setId( comment.getId() );
        commentDto.setText( comment.getText() );

        commentDto.setPostId( comment.getPost().getPostId() );
        commentDto.setUserName( comment.getUser().getUserName() );
        commentDto.setCreatedDate( getDuration(comment) );

        return commentDto;
    }
}
