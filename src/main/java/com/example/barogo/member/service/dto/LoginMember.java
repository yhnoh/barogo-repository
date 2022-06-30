package com.example.barogo.member.service.dto;

import com.example.barogo.exception.barogo.BarogoIllegalArgumentException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
public class LoginMember {
    private String username;
    private String password;


    @Builder
    public LoginMember(String username, String password) {
        validInputLogin(username, password);
        this.username = username;
        this.password = password;
    }

    public void validInputLogin(String username, String password){
        if(!StringUtils.hasText(username)){
            throw new BarogoIllegalArgumentException("회원명을 입력해 주세요.");
        }

        if(username.indexOf(" ") > -1){
            throw new BarogoIllegalArgumentException("회원명에 공백을 삽입할 수 없습니다.");
        }

        if(!StringUtils.hasText(password)){
            throw new BarogoIllegalArgumentException("패스워드를 입력해 주세요.");
        }

        if(password.indexOf(" ") > -1){
            throw new BarogoIllegalArgumentException("패스워드에 공백을 삽입할 수 없습니다.");
        }
    }
}
