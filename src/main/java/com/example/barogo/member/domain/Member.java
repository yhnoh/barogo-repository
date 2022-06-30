package com.example.barogo.member.domain;

import com.example.barogo.exception.barogo.BarogoSqlException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String username;
    private String password;

    @Builder
    public Member(String username, String password) {
        if(!StringUtils.hasText(username)){
            throw new BarogoSqlException("회원명을 입력해 주세요.");
        }

        if(!StringUtils.hasText(password)){
            throw new BarogoSqlException("패스워드를 입력해 주세요.");
        }

        this.username = username;
        this.password = password;
    }
}
