package com.example.barogo.authorization;


import com.example.barogo.exception.barogo.BarogoAuthorizationException;
import com.example.barogo.member.domain.Member;
import com.example.barogo.member.repository.MemberRepository;
import com.example.barogo.member.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthorizationArgumentResolver implements HandlerMethodArgumentResolver {


    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Authorization.class);
        boolean hasParameterType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasParameterAnnotation && hasParameterType;
    }



    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String jwt = request.getHeader("authorization");
        Optional<Member> findMember = Optional.empty();

        if(StringUtils.hasText(jwt)){
            findMember = jwtService.getMember(jwt);
        }

        if(checkRequired(parameter)){
            if(!findMember.isPresent()){
                throw new BarogoAuthorizationException("접근권한이 없습니다.");
            }
        }

        return findMember.orElse(null);
    }



    protected boolean checkRequired(MethodParameter parameter) {
        Authorization authorization = parameter.getParameterAnnotation(Authorization.class);
        return authorization != null && authorization.required() && !parameter.isOptional();
    }
}
