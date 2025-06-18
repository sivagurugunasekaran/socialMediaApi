package com.dev.SocialMediaApi.ServiceImpl;

import com.dev.SocialMediaApi.Model.Post;
import com.dev.SocialMediaApi.Service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostServiceImpl implements PostService {

    // In-memory store for posts
    private final Map<Long, Post> postStore = new ConcurrentHashMap<>();

    // Atomic counter for generating unique post IDs
    private final AtomicLong idCounter = new AtomicLong();

    // Creates a new post and adds it to the store
    @Override
    public Post createPost(String content, String author) {
        Long id = idCounter.incrementAndGet();
        Post post = new Post(id, content, author, LocalDateTime.now(), new HashSet<>());
        postStore.put(id, post);
        return post;
    }

    // Returns all posts as a list
    @Override
    public List<Post> listPosts() {
        return new ArrayList<>(postStore.values());
    }

    // Likes a post by adding the username to the set of users who liked it
    @Override
    public boolean likePost(Long id, String username) {
        Post post = postStore.get(id);
        if (post != null) {
            post.getLikedBy().add(username);
            return true;
        }
        return false;
    }

    // Deletes a post if it exists and the requester is the original author
    @Override
    public int deletePostWithStatus(Long id, String username) {
        Post post = postStore.get(id);
        if (post == null) {
            return 404;
        }
        if (!post.getAuthor().equals(username)) {
            return 403;
        }
        postStore.remove(id);
        return 200;
    }
}
