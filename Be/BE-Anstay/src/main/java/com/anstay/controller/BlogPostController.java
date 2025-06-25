package com.anstay.controller;

import com.anstay.entity.BlogPost;
import com.anstay.enums.BlogStatus;
import com.anstay.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/blog-posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    // Thêm mới
    @PostMapping
    public BlogPost create(@RequestBody BlogPost blogPost) {
        return blogPostService.save(blogPost);
    }

    // Cập nhật (PUT)
    @PutMapping("/{id}")
    public BlogPost update(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        blogPost.setId(id);
        return blogPostService.save(blogPost);
    }

    // Lấy tất cả (admin)
    @GetMapping
    public List<BlogPost> getAll() {
        return blogPostService.findAll();
    }

    // Lấy theo id
    @GetMapping("/{id}")
    public BlogPost getById(@PathVariable Long id) {
        return blogPostService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    // Lấy theo slug (dùng cho public FE)
    @GetMapping("/slug/{slug}")
    public BlogPost getBySlug(@PathVariable String slug) {
        return blogPostService.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    // Lấy danh sách theo status (PUBLISHED/DRAFT/...)
    @GetMapping("/status/{status}")
    public List<BlogPost> getByStatus(@PathVariable BlogStatus status) {
        return blogPostService.findByStatus(status);
    }

    // Lấy danh sách theo người tạo
    @GetMapping("/createdBy/{createdBy}")
    public List<BlogPost> getByCreatedBy(@PathVariable Long createdBy) {
        return blogPostService.findByCreatedBy(createdBy);
    }

    // Xoá
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogPostService.deleteById(id);
    }
}
