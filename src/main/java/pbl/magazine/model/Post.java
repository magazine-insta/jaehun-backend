package pbl.magazine.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pbl.magazine.dto.PostRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String contentText;

    @Column
    private String contentImg;

    @Column
    private String layoutType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private final List<Likes> likes = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user) {
        this.contentText = requestDto.getContents();
        this.contentImg = requestDto.getImageUrl();
        this.layoutType = requestDto.getLayoutType();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.contentText = requestDto.getContents();
        this.contentImg = requestDto.getImageUrl();
        this.layoutType = requestDto.getLayoutType();
    }
}
