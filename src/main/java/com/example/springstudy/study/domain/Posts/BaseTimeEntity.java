/**
 * 모든 Entity 의 상위 클래스가 되어, createdDate, modifiedDate 를 자동으로 관리하는 역할
 */
package com.example.springstudy.study.domain.Posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// JPA Entity 클래스가 BaseTimeEntity를 상속할 때, 필드들 ( createdDate, modifiedDate ) 도 칼럼으로 인식.
@MappedSuperclass
// BaseTimeEntity 클래스에 Auditing 기능을 포함시킴.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // 생성 시간
    @CreatedDate
    private LocalDateTime createdDate;

    // 변경 시간
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}