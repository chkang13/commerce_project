package com.my.commerce.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        return new BaseResponse<>(e.getStatus());
    }

    @ExceptionHandler(BasicException.class)
    public BaseResponse<BaseResponseStatus> basicException(BasicException e) {
        return new BaseResponse<>(e.getStatus());
    }
}
