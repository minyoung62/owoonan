package com.owoonan.owoonan.domain.comment.repository;

import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> findAllComment(Long postId);

}
