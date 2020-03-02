package com.example.springstudy.study.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//생성자를 자동으로 만들어 주는 어노테이션.
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST" , "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}