package com.owoonan.owoonan.domain.comment.api;

import com.owoonan.owoonan.domain.comment.dto.CommentCreateRequestDto;
import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import com.owoonan.owoonan.domain.comment.dto.CommentUpdateRequestDto;
import com.owoonan.owoonan.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentApiController {
  private final CommentService commentService;

  @PostMapping()
  public ResponseEntity<Long> create(@Valid @RequestBody CommentCreateRequestDto commentCreateRequestDto,
                                     @PathVariable Long postId) {
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(commentService.create(postId, commentCreateRequestDto.toEntity(), getPrincipal().getUsername()));
  }

  @GetMapping()
  public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long postId) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(postId));
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<Void> update(@PathVariable Long postId,
                                     @PathVariable Long commentId,
                                     @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
    commentService.update(commentId ,commentUpdateRequestDto.toEntity() ,getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> delete(@PathVariable Long postId,
                                     @PathVariable Long commentId) {
    commentService.delete(commentId, getPrincipal().getUsername());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  public User getPrincipal() {
    return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
