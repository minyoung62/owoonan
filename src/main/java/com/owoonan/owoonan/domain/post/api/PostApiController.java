package com.owoonan.owoonan.domain.post.api;

import com.owoonan.owoonan.domain.post.dto.PostCreateRequest;
import com.owoonan.owoonan.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor

public class PostApiController {

  private final PostService postService;

  @PostMapping()
  public ResponseEntity<Long> create(@Valid @ModelAttribute PostCreateRequest postCreateRequest) {

    Long postId = postService.create(postCreateRequest.toEntity(), postCreateRequest.getImages(), getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(postId);
  }

  public User getPrincipal() {
    return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
