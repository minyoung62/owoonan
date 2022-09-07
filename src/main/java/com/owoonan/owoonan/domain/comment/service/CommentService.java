package com.owoonan.owoonan.domain.comment.service;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import com.owoonan.owoonan.domain.comment.error.CommentMissMatchException;
import com.owoonan.owoonan.domain.comment.error.CommentNotFoundException;
import com.owoonan.owoonan.domain.comment.repository.CommentRepository;
import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long create(final Long postId, final Comment comment, final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        comment.addPost(post);
        comment.addUser(user);

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();

    }

    public void update(final Long commentId, final Comment updatedComment,final  String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        if (!userId.equals(comment.getUser().getUserId())) throw new CommentMissMatchException(ErrorCode.COMMENT_MISS_MATCH);

        comment.patch(updatedComment);

        commentRepository.save(comment);
    }

    public void delete(final Long commentId, final String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!userId.equals(comment.getUser().getUserId())) throw new CommentMissMatchException(ErrorCode.COMMENT_MISS_MATCH);

        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> findAll(final Long postId) {
        return commentRepository.findAllComment(postId);
    }
}
