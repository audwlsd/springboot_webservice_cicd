/**
 *  구글 로그인 이후 가져온 사용자 정보(email, name, picture) 를 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원
 */

package com.example.springstudy.study.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import com.example.springstudy.study.config.auth.dto.OAuthAttributes;
import com.example.springstudy.study.config.auth.dto.SessionUser;
import com.example.springstudy.study.domain.user.User;
import com.example.springstudy.study.domain.user.UserRepository;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserService is a raw type. References to generic type OAuth2UserService<R,U> should be parameterized 
        // 해결하려면 <OAuth2UserRequest, OAuth2User> 붙여줘야함.
        // 기존코드 : OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registraionId : 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        // userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값을 이야기함. (PK 같은 의미)
        // 네이버/카카오 등은 기본 코드 지원 x , 구글은 지원 O (코드 : sub)
                .getUserInfoEndpoint().getUserNameAttributeName();



        // OAuthAttributes : OAuth2UserService 를 통해 가져온 OAuth2User의 attribute를 담을 클래스. 네이버 로그인 등에서도 이 클래스를 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());




        User user = saveOrUpdate(attributes);
        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스 , 새로 만들어서 씀.
        httpSession.setAttribute("user", new SessionUser(user));




        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능 구현
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}