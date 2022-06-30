package com.example.barogo.member.domain;

import com.example.barogo.exception.barogo.BarogoSqlException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    public void Member삽입(){

        Member member = Member.builder()
                .username("노영호")
                .password("1234")
                .build();

        Assertions.assertThat(member.getUsername()).isEqualTo("노영호");
        Assertions.assertThat(member.getPassword()).isEqualTo("1234");

    }

    @Test
    public void Member삽입시_username_password공백삽입불가(){
        Assertions.assertThatThrownBy(() -> {
            Member.builder()
                    .username("")
                    .password("1234")
                    .build();

        }).isInstanceOf(BarogoSqlException.class);

        Assertions.assertThatThrownBy(() -> {
            Member.builder()
                    .username("노영호")
                    .password("")
                    .build();

        }).isInstanceOf(BarogoSqlException.class);
    }



}
