package com.owoonan.owoonan.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String userId;
    private String username;
    private String contents;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
}
