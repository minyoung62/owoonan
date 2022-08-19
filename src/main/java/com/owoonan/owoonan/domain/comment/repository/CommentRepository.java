package com.owoonan.owoonan.domain.comment.repository;


import com.owoonan.owoonan.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
