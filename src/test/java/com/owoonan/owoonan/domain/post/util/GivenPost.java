package com.owoonan.owoonan.domain.post.util;

import com.owoonan.owoonan.domain.post.domain.Post;

import java.time.LocalDate;

public class GivenPost {

    public static final String content = "테스트용 포스트 컨텐츠~";
    public static final LocalDate workoutEndTime = LocalDate.now();
    public static final LocalDate workoutStartTime = LocalDate.now();

    public static Post toEntity() {
        return Post.builder()
                .content(content)
                .workoutEndTime(workoutEndTime)
                .workoutStartTime(workoutStartTime)
                .build();
    }
}
