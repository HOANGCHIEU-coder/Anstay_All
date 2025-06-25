package com.anstay.repository;

import com.anstay.entity.Comment;
import com.anstay.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTargetTypeAndTargetId(TargetType targetType, Integer targetId);
}