package com.anstay.service;

import com.anstay.dto.CommentDTO;
import com.anstay.entity.Comment;
import com.anstay.enums.TargetType;
import com.anstay.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Thêm bình luận
    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setUserId(commentDTO.getUserId());
        comment.setTargetType(commentDTO.getTargetType()); // Dùng Enum
        comment.setTargetId(commentDTO.getTargetId());
        comment.setComment(commentDTO.getComment());

        Comment savedComment = commentRepository.save(comment);
        return new CommentDTO(
                savedComment.getId(),
                savedComment.getUserId(),
                savedComment.getTargetType(),
                savedComment.getTargetId(),
                savedComment.getComment(),
                savedComment.getCreatedAt()
        );
    }

    // Lấy danh sách bình luận theo targetType và targetId
    public List<CommentDTO> getComments(String targetType, Integer targetId) {
        try {
            TargetType targetTypeEnum = TargetType.valueOf(targetType.toUpperCase());
            List<Comment> comments = commentRepository.findByTargetTypeAndTargetId(targetTypeEnum, targetId);

            return comments.stream().map(comment -> new CommentDTO(
                    comment.getId(),
                    comment.getUserId(),
                    comment.getTargetType(),
                    comment.getTargetId(),
                    comment.getComment(),
                    comment.getCreatedAt()
            )).collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid targetType: " + targetType);
            return List.of();
        }
    }

    // Xóa bình luận theo ID
    public boolean deleteComment(Integer id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
