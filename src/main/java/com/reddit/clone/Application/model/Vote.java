package com.reddit.clone.Application.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName ="postId")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;

    public Vote(Long voteId, VoteType voteType, Post post, User user) {
        this.voteId = voteId;
        this.voteType = voteType;
        this.post = post;
        this.user = user;
    }

    public Vote() {
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
