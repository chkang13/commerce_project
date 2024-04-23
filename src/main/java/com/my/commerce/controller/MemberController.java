package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;
import com.my.commerce.common.BasicException;
import com.my.commerce.dto.PatchMemberReqDTO;
import com.my.commerce.dto.PatchMemberReqDTO;
import com.my.commerce.dto.PatchPasswordReqDTO;
import com.my.commerce.dto.PostEmailCheckReqDTO;
import com.my.commerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 유저 정보 수정 API
     * @param patchMemberReqDTO
     * @return String
     * */
    @PatchMapping
    private BaseResponse<String> patchMember(Principal principal, @RequestBody PatchMemberReqDTO patchMemberReqDTO) throws BasicException {
        String patchMemberRes = memberService.patchMember(principal, patchMemberReqDTO);

        return new BaseResponse<>(patchMemberRes);
    }

    /**
     * 유저 비밀번호 수정 API
     * @param patchPasswordReqDTO
     * @return String
     * */
    @PatchMapping("/password")
    private BaseResponse<String> patchPassword(Principal principal, @RequestBody PatchPasswordReqDTO patchPasswordReqDTO) throws BasicException {
        String patchPasswordRes = memberService.patchPassword(principal, patchPasswordReqDTO);

        return new BaseResponse<>(patchPasswordRes);
    }



}
