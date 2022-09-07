package com.owoonan.owoonan.domain.post.dto;

import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class PostDetailResponseDto {
    private Long postId;
    private String contents;
    private String username;
    private List<ImageDto> imageUrls;
    private List<CommentResponseDto> comments;
    private Long likelyCount;
    private Boolean isLike = false;

    public PostDetailResponseDto(Long postId, String contents, String username, Long likelyCount) {
        this.postId = postId;
        this.contents = contents;
        this.username = username;
        this.likelyCount = likelyCount;
    }
}