package com.owoonan.owoonan.domain.post.repository;

import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import com.owoonan.owoonan.domain.likely.domain.Likely;
import com.owoonan.owoonan.domain.likely.domain.QLikely;
import com.owoonan.owoonan.domain.post.dto.ImageDto;
import com.owoonan.owoonan.domain.post.dto.PostDetailResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostSearchDto;
import com.owoonan.owoonan.domain.user.domain.QUser;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.owoonan.owoonan.domain.comment.domain.QComment.comment;
import static com.owoonan.owoonan.domain.image.domain.QImage.image;
import static com.owoonan.owoonan.domain.likely.domain.QLikely.likely;
import static com.owoonan.owoonan.domain.post.domain.QPost.post;
import static com.owoonan.owoonan.domain.user.domain.QUser.user;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
  private final JPAQueryFactory query;

  public List<PostResponseDto> findAllPostResponseDto(PostSearchDto postSearchDto, String userId) {
    List<PostResponseDto> result = query.select(Projections.constructor(PostResponseDto.class,
        post.id,
        post.content,
        post.username,
        post.userId,
        comment.count(),
        likely.count(),
        post.createTime,
        post.lastModifiedTime ))
      .from(post)
      .leftJoin(comment).on(post.id.eq(comment.post.id))
      .leftJoin(likely).on(post.id.eq(likely.post.id))
      .groupBy(post)
      .offset(postSearchDto.getOffset())
      .limit(postSearchDto.getLimit())
      .fetch();


    List<ImageDto> imageDtos = query
      .select(Projections.constructor(ImageDto.class, image.id, image.post.id, image.imageUrl))
      .from(image)
      .fetch();

    Map<Long, List<ImageDto>> imageDtoMap = imageDtos.stream().collect(Collectors.groupingBy(image -> image.getPostId()));

    result.forEach(p -> p.setImageDtos(imageDtoMap.get(p.getPostId())));

    return result;

  }

  public PostDetailResponseDto findPostDetail(Long postId, String userId) {
    PostDetailResponseDto postDetailResponseDto = query.select(Projections.constructor(PostDetailResponseDto.class,
        post.id,
        post.content,
        post.username,
        likely.count()))
      .from(post)
      .leftJoin(likely).on(post.id.eq(likely.post.id))
      .where(post.id.eq(postId))
      .groupBy(post)
      .fetchOne();

    if (userId != null) {
      Likely likely = query.select(QLikely.likely)
        .from(QLikely.likely)
        .where(QLikely.likely.post.id.eq(postId).and(QLikely.likely.user.userId.eq(userId)))
        .fetchOne();
      if (likely != null) {
        postDetailResponseDto.setIsLike(true);
      }
    }


    List<ImageDto> imageDtos = query.select(Projections.constructor(ImageDto.class, image.id, image.post.id, image.imageUrl))
      .from(image)
      .where(image.post.id.eq(postId))
      .fetch();

    List<CommentResponseDto> commentResponseDtos = query.select(
        Projections.constructor(CommentResponseDto.class,
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

    postDetailResponseDto.setImageUrls(imageDtos);
    postDetailResponseDto.setComments(commentResponseDtos);

    return postDetailResponseDto;
  }


}
