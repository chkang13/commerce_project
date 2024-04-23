package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;

import com.my.commerce.common.BasicException;
import com.my.commerce.dto.PostEmailCheckReqDTO;
import com.my.commerce.dto.PostEmailReqDTO;
import com.my.commerce.dto.PostMemberReqDTO;
import com.my.commerce.security.PostLoginReqDTO;
import com.my.commerce.security.TokenDTO;
import com.my.commerce.service.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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
     * @param postMemberReqDTO 회원 정보 DTO
     * @return String
     */
    @PostMapping("/signup")
    public BaseResponse<String> signupMember(@RequestBody PostMemberReqDTO postMemberReqDTO) throws BasicException{
        String signupMemberRes = authService.signupMember(postMemberReqDTO);

        return new BaseResponse<>(signupMemberRes);
    }

    @PostMapping("/login")
    public BaseResponse<TokenDTO> loginMember(@RequestBody PostLoginReqDTO postLoginReqDTO) throws BasicException{
        TokenDTO tokenDTO = authService.login(postLoginReqDTO);

        return new BaseResponse<>(tokenDTO);
    }

    /**
     * 메일 발송 API
     * @param postEmailReqDTO
     * @return String
     */
    @PostMapping("/email")
    private BaseResponse<String> emailCheck(@RequestBody PostEmailReqDTO postEmailReqDTO) throws BasicException{
        String result = authService.emailCheck(postEmailReqDTO);

        return new BaseResponse<>(result);
    }

    /**
     * 인증 번호 확인 API
     * @param postEmailCheckReqDTO
     * @return String
     */
    @PostMapping("/email/check")
    private BaseResponse<String> authCodeCheck(@RequestBody PostEmailCheckReqDTO postEmailCheckReqDTO) throws BasicException{
        String result = authService.authCodeCheck(postEmailCheckReqDTO);

        return new BaseResponse<>(result);
    }


    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) throws BasicException{
        String context= authService.logout(request);

        return new BaseResponse<>(context);
    }

}
