package com.example.barogo.delivery.domain;


import com.example.barogo.exception.barogo.BarogoSqlException;
import com.example.barogo.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryTest {

    @Mock
    Member member;

    Delivery delivery;
    @BeforeEach
    public void beforeEach(){
        member = Mockito.mock(Member.class);
        delivery = Delivery.builder()
                .member(member)
                .orderName("테스트 주문자")
                .orderAddress("테스트 주소")
                .build();
    }

    @Test
    public void 배달주문테스트(){

        assertThat(delivery.getOrderName()).isEqualTo("테스트 주문자");
        assertThat(delivery.getOrderAddress()).isEqualTo("테스트 주소");
        assertThat(delivery.getDeliveryStatus()).isEqualTo(Delivery.Status.ORDER);
        assertThat(delivery.getOrderDateTime()).isNotNull();
        assertThat(delivery.getDeliveryStartDateTime()).isNull();
        assertThat(delivery.getDeliveryCompleteDateTime()).isNull();

    }

    @Test
    public void 배달시작(){
        delivery.startDelivery();
        assertThat(delivery.getDeliveryStatus()).isEqualTo(Delivery.Status.PROGRESS);
        assertThat(delivery.getDeliveryStartDateTime()).isNotNull();

        //배달 완료가 되면 배달을 진행할 수 없다.
        delivery.completeDelivery();
        assertThatThrownBy(() -> delivery.startDelivery())
                .isInstanceOf(BarogoSqlException.class);
    }

    @Test
    public void 배달완료(){
        delivery.startDelivery();
        delivery.completeDelivery();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(Delivery.Status.COMPLETE);
        assertThat(delivery.getDeliveryCompleteDateTime()).isNotNull();

    }

    @Test
    public void 배달완료_예외(){

        //배달주문상태일때는 배달완료를 할 수 없다.
        assertThatThrownBy(() -> delivery.completeDelivery())
                .isInstanceOf(BarogoSqlException.class);

        delivery.startDelivery();
        delivery.completeDelivery();
        //배달완료 상태일때는 배달을 완료시킬 수 없다.
        assertThatThrownBy(() -> delivery.completeDelivery())
                .isInstanceOf(BarogoSqlException.class);

    }

    @Test
    public void 배달주소변경(){
        //배달 주문이 완료된 상태가 아니라면 주소를 변경할 수 있다.
        delivery.startDelivery();
        delivery.changeOrderAddress("테스트 주소2");

        assertThat(delivery.getOrderAddress()).isEqualTo("테스트 주소2");
    }
}