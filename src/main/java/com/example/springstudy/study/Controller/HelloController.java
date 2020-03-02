package com.example.springstudy.study.Controller;

import com.example.springstudy.study.dto.HelloResponseDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줌.
// @ResponseBody 를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌.
@RestController
public class HelloController {

    // HTTP Method 인 Get 요청을 받을 수 있는 API 를 만듬.
    // 예전에는 @RequestMapping(Method = RequestMethod.GET )
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // RequestParm : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션.
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
