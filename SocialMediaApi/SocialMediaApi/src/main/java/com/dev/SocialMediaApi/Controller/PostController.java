package com.dev.SocialMediaApi.Controller;

import com.dev.SocialMediaApi.DTO.PostRequest;
import com.dev.SocialMediaApi.Model.Post;
import com.dev.SocialMediaApi.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// PostController.java
/**
 * Post Controller handling post-related operations
 *
 * This controller manages social media post operations including creation, retrieval,
 * liking, and deletion. All operations except listing posts require authentication.
 *
 * Endpoints:
 * - POST /posts - Create a new post (authenticated)
 * - GET /posts - Get all posts (public)
 * - POST /posts/{id}/like - Like/unlike a post (authenticated)
 * - DELETE /posts/{id} - Delete a post (authenticated, owner only)
 */

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Create a new post
     *
     * This endpoint allows authenticated users to create new posts.
     * The post content is provided in the request body, and the author
     * is determined from the authentication context.
     *
     * @param request PostRequest object containing post content
     * @param auth Authentication object containing current user's details
     * @return ResponseEntity with created Post object
     */
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        Post post = postService.createPost(request.getContent(), auth.getName());
        return ResponseEntity.ok(post);
    }



    /**
     * Retrieve all posts
     *
     * This endpoint returns a list of all posts in the system.
     * authentication  required via JWT token.
     * Posts are typically ordered by creation date (newest first).
     *
     * @return ResponseEntity containing List of all Post objects
     */
    @GetMapping
    public ResponseEntity<?> listPosts(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        return ResponseEntity.ok(postService.listPosts());
    }

    /**
     * Like a post
     *
     * This endpoint allows authenticated users to like posts.
     *
     * @param id Post ID to like (from URL path)
     * @param auth Authentication object containing current user's details
     * @return ResponseEntity with success message or error if post not found
     */

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id, Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        boolean liked = postService.likePost(id, auth.getName());

        if (liked) {
            return ResponseEntity.ok("Post liked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }

    /**
     * Delete a post
     *
     * This endpoint allows authenticated users to delete their own posts.
     * Only the post author can delete their post (authorization enforced in service layer).
     *
     * @param id Post ID to delete (from URL path)
     * @param auth Authentication object containing current user's details
     * @return ResponseEntity with success message, 403 Forbidden if unauthorized, or 404 if not found
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        int result = postService.deletePostWithStatus(id, auth.getName());

        return switch (result) {
            case 200 -> ResponseEntity.ok("Post deleted successfully.");
            case 403 -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this post.");
            case 404 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        };
    }
}
