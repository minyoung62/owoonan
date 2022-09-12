package com.owoonan.owoonan.domain.image.domain;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String imageName;

    @NotNull
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Builder
    public Image(Long id, String imageName, String imageUrl, Post post) {
        this.id = id;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.post = post;
    }

}
