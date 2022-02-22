package pbl.magazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pbl.magazine.dto.PostRequestDto;
import pbl.magazine.dto.PostResponseDto;
import pbl.magazine.model.Likes;
import pbl.magazine.model.Post;
import pbl.magazine.model.User;
import pbl.magazine.repository.LikeRepository;
import pbl.magazine.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public List<PostResponseDto> getListOfPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            Long likeCount = likeRepository.countByPost(post);
            responseDtoList.add(new PostResponseDto(post, likeCount));
        }
        return responseDtoList;
    }


    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("")
        );
        Long likeCount = likeRepository.countByPost(post);
        return new PostResponseDto(post, likeCount);
    }

    public PostResponseDto writePost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        return new PostResponseDto(postRepository.save(post), 0L);
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("")
        );
        post.update(requestDto);
        return id;
    }

    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    public Long changeLike(Long post_id, User user) {
        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new NullPointerException("게시글이 없습니다")
        );

        Optional<Likes> like = likeRepository.findByPostAndUser(post, user);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            likeRepository.save(new Likes(post, user));
        }
        return post_id;
    }
}
