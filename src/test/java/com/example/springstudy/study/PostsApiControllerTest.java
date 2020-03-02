package com.example.springstudy.study;

import com.example.springstudy.study.domain.Posts.Posts;
import com.example.springstudy.study.domain.Posts.PostsRepository;
import com.example.springstudy.study.dto.PostsSaveRequestDto;
import com.example.springstudy.study.dto.PostsUpdateRequestDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

// import org.springframework.boot.test.web.client.TestRestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    // Spring security 적용 전
    // @Autowired
    // private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;
    

    /**
     * WithMockUser 가 MockMvc에서만 작동하기 때문에, @SpringBootTest에서 MockMvc 를 사용하는 방법은 다음과 같다.
     */
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    // 매번 테스트가 시작되기전에 MockMvc 인스턴스를 생성한다.
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")  // 인증된 모의 사용자를 만들어서 roles 에 권한을 추가할 수 있음.
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)                                                    
                                                            .content(content)                                                    
                                                            .author("authors")                                                    
                                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        // 이거는 SpringSecurity 적용하기 전 , 즉 권한 설정과 관계 없이 post 요청하는 코드
        // ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto, Long.class);

        // 이거는 SpringSecurity 적용 후, 권한 설정과 관련한 코드
        // mvc preform : MockMvc 를 통해 API를 테스트 한다. 본문(Body)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환.
       //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        // SpringSecurity 적용 전
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // SpringSecurity 적용 후
        List<Posts> allPosts = postsRepository.findAll();
        assertThat(allPosts.get(0).getTitle()).isEqualTo(title);
        assertThat(allPosts.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles="USER")  // 인증된 모의 사용자를 만들어서 roles 에 권한을 추가할 수 있음.
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        // HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        
        //when
        // SpringSecurity 적용 전
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // SpringSecurity 적용 후
         mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        // SpringSecurity 적용 전
         // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // SpringSecurity 적용 후
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

        
    }
}