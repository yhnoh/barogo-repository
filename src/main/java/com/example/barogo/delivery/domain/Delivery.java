package com.example.barogo.delivery.domain;

import com.example.barogo.exception.barogo.BarogoSqlException;
import com.example.barogo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    public Member member;

    public String orderName;
    private String orderAddress;

    @Enumerated(EnumType.STRING)
    public Status deliveryStatus;

    public LocalDateTime orderDateTime;
    public LocalDateTime deliveryStartDateTime;
    public LocalDateTime deliveryCompleteDateTime;

    public enum Status {
        ORDER("배달주문"),
        PROGRESS("배달중"),
        COMPLETE("배달완료")
        ,;

        private String name;

        Status(String name) {
            this.name = name;
        }
    }

    @Builder
    public Delivery(Member member, String orderName, String orderAddress){
        if(member == null || !StringUtils.hasText(orderName) || !StringUtils.hasText(orderAddress)){
            throw new BarogoSqlException("배달을 위한 필수 값들이 없습니다.");
        }

        this.member = member;
        this.orderName = orderName;
        this.orderAddress = orderAddress;
        deliveryStatus = Status.ORDER;
        orderDateTime = LocalDateTime.now();
    }

    public void startDelivery(){
        if(deliveryStatus != Status.ORDER){
            throw new BarogoSqlException("배달 주문이 들어온 상태가 아니면 배달을 진행할 수 없습니다.");
        }

        deliveryStatus = Status.PROGRESS;
        deliveryStartDateTime = LocalDateTime.now();
    }


    /**
     * 이미 배달 완료 이면 배달완료 안됨
     */
    public void completeDelivery(){
        if(deliveryStatus != Status.PROGRESS){
            throw new BarogoSqlException("배달 진행중의 상태가 아니면 배달을 완료할 수 없습니다.");
        }

        deliveryStatus = Status.COMPLETE;
        deliveryCompleteDateTime = LocalDateTime.now();
    }

    /**
     * 배달이 완료된 경우에는 주소를 변경할 수 없다.
     */
    public void changeOrderAddress(String orderAddress){
        if(deliveryStatus == Status.COMPLETE){
            throw new BarogoSqlException("배달이 완료 상태이면 주소를 변경할 수 없습니다.");
        }

        if(!StringUtils.hasText(orderAddress)){
            throw new BarogoSqlException("배달 주소를 입력해 주세요.");
        }

        this.orderAddress = orderAddress;

    }
}
