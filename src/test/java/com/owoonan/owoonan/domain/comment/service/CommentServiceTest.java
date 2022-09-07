package com.owoonan.owoonan.domain.comment.service;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import com.owoonan.owoonan.domain.comment.error.CommentNotFoundException;
import com.owoonan.owoonan.domain.comment.repository.CommentRepository;
import com.owoonan.owoonan.domain.comment.util.GivenComment;
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

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    static User user;
    static Post post;
    static Long commentId;

    @BeforeEach
    void init() {
        user = userRepository.save(GivenUser.toEntity());
        List<MultipartFile> files = new ArrayList<>();
        Long postId = postService.create(GivenPost.toEntity(),files , user.getUserId());
        post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        commentId = commentService.create(post.getId(), GivenComment.toEntity(), user.getUserId());
    }

    @Test
    void create() {
        // given
        Comment comment = Comment.builder().user(user).contents("댓글테스트!").post(post).build();
        // when
        Long commentId = commentService.create(post.getId(), comment, user.getUserId());
        Comment savedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        // then
        Assert.assertEquals(commentId, savedComment.getId());
    }

    @Test
    void update() {
        // given
        String updatedContents = "테스트 댓글 수정";
        Comment updatedComment = Comment.builder().contents(updatedContents).build();

        // when
        commentService.update(commentId, updatedComment, user.getUserId());
        Comment exceptedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        // then
        Assert.assertEquals(updatedContents, exceptedComment.getContents());
    }

    @Test
    void delete() {
        // given // when
        commentService.delete(commentId, user.getUserId());

        // then
        Assert.assertThrows(CommentNotFoundException.class,
                () -> {
                    commentRepository.findById(commentId).orElseThrow( () ->
                            new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
                } );
    }

    @Test
    void findAll() {
        // given
        commentRepository.deleteAll();
        int commentSize = 5;
        for(int i = 0; i < commentSize; i++) {
            Comment createdComment = Comment.builder().post(post).user(user).contents("테스트 댓글" + i).build();
            commentRepository.save(createdComment);
        }
        // when
        List<CommentResponseDto> commentResponseDto = commentService.findAll(post.getId());

        // then
        Assert.assertEquals(commentSize, commentResponseDto.size());
    }
}
