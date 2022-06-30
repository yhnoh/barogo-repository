package com.example.barogo.member.repository;

import com.example.barogo.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원저장테스트(){
        Member member = Member.builder()
                .username("노영호")
                .password("a1b2c3d4e5A!")
                .build();

        Member saveMember = memberRepository.save(member);
        Assertions.assertThat(saveMember.getId()).isNotNull();
        Assertions.assertThat(saveMember.getUsername()).isEqualTo("노영호");
        Assertions.assertThat(saveMember.getPassword()).isEqualTo("a1b2c3d4e5A!");
    }

    @Test
    public void 유저명으로회원찾기(){
        Member member = Member.builder()
                .username("노영호")
                .password("a1b2c3d4e5A!")
                .build();

        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByUsername("노영호");
        Assertions.assertThat(findMember.isPresent()).isEqualTo(true);
    }

}