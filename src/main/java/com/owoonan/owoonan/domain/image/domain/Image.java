package com.owoonan.owoonan.domain.image.domain;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Post post;

    @Builder
    public Image(Long id, String imageUrl, Post post) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.post = post;
    }
}
