package com.example.barogo.delivery.controller;

import com.example.barogo.ApiResponse;
import com.example.barogo.authorization.Authorization;
import com.example.barogo.delivery.service.dto.DeliveryDto;
import com.example.barogo.delivery.service.DeliveryService;
import com.example.barogo.member.domain.Member;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @ApiOperation(value = "배달 주소 수정")
    @PutMapping("/{deliveryId}")
    public ApiResponse<String> changeOrderAddress(@PathVariable long deliveryId, @RequestBody String orderAddress){
        String result = deliveryService.changeOrderAddress(deliveryId, orderAddress);
        return ApiResponse.success(result, "");
    }

    @ApiOperation(value = "사용자 배달 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "access token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "searchDate", value = "yyyy-MM-dd", required = true, dataType = "string", paramType = "query"),
    })
    @GetMapping("")
    public ApiResponse<List<DeliveryDto>> memberDeliveries(@Authorization Member member
            , @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate searchDate){
        List<DeliveryDto> result = deliveryService.memberDeliveries(member, searchDate);
        return ApiResponse.success(result, "");
    }




}
