package com.owoonan.owoonan.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long postId;
    private String contents;
    private List<ImageDto> imageDtos;
    private Long commentCount;
    private Long likelyCount;
    private boolean isLike = false;

    public PostResponseDto(Long postId, String contents, Long commentCount, Long likelyCount) {
        this.postId = postId;
        this.contents = contents;
        this.commentCount = commentCount;
        this.likelyCount = likelyCount;
    }
}