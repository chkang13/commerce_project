package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;

import com.my.commerce.dto.PostMemberReqDTO;
import com.my.commerce.security.PostLoginReqDTO;
import com.my.commerce.security.TokenDTO;
import com.my.commerce.service.AuthService;
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
     * @param postMemberReqDTO 회원 정보 DTO
     * @return String
     */
    @PostMapping("/signup")
    public BaseResponse<String> signupMember(@RequestBody PostMemberReqDTO postMemberReqDTO) {
        String signupMemberRes = authService.signupMember(postMemberReqDTO);
        log.info("signupMember 요청: " + signupMemberRes);

        return new BaseResponse<>(signupMemberRes);
    }

    @PostMapping("/login")
    public BaseResponse<TokenDTO> loginMember(@RequestBody PostLoginReqDTO postLoginReqDTO) {
        TokenDTO tokenDTO = authService.login(postLoginReqDTO);
        log.info("1");

        return new BaseResponse<>(tokenDTO);
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

}
