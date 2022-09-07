package com.owoonan.owoonan.domain.comment.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class CommentMissMatchException extends InvalidValueException {
    public CommentMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
