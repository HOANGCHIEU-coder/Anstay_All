package com.anstay.controller;

import com.anstay.dto.CommentDTO;
import com.anstay.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.addComment(commentDTO);
        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/{targetType}/{targetId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable String targetType, @PathVariable Integer targetId) {
        return ResponseEntity.ok(commentService.getComments(targetType, targetId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        boolean deleted = commentService.deleteComment(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
