package com.example.barogo.member.controller;

import com.example.barogo.ApiResponse;
import com.example.barogo.member.service.JwtMemberService;
import com.example.barogo.member.service.dto.JoinMemberDto;
import com.example.barogo.member.service.dto.JoinMemberResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final JwtMemberService jwtMemberService;
    @ApiOperation(value = "회원가입")
    @PostMapping("")
    public ApiResponse<JoinMemberResponseDto> join(@RequestBody JoinMemberDto joinMemberDto){

        JoinMemberResponseDto result = jwtMemberService.join(joinMemberDto);
        return ApiResponse.success(result, "");
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResponse<JoinMemberResponseDto> login(@RequestBody JoinMemberDto joinMemberDto){
        JoinMemberResponseDto result = jwtMemberService.login(joinMemberDto);
        return ApiResponse.success(result, "");
    }

}
