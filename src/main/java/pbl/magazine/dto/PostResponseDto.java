package pbl.magazine.dto;

import lombok.Getter;
import lombok.Setter;
import pbl.magazine.model.Post;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String contentText;
    private String contentImg;
    private LocalDateTime createdAt;
    private String nickname;
    private Long likeCount;

    public PostResponseDto(Post post, Long likeCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contentText = post.getContentText();
        this.contentImg = post.getContentImg();
        this.createdAt = post.getCreatedAt();
        this.nickname = post.getUser().getNickname();
        this.likeCount = likeCount;
    }
}
