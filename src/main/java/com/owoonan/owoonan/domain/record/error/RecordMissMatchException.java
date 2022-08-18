package com.owoonan.owoonan.domain.record.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.error.exception.InvalidValueException;

public class RecordMissMatchException extends InvalidValueException {
    public RecordMissMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
