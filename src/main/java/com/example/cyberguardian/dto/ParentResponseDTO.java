package com.example.cyberguardian.dto;

import java.time.LocalDateTime;

public class ParentResponseDTO {

    private Long parentId;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public ParentResponseDTO(
            Long parentId,
            String name,
            String email,
            LocalDateTime createdAt) {

        this.parentId = parentId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}