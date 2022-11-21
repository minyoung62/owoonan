package com.owoonan.owoonan.domain.post.service;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.comment.repository.CommentRepository;
import com.owoonan.owoonan.domain.image.domain.Image;
import com.owoonan.owoonan.domain.image.repository.ImageRepository;
import com.owoonan.owoonan.domain.likely.domain.Likely;
import com.owoonan.owoonan.domain.likely.repository.LikelyRepository;
import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.dto.PostDetailResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import com.owoonan.owoonan.domain.post.dto.PostSearchDto;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.util.GivenUser;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
<<<<<<< HEAD
=======
import org.junit.jupiter.api.BeforeAll;
>>>>>>> c3ebd1ad24f989028ec3edf9d2212f156d885d5f
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD
import org.springframework.util.StopWatch;
=======
>>>>>>> c3ebd1ad24f989028ec3edf9d2212f156d885d5f

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(true)
class PostReadServiceTest {


<<<<<<< HEAD
  @Autowired
  private PostReadService postReadService;

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private LikelyRepository likelyRepository;
  @Autowired
  private ImageRepository imageRepository;
  @Autowired
  private UserRepository userRepository;

  static User user;
  static int postCount = 10;
  static int commentCount = 5;
  static int likelyCount = 1;
  static int imageUrlcount = 5;
  static String postContents = "포스트내용물";
  static String commentcontents = "댓글내용물";
  static String imageUrl = "이미지url";

  StopWatch stopWatch = new StopWatch();

  @BeforeEach
  void init() {
    user = userRepository.save(GivenUser.toEntity());

    LocalDate now = LocalDate.now();
    for (int i = 0; i < postCount; i++) {
      Post post = Post.builder()
        .content(postContents + i)
        .user(user)
        .username(user.getUsername())

        .workoutEndTime(now)
        .workoutStartTime(now).build();

      postRepository.save(post);
    }

    List<Post> allPost = postRepository.findAll();
    allPost.forEach((post) -> {
      for (int i = 0; i < commentCount; i++) {

        Comment comment = Comment.builder().post(post).user(user).contents(commentcontents + i).build();
        commentRepository.save(comment);
      }
      for (int i = 0; i < likelyCount; i++) {

        Likely likely = Likely.builder().post(post).user(user).build();
        likelyRepository.save(likely);
      }
      for (int i = 0; i < imageUrlcount; i++) {

        Image image = Image.builder().imageName("imageName").imageUrl("imageUrl").post(post).build();
        imageRepository.save(image);
      }
    });

  }

  @Test
  void findAll() {
    //given
    PostSearchDto postSearchDto = new PostSearchDto(0, 10);
    // when
    List<PostResponseDto> postResponseDtos = postReadService.findAll(postSearchDto, user.getUserId());
    //then

    assertEquals(10, postResponseDtos.size());
  }

  @Test
  void findPostDetail() {
    // given
    PostSearchDto postSearchDto = new PostSearchDto(0, 10);
    PostResponseDto postResponseDto = postRepository.findAllPostResponseDto(postSearchDto, user.getUserId()).get(0);
    stopWatch.start();
    // when
    PostDetailResponseDto postDetail = postReadService.findPostDetail(postResponseDto.getPostId(), user.getUserId());
    stopWatch.stop();
    System.out.println(stopWatch.getTotalTimeMillis());
    // then
    assertEquals(postResponseDto.getPostId(), postDetail.getPostId());
    assertEquals(postResponseDto.getContents(), postDetail.getContents());
  }
=======
    @Autowired
    private PostReadService postReadService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikelyRepository likelyRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

    static User user;
    static int postCount = 10;
    static int commentCount = 5;
    static int likelyCount = 1;
    static int imageUrlcount = 5;
    static String postContents = "포스트내용물";
    static String commentcontents = "댓글내용물";
    static String imageUrl = "이미지url";

    @BeforeEach
    void init() {
        user = userRepository.save(GivenUser.toEntity());

        LocalDate now = LocalDate.now();
        for(int i = 0; i< postCount; i++) {
            Post post = Post.builder()
                    .content(postContents + i)
                    .user(user)
                    .workoutEndTime(now)
                    .workoutStartTime(now).build();

            postRepository.save(post);
        }

        List<Post> allPost = postRepository.findAll();
        allPost.forEach((post) -> {
            for(int i = 0; i < commentCount; i++) {

                Comment comment = Comment.builder().post(post).user(user).contents(commentcontents + i).build();
                commentRepository.save(comment);
            }
            for(int i = 0; i < likelyCount; i++) {

                Likely likely = Likely.builder().post(post).user(user).build();
                likelyRepository.save(likely);
            }
            for(int i = 0; i < imageUrlcount; i++) {

                Image image = Image.builder().imageName("imageName").imageUrl("imageUrl").post(post).build();
                imageRepository.save(image);
            }
        });

    }

    @Test
    void findAll() {
        //given
        PostSearchDto postSearchDto = new PostSearchDto(0, 10);
        // when
        List<PostResponseDto> postResponseDtos = postReadService.findAll(postSearchDto, user.getUserId());
        //then

        assertEquals(10, postResponseDtos.size());
    }

    @Test
    void findPostDetail() {
        // given
        PostSearchDto postSearchDto = new PostSearchDto(0, 10);
        PostResponseDto postResponseDto = postRepository.findAllPostResponseDto(postSearchDto, user.getUserId()).get(0);

        // when
        PostDetailResponseDto postDetail = postReadService.findPostDetail(postResponseDto.getPostId(), null);
        // then
        assertEquals(postResponseDto.getPostId(), postDetail.getPostId());
        assertEquals(postResponseDto.getContents(), postDetail.getContents());
    }
>>>>>>> c3ebd1ad24f989028ec3edf9d2212f156d885d5f
}
