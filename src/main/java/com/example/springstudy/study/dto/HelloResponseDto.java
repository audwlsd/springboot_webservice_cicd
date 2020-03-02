package com.example.springstudy.study.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Get 메소드를 자동으로 만들어줌
@Getter
// final 있는 필드의 생성자를 생성해줌.
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}