package com.my.memberservice.controller;

import com.my.coreservice.global.common.BaseResponse;
import com.my.memberservice.dto.PatchMemberReqDTO;
import com.my.memberservice.dto.PatchPasswordReqDTO;
import com.my.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private BaseResponse<String> patchMember(@RequestHeader Long memberId , @RequestBody PatchMemberReqDTO patchMemberReqDTO) {
        String patchMemberRes = memberService.patchMember(memberId, patchMemberReqDTO);

        return new BaseResponse<>(patchMemberRes);
    }

    /**
     * 유저 비밀번호 수정 API
     *
     * @param patchPasswordReqDTO 비밀번호 정보
     * @return String
     * */
    @PatchMapping("/password")
    private BaseResponse<String> patchPassword(@RequestHeader Long memberId, @RequestBody PatchPasswordReqDTO patchPasswordReqDTO) {
        String patchPasswordRes = memberService.patchPassword(memberId, patchPasswordReqDTO);

        return new BaseResponse<>(patchPasswordRes);
    }



}
