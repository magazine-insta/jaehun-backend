package pbl.magazine.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pbl.magazine.dto.PostRequestDto;
import pbl.magazine.dto.PostResponseDto;
import pbl.magazine.model.User;
import pbl.magazine.repository.UserRepository;
import pbl.magazine.security.UserDetailsImpl;
import pbl.magazine.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/post")
    public List<PostResponseDto> getListOfPosts() {
        return postService.getListOfPost();
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/api/post")
    public PostResponseDto writePost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.writePost(requestDto, user);
    }

    @PutMapping("/api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @PutMapping("/api/post/{id}/like")
    public Long changeLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.changeLike(id, userDetails.getUser());
    }

    @DeleteMapping("/api/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

}
