package com.owoonan.owoonan.domain.user.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
