package com.example.barogo.member.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinMemberResponseDto {
    String username;
    String accessToken;

    @Builder
    public JoinMemberResponseDto(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
