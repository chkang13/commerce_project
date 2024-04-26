package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;

import com.my.commerce.dto.Member.PostEmailCheckReqDTO;
import com.my.commerce.dto.Member.PostEmailReqDTO;
import com.my.commerce.dto.Member.PostMemberReqDTO;
import com.my.commerce.security.PostLoginReqDTO;
import com.my.commerce.security.TokenDTO;
import com.my.commerce.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * 회원가입 API
     *
     * @param postMemberReqDTO 회원 정보
     * @return String
     */
    @PostMapping("/signup")
    public BaseResponse<String> signupMember(@RequestBody PostMemberReqDTO postMemberReqDTO) {
        String signupMemberRes = authService.signupMember(postMemberReqDTO);

        return new BaseResponse<>(signupMemberRes);
    }

    /**
     * 로그인 API
     *
     * @param postLoginReqDTO 로그인 정보
     * @return TokenDTO 토큰 정보
     */
    @PostMapping("/login")
    public BaseResponse<TokenDTO> loginMember(@RequestBody PostLoginReqDTO postLoginReqDTO) {
        TokenDTO tokenDTO = authService.login(postLoginReqDTO);

        return new BaseResponse<>(tokenDTO);
    }

    /**
     * 메일 발송 API
     *
     * @param postEmailReqDTO 이메일 정보
     * @return String
     */
    @PostMapping("/email")
    private BaseResponse<String> emailCheck(@RequestBody PostEmailReqDTO postEmailReqDTO) {
        String result = authService.emailCheck(postEmailReqDTO);

        return new BaseResponse<>(result);
    }

    /**
     * 인증 번호 확인 API
     *
     * @param postEmailCheckReqDTO 인증 번호 정보
     * @return String
     */
    @PostMapping("/email/check")
    private BaseResponse<String> authCodeCheck(@RequestBody PostEmailCheckReqDTO postEmailCheckReqDTO) {
        String result = authService.authCodeCheck(postEmailCheckReqDTO);

        return new BaseResponse<>(result);
    }

    /**
     * 로그아웃 API
     *
     * @return String
     */
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        String context= authService.logout(request);

        return new BaseResponse<>(context);
    }

}
