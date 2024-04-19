package com.my.commerce.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * Default status code
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),
    NOT_FOUND(false, 404, "요청을 찾을 수 없습니다."),
    SERVER_ERROR(false, 500, "서버 처리에 오류가 발생하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
