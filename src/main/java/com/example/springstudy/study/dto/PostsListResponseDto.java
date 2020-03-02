package com.example.springstudy.study.dto;

import lombok.Getter;

import java.time.LocalDateTime;

import com.example.springstudy.study.domain.Posts.Posts;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}