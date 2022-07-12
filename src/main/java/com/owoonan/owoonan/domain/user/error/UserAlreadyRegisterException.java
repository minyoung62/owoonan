package com.owoonan.owoonan.domain.user.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class UserAlreadyRegisterException extends InvalidValueException {
    public UserAlreadyRegisterException(ErrorCode errorCode) {
        super(errorCode);
    }
}
