package com.owoonan.owoonan.domain.post.repository;

import com.owoonan.owoonan.domain.post.dto.PostDetailResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostSearchDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostResponseDto> findAllPostResponseDto(PostSearchDto postSearchDto, String userId);

    PostDetailResponseDto findPostDetail(Long postId, String userId);
}
