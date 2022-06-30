package com.example.barogo.delivery.repository;

import com.example.barogo.delivery.domain.Delivery;
import com.example.barogo.member.domain.Member;
import com.example.barogo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;


    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 사용자배달리스트(){
        Member member = Member.builder()
                .username("테스트")
                .password("테스트비밀번호")
                .build();

        Member saveMember = memberRepository.save(member);
        LocalDate localDate = LocalDate.now().minusDays(3);
        LocalDateTime orderDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        Sort sort = Sort.by("orderDateTime").descending();

        deliveryRepository.findAllMemberDelivery(saveMember, orderDateTime, sort);
    }
}