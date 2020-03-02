package com.example.springstudy.study.config.auth;


import lombok.RequiredArgsConstructor;

import com.example.springstudy.study.domain.user.Role;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
// Spring security 설정들을 활성화 시켜줌.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        // 아래 2줄 : h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .csrf().disable()
                .headers().frameOptions().disable()
                    .and()
                    // url 별 권한 관리를 설정하는 옵션의 시작점. 선언해주어야, antMatchers 옵션 사용 가능
                        .authorizeRequests()
                        // 권한 관리 대상을 지정하는 옵션 , URL, HTTP 메소드별로 관리가 가능
                        // 전체 열람 권한 주는 것 : permitAll()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        // anyRequest : 설정된 값들 이외 URL 들을 나타냄.
                        // authenticate 를 추가하여, 나머지 URL 들은 인증된 사용자들에게만 허용됨. 로그인한 사용자들을 이야기하는 것.
                        .anyRequest().authenticated()
                    .and()
                        // 로그아웃 기능에 대한 설정의 진입점. 로그아웃 성공시 /로 이동
                        .logout()
                            .logoutSuccessUrl("/")
                    .and()
                    // OAuth 2 로그인 기능에 대한 설정의 진입점.
                        .oauth2Login()
                        // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                            .userInfoEndpoint()
                            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록함.
                            // 리소스 서버(구글 등)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
                                .userService(customOAuth2UserService);
    }
}