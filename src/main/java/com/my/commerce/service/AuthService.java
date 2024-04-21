package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.dto.PostEmailCheckReqDTO;
import com.my.commerce.dto.PostEmailReqDTO;
import com.my.commerce.dto.PostMemberReqDTO;
import com.my.commerce.repository.MemberRepository;
import com.my.commerce.domain.Member;
import com.my.commerce.security.PostLoginReqDTO;
import com.my.commerce.security.TokenDTO;
import com.my.commerce.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.my.commerce.common.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final JavaMailSender javaMailSender;
    private final RedisUtilService redisUtilService;
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

    /**
     * 검증을 위한 이메일 발송 API
     */
    @Transactional
    public String emailCheck(PostEmailReqDTO postEmailReqDTO) {
        // 이메일 존재 여부 확인
        if (memberRepository.existsByEmail(postEmailReqDTO.getEmail())) {
            throw new BaseException(EXIST_EMAIL_ERROR);
        }

        // 이메일 생성 및 발송
        SimpleMailMessage message = new SimpleMailMessage();
        String authCode = getAuthCode();
        message.setTo(postEmailReqDTO.getEmail());
        message.setSubject("커머스 서비스 이메일 인증코드");
        message.setText("인증번호: " + authCode);
        javaMailSender.send(message);

        // Redis에 인증번호 저장 (3분)
        redisUtilService.setData(postEmailReqDTO.getEmail(), authCode, 180000);

        return "인증 메일이 발송되었습니다.";
    }

    /**
     * 인증코드 난수 발생 함수
     */
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;
        while (buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    /**
     * 이메일 인증 코드 확인
     */
    @Transactional
    public String authCodeCheck(PostEmailCheckReqDTO postEmailCheckReqDTO) {
        if (redisUtilService.existData(postEmailCheckReqDTO.getEmail())) {
            if (!redisUtilService.getData(postEmailCheckReqDTO.getEmail()).equals(postEmailCheckReqDTO.getAuthCode())) {
                throw new BaseException(INVALID_AUTH_CODE);
            } else {
                return "이메일 인증 성공했습니다.";
            }
        } else {
            throw new BaseException(EXPIRE_AUTH_CODE);
        }
    }

}