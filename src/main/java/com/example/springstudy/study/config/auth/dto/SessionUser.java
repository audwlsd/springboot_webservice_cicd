package com.example.springstudy.study.config.auth.dto;

import lombok.Getter;

import java.io.Serializable;

import com.example.springstudy.study.domain.user.User;


// SessionUser 에는 인증된 사용자 정보만 필요함. 그 외 필요 정보는 없으니, nave/email/picture 만 필드로 선언함.
@Getter
// The serialiable class SessionUser does not delcare a static final serialVersionUID filed of type long Warning 안뜨게 하기
@SuppressWarnings("serial")
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}