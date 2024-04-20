package com.my.commerce.service;

import com.my.commerce.dto.PostMemberReqDTO;
import com.my.commerce.repository.MemberRepository;
import com.my.commerce.domain.Member;
import com.my.commerce.security.PostLoginReqDTO;
import com.my.commerce.security.TokenDTO;
import com.my.commerce.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원가입 API
     */
    @Transactional
    public String signupMember(PostMemberReqDTO postMemberReqDTO) {
        Member member = postMemberReqDTO.toEntity(postMemberReqDTO);
        member.addMemberRole("USER");
        memberRepository.save(member);

        return "회원가입 성공하였습니다.";
    }

    @Transactional
    public TokenDTO login(PostLoginReqDTO postLoginReqDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(postLoginReqDTO.getEmail(), postLoginReqDTO.getPassword());
        log.info("2");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("3");
        TokenDTO tokenDTO = tokenProvider.createAccessToken(authentication);
        log.info("4");
        return tokenDTO;
    }

}