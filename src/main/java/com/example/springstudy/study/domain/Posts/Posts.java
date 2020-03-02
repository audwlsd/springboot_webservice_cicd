/*
게시글의 Entity 클래스.
DB와 맞닿은 핵심 클래스
*/

package com.example.springstudy.study.domain.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// lombok 의 어노테이션
/*
Getter : 클래스 내 getter 메소드 자동 생성
NoArgsConstructor : 기본 생성자 자동 추가 / public posts() {} 과 같은 효과
*/
@Getter  
@NoArgsConstructor
// JPA 의 어노테이션
/*
Entity : 기본적으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭함. 테이블과 매칭될 클래스
entity 클래스 에서는 setter 를 절대로 만들지 않는다. setter 를 무작정 생성하게 되면, 
인스턴스 값들이 언제 어디서 변해야 하는지 구분이 안됨.
*/
@Entity   
// Posts 클래스는 실제 DB와 매칭될 클래스 / 엔티티 클래스 라고도 불림.
// basetime entity 를 상속받아야만, posts 앤티티에서 자동으로 실행이 됨.
public class Posts extends BaseTimeEntity {

    // 테이블의 PKvlfem
    @Id
    // pk 생성 규칙 : auto_increment 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // column이라고 안붙여도, 모두 칼럼이 됨. 기본값 이외에 추가로 변경이 필요한 옵션이 있으면 사용하는 어노테이션
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 해당 클래스의 빌더 패턴 클래스를 생성 / 생성자 상단에 선언 시 생성자에 포함된 빌드만 빌더에 포함.
    // builder 를 사용하게 되면, 필드에 어떤 값을 채워야 하는지 명확하게 인지가 가능
    @Builder
    public Posts (String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}