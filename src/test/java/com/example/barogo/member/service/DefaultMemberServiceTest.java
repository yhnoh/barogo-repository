package com.example.barogo.member.service;

import com.example.barogo.member.domain.Member;
import com.example.barogo.exception.barogo.BarogoIllegalStateException;
import com.example.barogo.member.repository.MemberRepository;
import com.example.barogo.member.service.dto.JoinMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DefaultMemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    DefaultMemberService memberService;


    @Test
    public void 회원가입(){

        String username = "노영호";
        String password = "a1b2c3d4e5A!";


        Mockito.when(memberRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(memberRepository.save(Mockito.any())).thenReturn(Member.builder()
                .username(username)
                .password(password)
                .build());

        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

        JoinMember joinMemberDto = JoinMember.builder()
                .username(username)
                .password(password)
                .build();

        Member joinMember = memberService.join(joinMemberDto);
        Assertions.assertThat(joinMember.getUsername()).isEqualTo("노영호");
        Assertions.assertThat(joinMember.getPassword()).isEqualTo("a1b2c3d4e5A!");

    }

    @Test
    public void 회원가입아이디중복시예외(){

        String username = "노영호";
        String password = "a1b2c3d4e5A!";
        Member findMember = Member.builder()
                .username(username)
                .password(password)
                .build();

        Mockito.when(memberRepository.findByUsername(username)).thenReturn(Optional.ofNullable(findMember));
        JoinMember joinMemberDto = JoinMember.builder()
                .username(username)
                .password(password)
                .build();

        Assertions.assertThatThrownBy(() -> memberService.join(joinMemberDto))
                .isInstanceOf(BarogoIllegalStateException.class);

    }

    @Test
    public void 패스워드_대문자테스트(){
        String password = "aA";
        String pattern = ".*[A-Z].*";
        boolean matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "Aa";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "aa";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(false);

    }

    @Test
    public void 패스워드_소문자테스트(){
        String password = "aA";
        String pattern = ".*[a-z].*";
        boolean matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "aa";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "AA";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(false);
    }

    @Test
    public void 패스워드_숫자테스트(){
        String password = "a0";
        String pattern = ".*[0-9].*";
        boolean matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "00";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "aA";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(false);
    }

    @Test
    public void 패스워드_특수문자자테스트(){
       String password = "a|";
        String pattern = ".*[!@#$%^&*(),.?\":{}|<>].*";
        boolean matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "!#";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(true);

        password = "aA0";
        matches = password.matches(pattern);
        Assertions.assertThat(matches).isEqualTo(false);
    }

    @Test
    public void 회원가입성공(){
        Member member = Member.builder()
            .username("노영호")
            .password("a1b2c3d4e5A!")
            .build();

        Assertions.assertThat(member.getUsername()).isEqualTo("노영호");
        Assertions.assertThat(member.getPassword()).isEqualTo("a1b2c3d4e5A!");
    }


}
