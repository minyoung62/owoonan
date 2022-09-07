package com.owoonan.owoonan.domain.comment.util;

import com.owoonan.owoonan.domain.comment.domain.Comment;

public class GivenComment {
    public final static String contents = "테스트 댓글";

    public static Comment toEntity() {
        return Comment.builder()
                .contents(contents)
                .build();
    }
}
