package com.owoonan.owoonan.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private Long postId;
    private String imageUrl;
}
