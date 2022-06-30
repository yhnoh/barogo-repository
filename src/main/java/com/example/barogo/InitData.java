package com.example.barogo;

import com.example.barogo.delivery.domain.Delivery;
import com.example.barogo.delivery.repository.DeliveryRepository;
import com.example.barogo.delivery.service.DeliveryService;
import com.example.barogo.member.domain.Member;
import com.example.barogo.member.service.MemberService;
import com.example.barogo.member.service.dto.JoinMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberService memberService;
    private final DeliveryRepository deliveryRepository;

    @PostConstruct
    @Transactional
    public void init(){
        JoinMember joinMember1 = JoinMember.builder().username("배달원1").password("1a2b3c4d5e6rAA").build();
        JoinMember joinMember2 = JoinMember.builder().username("배달원2").password("1a2b3c4d5e6rAAD").build();
        Member saveMember1 = memberService.join(joinMember1);
        Member saveMember2 = memberService.join(joinMember2);
        List<Delivery> deliveries = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                deliveries.add(Delivery.builder().orderName("주문자" + i).member(saveMember1).orderAddress("주문자주소" + i).build());
            }else{
                deliveries.add(Delivery.builder().orderName("주문자" + i).member(saveMember2).orderAddress("주문자주소" + i).build());
            }
        }

        deliveryRepository.saveAll(deliveries);

    }
}

