package com.anstay.repository;

import com.anstay.entity.BlogPost;
import com.anstay.enums.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    // Tìm theo slug
    Optional<BlogPost> findBySlug(String slug);

    // Lấy danh sách bài theo status (enum)
    List<BlogPost> findByStatus(BlogStatus status);

    // Lấy bài theo người tạo
    List<BlogPost> findByCreatedBy(Long createdBy);

    // (Mở rộng) Lấy bài theo status và người tạo
    List<BlogPost> findByStatusAndCreatedBy(BlogStatus status, Long createdBy);
}
