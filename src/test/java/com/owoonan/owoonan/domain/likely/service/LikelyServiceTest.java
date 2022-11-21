package com.owoonan.owoonan.domain.likely.service;

import com.owoonan.owoonan.domain.likely.domain.Likely;
import com.owoonan.owoonan.domain.likely.repository.LikelyRepository;
import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.error.PostNotFoundException;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.post.service.PostService;
import com.owoonan.owoonan.domain.post.util.GivenPost;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.util.GivenUser;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikelyServiceTest {

    @Autowired
    private LikelyService likelyService;
    @Autowired
    private LikelyRepository likelyRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    static User user;
    static Post post;

    @BeforeEach
    void init() {
        user = userRepository.save(GivenUser.toEntity());
        List<MultipartFile> files = new ArrayList<>();
        Long postId = postService.create(GivenPost.toEntity(), files, user.getUserId());
        post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }

    @Test
    void likePost() {
        // given // when
        Long likelyId = likelyService.likePost(post.getId(), user.getUserId());
        Optional<Likely> likely = likelyRepository.findById(likelyId);
        if (likely.isEmpty()) {
            return;
        }
        Likely expectedLikely = likely.get();
        // then
        Assert.assertEquals(likelyId, expectedLikely.getId());
    }

//    @Test
//    void unlikePost() {
//        // given
//        Long likelyId = likelyService.likePost(post.getId(), user.getUserId());
//        // when
//        likelyService.unLikePost(likelyId, user.getUserId());
//        // then
//        Assert.assertThrows(IllegalArgumentException.class,
//                () -> {
//                    likelyRepository.findById(likelyId).orElseThrow( () ->
//                            new IllegalArgumentException("no data"));
//                } );
//    }
}
