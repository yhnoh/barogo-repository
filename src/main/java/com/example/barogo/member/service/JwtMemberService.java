package com.example.barogo.member.service;

import com.example.barogo.member.domain.Member;
import com.example.barogo.member.service.dto.JoinMember;
import com.example.barogo.member.service.dto.JoinMemberDto;
import com.example.barogo.member.service.dto.JoinMemberResponseDto;
import com.example.barogo.member.service.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtMemberService {
    private final MemberService memberService;
    private final JwtService jwtService;

    @Transactional
    public JoinMemberResponseDto join(JoinMemberDto joinMemberDto){

        JoinMember joinMember = JoinMember.builder()
                .username(joinMemberDto.getUsername())
                .password(joinMemberDto.getPassword())
                .build();

        Member member = memberService.join(joinMember);
        String accessToken = jwtService.createJwtToken(member.getUsername());

        return JoinMemberResponseDto.builder()
                .username(joinMemberDto.getUsername())
                .accessToken(accessToken)
                .build();

    }


    @Transactional
    public JoinMemberResponseDto login(JoinMemberDto joinMemberDto){
        LoginMember loginMember = LoginMember.builder()
                .username(joinMemberDto.getUsername())
                .password(joinMemberDto.getPassword())
                .build();

        Member member = memberService.login(loginMember);
        String accessToken = jwtService.createJwtToken(member.getUsername());

        return JoinMemberResponseDto.builder()
            .username(joinMemberDto.getUsername())
            .accessToken(accessToken)
            .build();

    }

}
