package com.owoonan.owoonan.domain.post.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
