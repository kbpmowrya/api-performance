package com.pal.poc.api.perf.completable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    // You can add a method to set timestamps, for example:
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}

