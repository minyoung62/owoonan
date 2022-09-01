package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.dto.PostUpdateDto;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.post.util.GivenImage;
import com.owoonan.owoonan.domain.post.util.GivenPost;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.util.GivenUser;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;
import javax.transaction.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
      List<MultipartFile> files = GivenImage.getImages();
        postId = postService.create(GivenPost.toEntity() ,files ,user.getUserId());
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
      Long postId = postService.create(createdPost , GivenImage.getImages(), user.getUserId());
      Post savedPost = postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        // then
        Assert.assertEquals(2, savedPost.getImages().size());
        Assert.assertEquals(postId, savedPost.getId());
    }

    @Test
    void update() {
        // given
      String imgPath1 = "update1.png";
      String imgName1 = "update1";
      String imgPath2 = "add1.png";
      String imgName2 = "add1";
      List<MultipartFile> updateImages = List.of(
        new MockMultipartFile(imgName1, imgPath1, MediaType.IMAGE_PNG_VALUE, imgName1.getBytes()),
        new MockMultipartFile(imgName2, imgPath2, MediaType.IMAGE_PNG_VALUE, imgName2.getBytes())
      );
      Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
      Long updateImageId = findPost.getImages().get(0).getId();
      List<Long> updateIds = List.of(updateImageId);
      System.out.println(updateIds);
      PostUpdateDto postUpdateDto = new PostUpdateDto("updateContante", updateIds, updateImages);

        // when
        postService.update(postId,
          postUpdateDto.toEntity(),
          postUpdateDto.getUpdateImageIds(),
          postUpdateDto.getUpdateImages(),
          user.getUserId());

        Post updatedPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        // then
        Assert.assertEquals(postUpdateDto.getContents(), updatedPost.getContent());
      Assert.assertEquals(postUpdateDto.getUpdateImages().size(), 2);

    }

    @Test
    void delete() {
        // given when
        postService.delete(postId, user.getUserId());
        //then
        Assert.assertThrows(PostNotFoundException.class,
                () -> {
                    postRepository.findById(postId).orElseThrow( () ->
                            new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
                } );
    }


}
