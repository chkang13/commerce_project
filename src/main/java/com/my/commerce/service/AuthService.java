package com.my.commerce.service;

import com.my.commerce.dto.PostMemberReqDTO;
import com.my.commerce.repository.MemberRepository;
import com.my.commerce.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;

    /**
     * 회원가입 API
     */
    @Transactional
    public String signupMember(PostMemberReqDTO postMemberReqDTO) {
        Member member = postMemberReqDTO.toEntity(postMemberReqDTO);
        memberRepository.save(member);

        return "회원가입 성공하였습니다.";
    }

}