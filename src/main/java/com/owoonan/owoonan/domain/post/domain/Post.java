package com.owoonan.owoonan.domain.post.domain;

import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.image.domain.Image;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100000)
    @NotNull
    private String content;

    private LocalDate workoutStartTime;

    private LocalDate workoutEndTime;

    @OneToMany(mappedBy = "post",  cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(Long id, String content, LocalDate workoutStartTime, LocalDate workoutEndTime, User user) {
        this.id = id;
        this.content = content;
        this.workoutStartTime = workoutStartTime;
        this.workoutEndTime = workoutEndTime;
        this.user = user;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void patch(Post updatedPost) {
        this.content = updatedPost.getContent();
        this.workoutEndTime = updatedPost.workoutEndTime;
        this.workoutStartTime = updatedPost.workoutStartTime;
    }

  public void addImages(List<Image> images) {
    this.images = images;
  }
}
