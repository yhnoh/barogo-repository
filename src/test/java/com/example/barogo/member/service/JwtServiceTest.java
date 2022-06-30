package com.example.barogo.member.service;


import com.example.barogo.member.domain.Member;
import com.example.barogo.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    JwtService jwtService;

    @Test
    public void jwt생성(){

        Member member = Member.builder()
                .username("테스트")
                .password("테스트")
                .build();

        when(memberRepository.findByUsername("테스트")).thenReturn(Optional.ofNullable(member));

        String jwtToken = jwtService.createJwtToken("테스트");
        assertThat(jwtToken).isNotNull();

    }

    @Test
    public void jwt복호화(){
        //테스트 디버깅 추천
        Member member = Member.builder()
                .username("테스트")
                .password("테스트")
                .build();

        when(memberRepository.findByUsername("테스트")).thenReturn(Optional.ofNullable(member));

        String jwtToken = jwtService.createJwtToken("테스트");
        Optional<Member> decMember = jwtService.getMember(jwtToken);

    }
}
