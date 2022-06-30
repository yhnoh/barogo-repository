package com.example.barogo.delivery.service;

import com.example.barogo.delivery.repository.DeliveryRepository;
import com.example.barogo.exception.barogo.BarogoIllegalStateException;
import com.example.barogo.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    DeliveryRepository deliveryRepository;
    @InjectMocks
    DeliveryService deliveryService;

    @Test
    public void 주소변경예외테스트(){
        //배달내역이 존재해야 변경 가능
        long deliveryId = 0L;
        String orderAddress = "테스트";
        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> deliveryService.changeOrderAddress(deliveryId, orderAddress))
                .isInstanceOf(BarogoIllegalStateException.class);

    }

    @Test
    public void 배달리스트예외테스트(){
        Member member = Mockito.mock(Member.class);
        LocalDate localDate = LocalDate.now().minusDays(4);
        //Mockito.when(deliveryRepository.findAllMemberDelivery(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

        Assertions.assertThatThrownBy(() -> deliveryService.memberDeliveries(member, localDate))
                .isInstanceOf(BarogoIllegalStateException.class);

    }

}