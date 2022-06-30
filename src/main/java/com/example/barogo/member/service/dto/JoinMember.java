package com.example.barogo.member.service.dto;

import com.example.barogo.exception.barogo.BarogoIllegalArgumentException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
public class JoinMember {
    private String username;
    private String password;

    @Builder
    public JoinMember(String username, String password) {
        validUsername(username);
        validPassword(password);

        this.username = username;
        this.password = password;
    }

    public void validUsername(String username){
        if(!StringUtils.hasText(username)){
            throw new BarogoIllegalArgumentException("회원명을 입력해 주세요.");
        }

        if(username.indexOf(" ") > -1){
            throw new BarogoIllegalArgumentException("회원명에 공백을 삽입할 수 없습니다.");
        }
    }

    /**
     * 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중
     * 3종류 이상으로 12자리 이상의 문자열로 생성
     */
    public void validPassword(String password){
        if(!StringUtils.hasText(password)){
            throw new BarogoIllegalArgumentException("패스워드를 입력해 주세요.");
        }

        if(password.indexOf(" ") > -1){
            throw new BarogoIllegalArgumentException("패스워드에 공백을 삽입할 수 없습니다.");
        }

        if(password.length() < 12){
            throw new BarogoIllegalArgumentException("패스워드는 12자리 이상을 입력해야합니다.");
        }

        //대문자 체크
        int count = 0;
        if(password.matches(".*[A-Z].*")){
            count++;
        }
        //소문자 체크
        if(password.matches(".*[a-z].*")){
            count++;
        }
        //숫자 체크
        if(password.matches(".*[0-9].*")){
            count++;
        }
        //특수문자 체크
        if(password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")){
            count++;
        }

        if(count < 3){
            throw new BarogoIllegalArgumentException("비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상 조합되어야 합니다.");
        }
    }


}
