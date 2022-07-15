package com.owoonan.owoonan.domain.record.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class RecordNotFoundException extends EntityNotFoundException {
    public RecordNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
