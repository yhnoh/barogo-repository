package com.example.barogo.member.service;

import com.example.barogo.member.domain.Member;
import com.example.barogo.member.service.dto.JoinMember;
import com.example.barogo.member.service.dto.LoginMember;

public interface MemberService {
    Member join(JoinMember joinMember);
    Member login(LoginMember loginMember);
}
