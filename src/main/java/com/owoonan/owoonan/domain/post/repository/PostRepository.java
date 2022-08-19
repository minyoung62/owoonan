package com.owoonan.owoonan.domain.post.repository;

import com.owoonan.owoonan.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
