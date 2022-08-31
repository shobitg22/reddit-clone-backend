package com.reddit.clone.Application.dto;

import com.reddit.clone.Application.model.VoteType;

public class VoteDto {
    private VoteType voteType;
    private Long postId;

    public VoteDto(VoteType voteType, Long postId) {
        this.voteType = voteType;
        this.postId = postId;
    }

    public VoteDto() {
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
