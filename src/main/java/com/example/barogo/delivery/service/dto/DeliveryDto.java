package com.example.barogo.delivery.service.dto;

import com.example.barogo.delivery.domain.Delivery;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class DeliveryDto {
    String username;
    public String orderName;
    private String orderAddress;
    public Delivery.Status deliveryStatus;
    public LocalDateTime orderDateTime;
    public LocalDateTime deliveryStartDateTime;
    public LocalDateTime deliveryCompleteDateTime;

    @Builder
    public DeliveryDto(String username, String orderName, String orderAddress, Delivery.Status deliveryStatus, LocalDateTime orderDateTime, LocalDateTime deliveryStartDateTime, LocalDateTime deliveryCompleteDateTime) {
        this.username = username;
        this.orderName = orderName;
        this.orderAddress = orderAddress;
        this.deliveryStatus = deliveryStatus;
        this.orderDateTime = orderDateTime;
        this.deliveryStartDateTime = deliveryStartDateTime;
        this.deliveryCompleteDateTime = deliveryCompleteDateTime;
    }
}
