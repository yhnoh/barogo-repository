package com.example.barogo.member.service;

import com.example.barogo.member.domain.Member;
import com.example.barogo.exception.barogo.BarogoJwtException;
import com.example.barogo.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String ENCRYPT_KEY = "barogo";
    private static final String CLAIM_KEY = "username";
    private static final long EXPIRE_TIME = 24 * 60 * 1000;
    private final MemberRepository memberRepository;

    public String createJwtToken(String username) {

		Optional<Member> member = memberRepository.findByUsername(username);
		member.orElseThrow(() -> new BarogoJwtException("회원정보가 없어 JWT 생성 불가"));

		long curTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setHeaderParam("typ", "JWT")
                .setExpiration(new Date(curTime + EXPIRE_TIME))
                .setIssuedAt(new Date(curTime))
                .claim(CLAIM_KEY, username)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

	private byte[] generateKey(){
		byte[] key = null;
		try {
			//ENCRYPT_STRING =  pretty
			key = ENCRYPT_KEY.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new BarogoJwtException("JWT key encoding failed");
		}
		return key;
	}

	//JWT 복호화
	public Optional<Member> getMember(String jwt) {
		//결과값 = Claims
		Jws<Claims> claims = null;
		try {
			//비밀키를 이용해서 복호화 하는 작업
			//jwt가 유효한지, 위변조 되지 않았는지 판단한다.
			//이 비밀키는 서버에만 존재해야되고, 유출되어서는 안된다.
			claims = Jwts.parser()
						 .setSigningKey(this.generateKey())
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new BarogoJwtException("JWT decoding failed");
		}

		//반환 타입은 LinkedHashMap 이다. 이를 User 타입으로 변환하기 위해 ObjectMapper 사용
		String username = claims.getBody().get(CLAIM_KEY).toString();

		return memberRepository.findByUsername(username);
	}
}

