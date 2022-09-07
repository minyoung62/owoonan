package com.owoonan.owoonan.domain.comment.repository;

import com.owoonan.owoonan.domain.comment.domain.QComment;
import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.owoonan.owoonan.domain.comment.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory query;

    public List<CommentResponseDto> findAllComment(Long postId) {
        return  query.select(Projections.constructor(CommentResponseDto.class,
                comment.id,
                comment.post.id,
                comment.user.userId,
                comment.user.username,
                comment.contents,
                comment.createTime,
                comment.lastModifiedTime))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetch();
    }

}
