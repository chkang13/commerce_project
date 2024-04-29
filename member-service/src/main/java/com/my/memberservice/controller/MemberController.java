package com.my.memberservice.controller;

import com.my.coreservice.global.common.BaseResponse;
import com.my.memberservice.dto.Member.PatchMemberReqDTO;
import com.my.memberservice.dto.Member.PatchPasswordReqDTO;
import com.my.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 유저 정보 수정 API
     *
     * @param patchMemberReqDTO 유저 정보
     * @return String
     * */
    @PatchMapping
    private BaseResponse<String> patchMember(Principal principal, @RequestBody PatchMemberReqDTO patchMemberReqDTO) {
        String patchMemberRes = memberService.patchMember(principal, patchMemberReqDTO);

        return new BaseResponse<>(patchMemberRes);
    }

    /**
     * 유저 비밀번호 수정 API
     *
     * @param patchPasswordReqDTO 비밀번호 정보
     * @return String
     * */
    @PatchMapping("/password")
    private BaseResponse<String> patchPassword(Principal principal, @RequestBody PatchPasswordReqDTO patchPasswordReqDTO) {
        String patchPasswordRes = memberService.patchPassword(principal, patchPasswordReqDTO);

        return new BaseResponse<>(patchPasswordRes);
    }



}
