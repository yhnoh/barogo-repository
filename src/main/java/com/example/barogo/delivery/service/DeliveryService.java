package com.example.barogo.delivery.service;

import com.example.barogo.delivery.domain.Delivery;
import com.example.barogo.delivery.repository.DeliveryRepository;
import com.example.barogo.delivery.service.dto.DeliveryDto;
import com.example.barogo.exception.barogo.BarogoIllegalStateException;
import com.example.barogo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    /**
     * 주소변경
     */
    public String changeOrderAddress(long deliveryId, String orderAddress){
        Optional<Delivery> findDelivery = deliveryRepository.findById(deliveryId);
        findDelivery.orElseThrow(() -> new BarogoIllegalStateException("배달 내역이 없습니다."));

        Delivery delivery = findDelivery.get();
        delivery.changeOrderAddress(orderAddress);

        return orderAddress;
    }


    public List<DeliveryDto> memberDeliveries(Member member, LocalDate findLocalDate){

        LocalDate minDate = LocalDate.now().minusDays(3);
        if(minDate.isAfter(findLocalDate)){
            throw new BarogoIllegalStateException("기간은 최대 3일만 조회가능합니다.");
        }


        LocalDateTime localDateTime = LocalDateTime.of(findLocalDate, LocalTime.MIN);

        Sort sort = Sort.by("orderDateTime").descending();
        List<Delivery> deliveries = deliveryRepository.findAllMemberDelivery(member, localDateTime, sort);

        List<DeliveryDto> deliveryDtos = deliveries.stream()
                .map(delivery -> DeliveryDto.builder()
                        .username(delivery.getMember().getUsername())
                        .orderName(delivery.getOrderName())
                        .orderAddress(delivery.getOrderAddress())
                        .deliveryStatus(delivery.getDeliveryStatus())
                        .orderDateTime(delivery.getOrderDateTime())
                        .deliveryStartDateTime(delivery.getDeliveryStartDateTime())
                        .deliveryCompleteDateTime(delivery.getDeliveryCompleteDateTime())
                        .build())
                .collect(Collectors.toList());
        return deliveryDtos;
    }

}
