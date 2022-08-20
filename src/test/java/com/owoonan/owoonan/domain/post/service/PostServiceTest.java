package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.post.util.GivenPost;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.util.GivenUser;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.testng.Assert;
import javax.transaction.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    static User user;
    static Long postId;

    @BeforeEach
    void init () {
        user = userRepository.save(GivenUser.toEntity());
        postId = postService.create(GivenPost.toEntity(), user.getUserId());
    }

    @Test
    void create() {
        // given
        Post createdPost = Post.builder()
                .content("content")
                .workoutStartTime(LocalDate.now())
                .workoutEndTime(LocalDate.now())
                .build();
        // where
        Long postId = postService.create(createdPost, user.getUserId());
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        // then
        Assert.assertEquals(postId, savedPost.getId());
    }

    @Test
    void update() {
        // given
        Post updatedPost = Post.builder()
                .content("업데이트된포스트입니다")
                .workoutEndTime(LocalDate.now())
                .workoutStartTime(LocalDate.now())
                .build();
        // when
        postService.update(postId, updatedPost, user.getUserId());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        // then
        Assert.assertEquals(updatedPost.getContent(), post.getContent());
    }

    @Test
    void delete() {
        // given when
        postService.delete(postId, user.getUserId());

        //then
       ;
        Assert.assertThrows(PostNotFoundException.class,
                () -> {
                    postRepository.findById(postId).orElseThrow( () ->
                            new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
                } );
    }


}