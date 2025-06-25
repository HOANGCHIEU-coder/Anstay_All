package com.anstay.dto;

import com.anstay.enums.BlogStatus;

public class BlogPostDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String thumbnail;
    private BlogStatus status;    // Enum BlogStatus
    private Long createdBy;
    private String createdAt;     // ISO 8601 (String hoặc LocalDateTime tuỳ cách trả ra)
    private String updatedAt;

    public BlogPostDTO() {}

    public BlogPostDTO(Long id, String title, String slug, String content, String summary,
                       String thumbnail, BlogStatus status, Long createdBy,
                       String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public BlogStatus getStatus() { return status; }
    public void setStatus(BlogStatus status) { this.status = status; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
