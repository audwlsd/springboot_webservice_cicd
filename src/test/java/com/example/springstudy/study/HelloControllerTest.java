package com.example.springstudy.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

// 이 두개 import 해줘야 jsonPath , is 를 쓸 수 있음.
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springstudy.study.Controller.HelloController;
import com.example.springstudy.study.config.auth.SecurityConfig;

// 스프링부트 테스트와 Junit 사이에 연결자 역할을 함.
@RunWith(SpringRunner.class)   
// 스프링테스트 어노테이션 중, Web 에 집중할 수 있는 것, @Controller, @ControllerAdvice 등을 사용 가능
// 얘는 CustomOAuth2UserService 를 스캔하지 않는다.
// WebSecurityConfigurerAdapter, WebMvcConfigurer , @COntrollerAdvice, @Controller 를 읽음.
// 즉, @Repository, @Service, @Component 는 스캔 대상이 아님
// SecurityConfig는 읽었지만, SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService 는 읽지 않아서 에러 발생
// 스캔 대상에서 SecurityCOnfig 를 제거한다.
@WebMvcTest(controllers = HelloController.class, 
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                        classes = SecurityConfig.class)
            }
)
public class HelloControllerTest {

    // 스프링이 관리하는 빈(Bean) 을 주입 받는다.
    @Autowired
    // 웹 API를 테스트할 때 사용한다. 스프링 MVC 테스트의 시작점.
    // HTTP GET, POST 등에 대한 API 테스트를 할 수 있음.
    private MockMvc mvc;

    @Test
    @WithMockUser(roles="USER") // 권한 추가 해줌 Spring security때문에
    public void hello가_리턴된다() throws Exception {
        String hello = "hello" ;

        // /hello 의 주소로 HTTP GET 요청
        // 체이닝이 가능해서, 여러 검증을 이어서 선언 및 수행이 가능
        mvc.perform(get("/hello"))
         // mvc perform의 결과를 검증, HTTP Header의 Status를 검증한다. 200, 404, 500 상태 , OK : 200
        .andExpect(status().isOk())
        // 응답 본문의 내용을 검증한다. Controller 에서 "hello"를 리턴함.
        .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles="USER") // 권한 추가 해줌 Spring security때문에
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;


        mvc.perform(get("/hello/dto")
        // param 보낼 때 값은 무조건 String 으로만 보내야함. 
        .param("name", name)
        .param("amount", String.valueOf(amount)))
        .andExpect(status().isOk())
        // json 응답값을 필드별로 검증하는 메소드(jsonPath) , $를 기준으로 필드명을 구분.
        .andExpect(jsonPath("$.name" , is(name)))
        .andExpect(jsonPath("$.amount", is(amount)));
    }
}