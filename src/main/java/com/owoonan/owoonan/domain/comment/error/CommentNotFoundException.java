package com.owoonan.owoonan.domain.comment.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
