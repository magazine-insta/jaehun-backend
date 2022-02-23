package pbl.magazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pbl.magazine.dto.PostRequestDto;
import pbl.magazine.dto.PostResponseDto;
import pbl.magazine.model.Likes;
import pbl.magazine.model.Post;
import pbl.magazine.model.User;
import pbl.magazine.repository.LikeRepository;
import pbl.magazine.repository.PostRepository;
import pbl.magazine.security.UserDetailsImpl;

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
        Optional<User> user = getUser();
        User presentUser = null;
        if (user.isPresent())
             presentUser = user.get();


        List<Post> posts = postRepository.findAllPostByFetchJoin();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post p : posts) {
            PostResponseDto responseDto = new PostResponseDto(p);
            responseDtoList.add(responseDto);

            if (presentUser != null) {
                if (likeRepository.findByPostAndUser(p, presentUser).isPresent())
                    responseDto.changeIsLiked(true);
            }
        }
        return responseDtoList;
    }



    public PostResponseDto getPost(Long id, User user) {
        Post post = findPost(id);
        PostResponseDto responseDto = new PostResponseDto(post);
        if (likeRepository.findByPostAndUser(post, user).isPresent())
            responseDto.changeIsLiked(true);
        return responseDto;
    }

    public PostResponseDto writePost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        return new PostResponseDto(postRepository.save(post));
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        checkUser(post.getUser().getUsername());
        post.update(requestDto);
        return id;
    }

    public Long deletePost(Long id) {
        Post post = findPost(id);
        checkUser(post.getUser().getUsername());
        postRepository.deleteById(id);
        return id;
    }

    public Long changeLike(Long post_id, User user) {
        Post post = findPost(post_id);

        Optional<Likes> like = likeRepository.findByPostAndUser(post, user);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            likeRepository.save(new Likes(post, user));
        }
        return post_id;
    }

    private Optional<User> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            return Optional.of(userDetails.getUser());
        } else return Optional.empty();
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 없습니다")
        );
    }

    private void checkUser(String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (!(username.equals(userDetails.getUsername())))
            throw new AccessDeniedException("게시글 작성자만 접근할 수 있습니다");
    }
}
