package pbl.magazine.dto;

import lombok.Getter;
import lombok.Setter;
import pbl.magazine.model.Post;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostResponseDto {
    private Long postId;
    private String contents;
    private String imageUrl;
    private String layoutType;
    private LocalDateTime createdAt;
    private String nickname;
    private int likeCount;
    private boolean userLiked = false;
    private boolean isMe;

    public PostResponseDto(Post post, boolean isMe) {
        this.postId = post.getId();
        this.contents = post.getContentText();
        this.imageUrl = post.getContentImg();
        this.layoutType = post.getLayoutType();
        this.createdAt = post.getCreatedAt();
        this.nickname = post.getUser().getNickname();
        this.likeCount = post.getLikes().size();
        this.isMe = isMe;
    }

    public void changeIsLiked(boolean b) {
        this.userLiked = true;
    }
}
