package com.owoonan.owoonan.domain.post.dto;

import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class PostDetailResponseDto {
  private Long postId;
  private String contents;
  private String username;
  private String userId;
  private List<ImageDto> imageUrls  = new ArrayList<>();
  private List<CommentResponseDto> comments  = new ArrayList<>();
  private Long likelyCount;
  private Boolean isLike = false;

  public PostDetailResponseDto(Long postId, String contents, String username, String userId, Long likelyCount) {
    this.postId = postId;
    this.contents = contents;
    this.username = username;
    this.userId = userId;
    this.likelyCount = likelyCount;
  }
}
