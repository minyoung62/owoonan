package com.owoonan.owoonan.global.test.error;

import com.owoonan.owoonan.global.error.exception.EntityNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;

public class TestNotFoundException extends EntityNotFoundException {

    public TestNotFoundException(ErrorCode error) {
        super(error);
    }
}
