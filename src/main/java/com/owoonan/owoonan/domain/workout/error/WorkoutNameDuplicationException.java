package com.owoonan.owoonan.domain.workout.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class WorkoutNameDuplicationException extends InvalidValueException {
    public WorkoutNameDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
