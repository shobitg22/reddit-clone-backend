package com.reddit.clone.Application.dto;

public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subRedditName;

    private Integer voteCount;
    private Integer commentCount;
    private String duration;

    public PostResponse(Long id, String postName, String url, String description, String userName, String subRedditName, Integer voteCount, Integer commentCount, String duration) {
        this.id = id;
        this.postName = postName;
        this.url = url;
        this.description = description;
        this.userName = userName;
        this.subRedditName = subRedditName;
        this.voteCount = voteCount;
        this.commentCount = commentCount;
        this.duration = duration;
    }

    public PostResponse() {
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubRedditName() {
        return subRedditName;
    }

    public void setSubRedditName(String subRedditName) {
        this.subRedditName = subRedditName;
    }
}
