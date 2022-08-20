package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import com.owoonan.owoonan.domain.post.error.PostMissMatchException;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long create(final Post requestPost, final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        requestPost.add(user);
        Post savePost = postRepository.save(requestPost);

        return savePost.getId();
    }

    public void update(final Long postId, final Post updatedPost, final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!userId.equals(post.getUser().getUserId())) throw new PostMissMatchException(ErrorCode.POST_NOT_MISSMATCH);
        post.patch(updatedPost);

        postRepository.save(post);
    }

    public void delete(Long postId, String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!userId.equals(post.getUser().getUserId())) throw new PostMissMatchException(ErrorCode.POST_NOT_MISSMATCH);

        postRepository.delete(post);
    }
}
