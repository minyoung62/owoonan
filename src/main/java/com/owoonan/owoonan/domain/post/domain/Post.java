package com.owoonan.owoonan.domain.post.domain;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100000)
    @NotNull
    private String content;

    @NotNull
    private LocalDate workoutStartTime;

    @NotNull
    private LocalDate workoutEndTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
}
