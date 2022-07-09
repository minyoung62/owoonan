package com.owoonan.owoonan.global.error;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private String code;

    private ErrorResponse(final ErrorCode errorCode) {
        message = errorCode.getMessage();
        status = errorCode.getStatus();
        code = errorCode.getCode();
    }

    private ErrorResponse(String message, int status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse createBinding(final String message, final int status, final String code) {
        return new ErrorResponse(message, status, code);
    }
}
