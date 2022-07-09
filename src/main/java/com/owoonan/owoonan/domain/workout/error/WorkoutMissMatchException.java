package com.owoonan.owoonan.domain.workout.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class WorkoutMissMatchException extends InvalidValueException {
    public WorkoutMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
