package com.owoonan.owoonan.domain.comment.domain;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10000)
    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(Long id, String contents, Long parentId, User user, Post post) {
        this.id = id;
        this.contents = contents;
        this.parentId = parentId;
        this.user = user;
        this.post = post;
    }
}
