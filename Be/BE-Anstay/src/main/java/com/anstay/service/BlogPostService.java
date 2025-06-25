package com.anstay.service;

import com.anstay.entity.BlogPost;
import com.anstay.enums.BlogStatus;
import com.anstay.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public BlogPost save(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    public List<BlogPost> findAll() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> findById(Long id) {
        return blogPostRepository.findById(id);
    }

    public Optional<BlogPost> findBySlug(String slug) {
        return blogPostRepository.findBySlug(slug);
    }

    public List<BlogPost> findByStatus(BlogStatus status) {
        return blogPostRepository.findByStatus(status);
    }

    public List<BlogPost> findByCreatedBy(Long createdBy) {
        return blogPostRepository.findByCreatedBy(createdBy);
    }

    public void deleteById(Long id) {
        blogPostRepository.deleteById(id);
    }
}
