package com.owoonan.owoonan.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
  private Long postId;
  private String contents;
  private String username;
  private String userId;
  private List<ImageDto> imageDtos = new ArrayList<>();
  private Long commentCount;
  private LocalDateTime createdTime;
  private LocalDateTime lastModifiedTime;
  private Long likelyCount;
  private boolean isLike = false;

  public PostResponseDto(Long postId,
                         String contents,
                         String username,
                         String userId,
                         Long commentCount,
                         Long likelyCount,
                         LocalDateTime createdTime,
                         LocalDateTime lastModifiedTime) {
    this.postId = postId;
    this.contents = contents;
    this.username = username;
    this.userId = userId;
    this.commentCount = commentCount;
    this.likelyCount = likelyCount;
    this.createdTime = createdTime;
    this.lastModifiedTime = lastModifiedTime;
  }
}
