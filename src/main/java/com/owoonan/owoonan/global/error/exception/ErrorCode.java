package com.owoonan.owoonan.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    TEST(400, "test", "test");

    private final int status;
    private final String code;
    private final String message;
}
