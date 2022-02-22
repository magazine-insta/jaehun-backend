package pbl.magazine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pbl.magazine.dto.PostRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contentText;

    @Column
    private String contentImg;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contentText = requestDto.getContentText();
        this.contentImg = requestDto.getContentImg();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contentText = requestDto.getContentText();
        this.contentImg = requestDto.getContentImg();
    }
}
