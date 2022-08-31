package com.reddit.clone.Application.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.clone.Application.dto.CommentDto;
import com.reddit.clone.Application.model.Comment;
import com.reddit.clone.Application.model.Post;
import com.reddit.clone.Application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post",source = "post")
    @Mapping(target = "user",source = "user")
    Comment mapDtoToComment(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName",expression = "java(comment.getUser().getUserName())")
//    @Mapping(target = "createdDate",source = "comment.createdDate")
//    @Mapping(target = "text",source = "comment.text")
//    @Mapping(target = "id",source = "comment.id")
    @Mapping(target = "createdDate", expression = "java(getDuration(comment))")
    CommentDto mapCommentToDto(Comment comment);

    default String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
}
