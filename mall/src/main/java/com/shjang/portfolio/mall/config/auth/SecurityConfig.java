package com.shjang.portfolio.mall.config.auth;

import com.shjang.portfolio.mall.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
            .authorizeRequests() //URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/","/login","/art/list","/art/detail/**","/css/**","/images/**","/js/**","/h2/**").permitAll()
                .antMatchers("/api/v1/**","/art/save").hasRole(Role.GUEST.name())
                .anyRequest().authenticated() //위에 선언된 url외에 다른 url접근은 인증된 유저만 허용
                .and()
            .oauth2Login()
                .loginPage("/login") //커스텀 login페이지로 이동
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .and()
            .oauth2Login() //OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                    .userService(customOAuth2UserService); //소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
    }
}
