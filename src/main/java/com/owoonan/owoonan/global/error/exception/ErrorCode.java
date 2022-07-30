package com.owoonan.owoonan.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    METHOD_NOT_ALLOWED(405, "C001", "잘못된 요청입니다."),
    HANDLE_ACCESS_DENIED(400, "c002", "잘못된 접근입니다."),
    INTERNAL_SERVER_ERROR(500, "C005", "서버 내부 오류 입니다."),

    // jwt
    TOKEN_IS_NULL(401, "J001", "토큰이 존재하지 않습니다."),

    // User
    USER_NOT_FOUND(500, "U001", "해당 유저가 존재하지 않습니다"),

    USER_ALREADY_REGISTER(506, "U002", "이미 가입한 유저입니다"),

    // Workout
    WORKOUT_NOT_FOUND(501, "W001", "해당 운동이 존재하지 않습니다"),
    WORKOUT_MISS_MATCH(502, "W001", "해당 사용자의 운동이 아닙니다"),
    WORKOUT_NAME_DUPLICATION(503, "W003", "중복된 운동 이름 입니다"),

    // Routine
    ROUTINE_NOT_FOUND(504, "R001", "해당 운동 루틴은 존재하지 않습니다"),
    ROUTINE_MISS_MATCH(505, "R002", "해당 사용자의 루틴이 아닙니다"),

    // Record
    RECORD_NOT_FOUND(507, "RC001", "해당 기록이 존재하지 않습니다"),
    RECORD_MISS_MATCH(508, "RC002", "해당 사용자의 기록이 아닙니다");
    private final int status;
    private final String code;
    private final String message;

    public static String getMsg(ErrorCode errorCode){
        return errorCode.message;
    }
}
