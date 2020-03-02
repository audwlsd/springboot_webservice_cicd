package com.example.springstudy.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
SpringBootApplication : 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 자동으로 해줌.
                        이 위치부터 읽음. 프로젝트의 최상단에 위치
*/
// @EnableJpaAuditing 를 제거 , @WebMvcTest에서도 스캔하는데, WebMvcTest에서는 repository 를 못가져옴. 이를 분리해둬야함.
@SpringBootApplication
public class MainApplication    {
    
    // SpringApplication 이 제일 상단에 위치, 제일 처음 실행됨.
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}




