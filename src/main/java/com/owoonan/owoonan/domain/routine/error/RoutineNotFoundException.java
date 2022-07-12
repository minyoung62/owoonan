package com.owoonan.owoonan.domain.routine.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class RoutineNotFoundException extends EntityNotFoundException {

    public RoutineNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
