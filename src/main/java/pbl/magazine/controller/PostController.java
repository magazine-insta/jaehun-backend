package pbl.magazine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbl.magazine.dto.PostRequestDto;
import pbl.magazine.dto.PostResponseDto;
import pbl.magazine.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/post")
    public ResponseEntity<List<PostResponseDto>> getListOfPosts() {
        return ResponseEntity.ok().body(postService.getListOfPost());
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @PostMapping("/api/post")
    public PostResponseDto writePost(@RequestBody PostRequestDto requestDto) {
        return postService.writePost(requestDto);
    }

    @PutMapping("/api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @PutMapping("/api/post/{id}/like")
    public Long changeLike(@PathVariable Long id) {
        return postService.changeLike(id);
    }

    @DeleteMapping("/api/post/{id}")
    public String deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

}
