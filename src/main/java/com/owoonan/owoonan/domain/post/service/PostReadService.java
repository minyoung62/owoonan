package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.post.dto.PostDetailResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostSearchDto;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {
    private final PostRepository postRepository;

    public List<PostResponseDto> findAll(PostSearchDto postSearchDto) {
        return postRepository.findAllPostResponseDto(postSearchDto);
    }

    public PostDetailResponseDto findPostDetail(final Long postId, final String userId) {
        return postRepository.findPostDetail(postId, userId);
    }
}
