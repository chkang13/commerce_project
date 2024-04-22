package com.my.commerce.service;


import com.my.commerce.common.BaseException;
import com.my.commerce.domain.Member;
import com.my.commerce.dto.PatchMemberReqDTO;
import com.my.commerce.dto.PatchPasswordReqDTO;
import com.my.commerce.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.Optional;

import static com.my.commerce.common.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 수정 API
     * */
    public String patchMember(Principal principal, PatchMemberReqDTO patchMemberReqDTO) {
        Optional<Member> member = memberRepository.findById(Long.valueOf(principal.getName()));

        // 유저 존재 여부 확인
        if (member.isPresent()) {
            // 정보 수정
            member.get().update(patchMemberReqDTO.getPhone(), patchMemberReqDTO.getAddress());
        } else {
            throw new BaseException(MEMBER_INVALID_USER);
        }
        return "정보 수정 완료되었습니다.";
    }

    /**
     * 유저 비밀번호 수정 API
     * */
    public String patchPassword(Principal principal, PatchPasswordReqDTO patchPasswordReqDTO) {
        Optional<Member> member = memberRepository.findById(Long.valueOf(principal.getName()));

        // 유저 존재 여부 확인
        if (member.isPresent()) {
            // 비밀번호 일치 여부 확인
            if (!passwordEncoder.matches(patchPasswordReqDTO.getCurrentPassword(), member.get().getPassword())) {
                throw new BaseException(MEMBER_PASSWORD_DISCORD);
            }

            // 정보 수정
            member.get().updatePassword(passwordEncoder.encode(patchPasswordReqDTO.getChangedPassword()));
        } else {
            throw new BaseException(MEMBER_INVALID_USER);
        }
        return "비밀번호 수정 완료되었습니다.";
    }

}
