package com.reddit.clone.Application.dto;

public class PostRequest {
    private Long postId;
    private String subRedditName;
    private String postName;
    private String url;
    private String description;

    public PostRequest(Long postId, String subRedditName, String postName, String url, String description) {
        this.postId = postId;
        this.subRedditName = subRedditName;
        this.postName = postName;
        this.url = url;
        this.description = description;
    }

    public PostRequest() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getSubRedditName() {
        return subRedditName;
    }

    public void setSubRedditName(String subRedditName) {
        this.subRedditName = subRedditName;
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
}
