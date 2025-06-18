package com.dev.SocialMediaApi.DTO;



/**
 * Request DTO for creating posts
 */
import lombok.Data;

@Data
public class PostRequest {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
