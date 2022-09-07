package com.owoonan.owoonan.domain.comment.repository;


import com.owoonan.owoonan.domain.comment.domain.Comment;
import com.owoonan.owoonan.domain.comment.dto.CommentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
