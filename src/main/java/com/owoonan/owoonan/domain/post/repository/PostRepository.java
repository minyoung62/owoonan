package com.owoonan.owoonan.domain.post.repository;

import com.owoonan.owoonan.domain.post.domain.Post;
import com.owoonan.owoonan.domain.post.dto.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
