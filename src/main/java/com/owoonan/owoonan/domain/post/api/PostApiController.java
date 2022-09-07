package com.owoonan.owoonan.domain.post.api;

import com.owoonan.owoonan.domain.post.dto.*;
import com.owoonan.owoonan.domain.post.service.PostReadService;
import com.owoonan.owoonan.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/post/")
@RequiredArgsConstructor

public class PostApiController {

  private final PostService postService;
  private final PostReadService postReadService;

  @PostMapping()
  public ResponseEntity<Long> create(@Valid @ModelAttribute PostCreateRequest postCreateRequest) {
    System.out.println(getPrincipal().getUsername());
    Long postId = postService.create(postCreateRequest.toEntity(), postCreateRequest.getImages(), getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(postId);
  }

  @GetMapping()
  public ResponseEntity<List<PostResponseDto>> findAllPost(@ModelAttribute PostSearchDto postSearchDto) {
    return ResponseEntity.status(HttpStatus.OK).body(postReadService.findAll(postSearchDto, getPrincipal().getUsername()));
  }

  @GetMapping("{postId}")
  public ResponseEntity<PostDetailResponseDto> findOnePostDetail(@PathVariable Long postId) {
    return ResponseEntity.status(HttpStatus.OK).body(postReadService.findPostDetail(postId, getPrincipal().getUsername()));
  }

  @PutMapping("{postId}")
  public ResponseEntity<Void> patch(
            @PathVariable Long postId,
            @Valid @ModelAttribute PostUpdateDto postUpdateDto) {
    postService.update(postId, postUpdateDto.toEntity(),postUpdateDto.getUpdateImageIds() ,postUpdateDto.getUpdateImages() ,getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("{postId}")
  public ResponseEntity<Void> delete(@PathVariable Long postId) throws IOException {
    postService.delete(postId, getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.OK).build();
  }



  public User getPrincipal() {
     if (SecurityContextHolder
        .getContext()
        .getAuthentication().getPrincipal()
        .equals("anonymousUser")) {
        return null;
     }
    return (org.springframework.security.core.userdetails.User) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();


  }
}
