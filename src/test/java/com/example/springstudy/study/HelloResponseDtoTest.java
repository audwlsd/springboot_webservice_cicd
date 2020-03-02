package com.example.springstudy.study;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.springstudy.study.dto.HelloResponseDto;

public class HelloResponseDtoTest {

    @Test
    public void 롬북_기능_테스트() {

        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        /* 
        assertj : 테스트 검증 라이브러리 
        검증하고 싶은 대상을 메소드 인자로 받을 수 있고, 메소드 체이닝이 지원되어 이어서 사용 가능
        isEqualTo : 동등 비교 메소드
        */
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}