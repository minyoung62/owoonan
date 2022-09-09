package com.owoonan.owoonan.domain.post.domain;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.image.domain.Image;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 100000)
  @NotNull
  private String content;

  @NotNull
  private String username;

  private LocalDate workoutStartTime;

  private LocalDate workoutEndTime;


  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Post(Long id, String content, String username, LocalDate workoutStartTime, LocalDate workoutEndTime, User user) {
    this.id = id;
    this.content = content;
    this.username = username;
    this.workoutStartTime = workoutStartTime;
    this.workoutEndTime = workoutEndTime;
    this.user = user;
  }


  public void addUser(User user) {
    this.user = user;
    this.username = user.getUsername();
  }

  public void patch(Post updatedPost, List<Long> updatedImageIds) {
    this.content = updatedPost.getContent();
    List<Long> imgIds = images.stream().map(i -> i.getId()).collect(Collectors.toList());
    updatedImageIds.forEach(updatedImageId -> {
      if (imgIds.contains(updatedImageId)) {
        Image findImage = images.stream().filter(image -> updatedImageId.equals(image.getId())).findAny().orElse(null);
        images.remove(findImage);
      }
    });
    updatedPost.getImages().forEach(images -> {
      this.images.add(images);

    });

  }

  public void addImages(List<Image> images) {
    this.images = images;

  }
}
