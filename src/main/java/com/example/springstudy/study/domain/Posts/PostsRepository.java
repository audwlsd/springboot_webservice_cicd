package com.example.springstudy.study.domain.Posts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

// 데이터 조회 프레임워크 : querydsl , jooq, mybatis 가 있는데 qeurydsl 을 많이사용하고 있음. 레퍼런스도 많음


// JPA 에선 레포지토리라고 부르는데, 이게 DB Layer 접근자 이다.
// 보통, JpaRepository<Entity 클래스, PK 타입> 을 상속하면 기본 CRUD 메소드가 자동으로 생성된다.
// Entity Class 와 Repository 는 하나의 도메인 별로 함께 관리해야함.

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();


}