package com.my.commerce.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * Default status code
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),
    NOT_FOUND(false, 404, "요청을 찾을 수 없습니다."),
    SERVER_ERROR(false, 500, "서버 처리에 오류가 발생하였습니다."),

    AUTHORITY_INVALID(false, 480, "권한 정보가 없는 토큰입니다."),
    EXIST_EMAIL_ERROR(false, 481, "이미 존재하는 이메일입니다."),
    INVALID_AUTH_CODE(false,482,"인증번호가 일치하지 않습니다."),
    EXPIRE_AUTH_CODE(false,483,"인증번호가 만료 되었습니다."),
    MEMBER_INVALID_USER(false, 600, "사용자가 존재하지 않습니다."),
    MEMBER_PASSWORD_DISCORD(false, 601, "비밀번호가 일치하지 않습니다."),
    PRODUCT_INVALID_ID(false, 700, " 상품 아이디 값을 확인해주세요.");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
