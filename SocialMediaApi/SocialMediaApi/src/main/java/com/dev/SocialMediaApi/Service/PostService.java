package com.dev.SocialMediaApi.Service;

import com.dev.SocialMediaApi.Model.Post;

import java.util.List;

public interface PostService {
    // Creates a new post with content and author name
    Post createPost(String content, String author);

    // Returns a list of all posts
    List<Post> listPosts();

    // Adds a like to the post from the specified user
    boolean likePost(Long id, String username);

    // Deletes a post by ID if the requester is the author
    int deletePostWithStatus(Long id, String username);
}
