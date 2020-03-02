package com.example.springstudy.study.domain_posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import com.example.springstudy.study.domain.Posts.Posts;
import com.example.springstudy.study.domain.Posts.PostsRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // 단위테스트가 끝날 때마다 수행되는 메소드를 지정
    // 테스트간 데이터의 침범을 막기 위해 사용한다.
    // 여러 테스트가 동시에 수행되면 h2에 데이터가 그대로 남아 있어 테스트 실패 할 수 있음.
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";


        // save에 insert/update 쿼리를 실행한다. id가 있으면 update, 없으면 insert 쿼리 실행
        postsRepository.save(Posts.builder()
                                    .title(title)
                                    .content(content)
                                    .author("amj7782@gmail.com")
                                    .build());

                                    
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                            .title("title")
                            .content("content")
                            .author("author")
                            .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate = " + posts.getCreatedDate() +
                             ", modifiedDate = " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate().isAfter(now));
        assertThat(posts.getModifiedDate().isAfter(now));

    }
}

