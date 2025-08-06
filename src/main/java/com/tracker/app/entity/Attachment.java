package com.tracker.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_id")
    private Long id;

    @NotNull(message = "Transaction record is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_record_id", nullable = false, unique = true)
    private TransRecord transRecord;

    @NotBlank(message = "File path is required")
    @Size(max = 500, message = "File path must not exceed 500 characters")
    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    // Constructors
    public Attachment() {
    }

    public Attachment(TransRecord transRecord, String filePath) {
        this.transRecord = transRecord;
        this.filePath = filePath;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Business methods
    public String getFileExtension() {
        if (filePath != null && filePath.contains(".")) {
            return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }

    public boolean isImage() {
        String extension = getFileExtension();
        return extension.matches("jpg|jpeg|png|gif|webp");
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransRecord getTransRecord() {
        return transRecord;
    }

    public void setTransRecord(TransRecord transRecord) {
        this.transRecord = transRecord;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "Attachment{"
                + "id=" + id
                + ", filePath='" + filePath + '\''
                + ", uploadedAt=" + uploadedAt
                + '}';
    }
}
