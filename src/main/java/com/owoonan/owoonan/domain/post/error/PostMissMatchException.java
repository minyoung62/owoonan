package com.owoonan.owoonan.domain.post.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class PostMissMatchException extends InvalidValueException {
    public PostMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
