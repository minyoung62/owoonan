package com.owoonan.owoonan.domain.routine.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class RoutineMissMatchException extends InvalidValueException {
    public RoutineMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
