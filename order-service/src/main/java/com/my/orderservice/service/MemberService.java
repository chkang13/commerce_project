package com.my.orderservice.service;


import com.my.coreservice.global.common.BaseException;
import com.my.orderservice.domain.Member;
import com.my.orderservice.dto.Member.PatchMemberReqDTO;
import com.my.orderservice.dto.Member.PatchPasswordReqDTO;
import com.my.orderservice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.my.coreservice.global.common.BaseResponseStatus.MEMBER_INVALID_USER;
import static com.my.coreservice.global.common.BaseResponseStatus.MEMBER_PASSWORD_DISCORD;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 수정 API
     * */
    @Transactional
    public String patchMember(Principal principal, PatchMemberReqDTO patchMemberReqDTO) {
        Member member = memberRepository.findById(Long.parseLong(principal.getName())).orElseThrow(() -> new BaseException(MEMBER_INVALID_USER));
        // 정보 수정
        member.update(patchMemberReqDTO.getPhone(), patchMemberReqDTO.getAddress());

        return "정보 수정 완료되었습니다.";
    }

    /**
     * 유저 비밀번호 수정 API
     * */
    @Transactional
    public String patchPassword(Principal principal, PatchPasswordReqDTO patchPasswordReqDTO) {
        Member member = memberRepository.findById(Long.parseLong(principal.getName())).orElseThrow(() -> new BaseException(MEMBER_INVALID_USER));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(patchPasswordReqDTO.getCurrentPassword(), member.getPassword())) {
            throw new BaseException(MEMBER_PASSWORD_DISCORD);
        }

        // 정보 수정
        member.updatePassword(passwordEncoder.encode(patchPasswordReqDTO.getChangedPassword()));

        return "비밀번호 수정 완료되었습니다.";
    }

}
