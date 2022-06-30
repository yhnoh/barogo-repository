package com.example.barogo.delivery.repository;

import com.example.barogo.delivery.domain.Delivery;
import com.example.barogo.member.domain.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @EntityGraph(attributePaths = "member", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select d from Delivery d where d.member = :member and d.orderDateTime > :orderDateTime")
    List<Delivery> findAllMemberDelivery(@Param("member") Member member, @Param("orderDateTime") LocalDateTime orderDateTime, Sort sort);
}
