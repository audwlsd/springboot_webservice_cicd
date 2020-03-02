package com.example.springstudy.study.Controller;


import com.example.springstudy.study.config.auth.LoginUser;
import com.example.springstudy.study.config.auth.dto.SessionUser;
import com.example.springstudy.study.dto.PostsResponseDto;
import com.example.springstudy.study.service.PostsService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

// model 사용하려면 이거 추가해야함.
// model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장한다.
import org.springframework.ui.Model;

// The blank final field postsService may not have been initializedJava(33554513) 해결하기 위해 어노테이션 추가
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    // LoginUser 는 직접 만든 어노테이션, 이걸 통해서 세션 정보를 가져올 수 있음.
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        // 기존 코드
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null) {
            model.addAttribute("userName", user.getName());
        }

        // 앞의 경로는 src/main/resources/templates 
        // 뒤의 확장자는 .mustache 가 붙는다.
        return "index";
    }

    @GetMapping("/posts/save")
    public String pustsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        
        return "posts-update";
    }
}