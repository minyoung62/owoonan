package com.owoonan.owoonan.domain.likely.domain;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Likely extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;


    @Builder
    public Likely(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }
}
