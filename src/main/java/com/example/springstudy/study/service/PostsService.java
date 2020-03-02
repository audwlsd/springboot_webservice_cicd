package com.example.springstudy.study.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import com.example.springstudy.study.domain.Posts.Posts;
import com.example.springstudy.study.domain.Posts.PostsRepository;
import com.example.springstudy.study.dto.PostsListResponseDto;
import com.example.springstudy.study.dto.PostsResponseDto;
import com.example.springstudy.study.dto.PostsSaveRequestDto;
import com.example.springstudy.study.dto.PostsUpdateRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    // readonly true 옵션은 조회 속도가 개선되기 때문에 등록,수정,삭제 기능이 없는 메소드에서 사용해야 됨.
    /**
     * .map(PostsListResponseDto::new) 는 .map ( posts -> new
     * PostsListResponseDto(posts)) 와 같아. 즉, postsRepository 에서 넘어온 Posts의 Stream 을
     * map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

                // JPA Repository 에서 이미 delete 메소드를 지원하고 있어서 이를 그대로 씀.
                // entity 가 그대로 파라미터로 와도되고, deleteById 를 이용하면 id로 삭제가 가능.
        postsRepository.delete(posts);
    }
}