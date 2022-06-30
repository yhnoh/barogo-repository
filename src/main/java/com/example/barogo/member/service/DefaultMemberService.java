package com.example.barogo.member.service;

import com.example.barogo.exception.barogo.BarogoAuthorizationException;
import com.example.barogo.member.domain.Member;
import com.example.barogo.exception.barogo.BarogoIllegalStateException;
import com.example.barogo.member.repository.MemberRepository;
import com.example.barogo.member.service.dto.JoinMember;
import com.example.barogo.member.service.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.util.Password;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * 회원가입
     */
    public Member join(JoinMember joinMember){

        //중복 테스트
        Optional<Member> findDuplicateMember = memberRepository.findByUsername(joinMember.getUsername());
        findDuplicateMember.ifPresent(member -> {
            throw new BarogoIllegalStateException("이미 가입된 아이디입니다.");
        });

        Member member = Member.builder()
                .username(joinMember.getUsername())
                .password(passwordEncoder.encode(joinMember.getPassword()))
                .build();

        return memberRepository.save(member);
    }


    /**
     * 로그인
     */
    public Member login(LoginMember loginMember){
        Optional<Member> findMember = memberRepository.findByUsername(loginMember.getUsername());
        findMember.orElseThrow(() -> new BarogoAuthorizationException("회원정보가 없습니다."));
        Member member = findMember.get();

        if(!passwordEncoder.matches(loginMember.getPassword(), member.getPassword())){
            throw new BarogoAuthorizationException("비밀번호를 정확히 입력해 주세요.");
        }
        return findMember.get();
    }

}
