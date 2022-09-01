package com.owoonan.owoonan.domain.post.api;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.dto.PostCreateRequest;
import com.owoonan.owoonan.domain.post.repository.PostRepository;
import com.owoonan.owoonan.domain.post.service.PostService;
import com.owoonan.owoonan.domain.post.util.GivenImage;
import com.owoonan.owoonan.domain.post.util.GivenPost;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.domain.vo.RoleType;
import com.owoonan.owoonan.domain.user.util.GivenUser;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostApiControllerTest {
  @InjectMocks
  PostApiController postApiController;
  @Mock
  PostService postService;
  MockMvc mockMvc;

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private UserRepository userRepository;

  static User user;
  static Long postId;
  @BeforeEach
  void beforeEach() {
//    user = userRepository.save(GivenUser.toEntity());
//    if(user == null) throw new IllegalArgumentException("adsf");
//    List<MultipartFile> files = new ArrayList<>();
//    postId = postService.create(GivenPost.toEntity() ,files ,user.getUserId());

    mockMvc = MockMvcBuilders.standaloneSetup(postApiController).build();
    user = GivenUser.toEntity();
    List<GrantedAuthority> role = AuthorityUtils.createAuthorityList(RoleType.USER.name());
    org.springframework.security.core.userdetails.User user1 =
      new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), role);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user1, user1.getPassword(), role);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  @WithMockUser(roles = "USER")
  void create() throws Exception{
    // given
    LocalDateTime time = LocalDateTime.of(2019, 11, 12, 12, 32,22);

    ArgumentCaptor<PostCreateRequest> postCreateRequestArgumentCaptor = ArgumentCaptor.forClass(PostCreateRequest.class);
    List<MultipartFile> imageFiles = GivenImage.getImages();
    PostCreateRequest req = PostCreateRequest.builder()
      .contents("test contents")
      .images(imageFiles)
      .build();

    when(postService.create(any(), any(), any())).thenReturn(1L);

    // when
    mockMvc.perform(
      multipart("/api/v1/post")
        .file("images", imageFiles.get(0).getBytes())
        .file("images", imageFiles.get(1).getBytes())
        .param("contents", req.getContents())
        .with(requestPostProcessor -> {
          requestPostProcessor.setMethod("POST");
          return requestPostProcessor;
        })
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(roles = "USER")
  void findAllPost() throws Exception {



    mockMvc.perform(get("/api/v1/post")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}
