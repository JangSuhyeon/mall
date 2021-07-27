package com.shjang.portfolio.mall.config.auth;

import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    //조건에 맞는 메소드가 있는지 확인
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; //파라미터에 @LoginUser이 붙어있는지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType()); //파라미터 클래스 타입이 SessionUser.class 인지 
        return isLoginUserAnnotation && isUserClass; //위 두가지 조건이 부합하면 true 반환
    }

    //있으면 파라미터에 전달할 객체를 생성
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user"); //세션에서 객체를 가져온다
    }
}
