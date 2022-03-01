package pbl.magazine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pbl.magazine.dto.PostRequestDto;
import pbl.magazine.dto.PostResponseDto;
import pbl.magazine.model.Likes;
import pbl.magazine.model.Post;
import pbl.magazine.model.User;
import pbl.magazine.repository.LikeRepository;
import pbl.magazine.repository.PostRepository;
import pbl.magazine.util.SecurityUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final SecurityUtil securityUtil;

    public List<PostResponseDto> getListOfPost() {
        Optional<User> user = securityUtil.getCurrentUser();
        User presentUser = null;
        if (user.isPresent())
             presentUser = user.get();


        List<Post> posts = postRepository.findAllPostByFetchJoin();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post p : posts) {
            PostResponseDto responseDto = new PostResponseDto(p, checkUser(p.getUser().getUsername()));
            responseDtoList.add(responseDto);

            if (presentUser != null) {
                if (likeRepository.findByPostAndUser(p, presentUser).isPresent())
                    responseDto.changeIsLiked(true);
            }
        }
        return responseDtoList;
    }

    public PostResponseDto getPost(Long id) {
        User user = securityUtil.getCurrentUserOrError();
        Post post = findPost(id);
        PostResponseDto responseDto = new PostResponseDto(post, checkUser(post.getUser().getUsername()));
        if (likeRepository.findByPostAndUser(post, user).isPresent())
            responseDto.changeIsLiked(true);
        return responseDto;
    }

    public PostResponseDto writePost(PostRequestDto requestDto) {
        User user = securityUtil.getCurrentUserOrError();
        Post post = new Post(requestDto, user);
        return new PostResponseDto(postRepository.save(post), true);
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        checkUserOrError(post.getUser().getUsername());
        post.update(requestDto);
        return id;
    }

    public String deletePost(Long id) {
        Post post = findPost(id);
        checkUserOrError(post.getUser().getUsername());
        postRepository.deleteById(id);
        return post.getContentImg();
    }

    public Long changeLike(Long post_id) {
        User user = securityUtil.getCurrentUserOrError();
        Post post = findPost(post_id);

        Optional<Likes> like = likeRepository.findByPostAndUser(post, user);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            likeRepository.save(new Likes(post, user));
        }
        return post_id;
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 없습니다")
        );
    }

    private void checkUserOrError(String username) {
        if (!checkUser(username))
            throw new AccessDeniedException("게시글 작성자만 접근할 수 있습니다");
    }

    private boolean checkUser(String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserDetails))
            return false;
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return username.equals(userDetails.getUsername());
    }
}
