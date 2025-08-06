package com.tracker.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_id")
    private Long id;

    @NotNull(message = "Notification type is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_type_id", nullable = false)
    private NotificationType notificationType;

    @Size(max = 50, message = "Entity type must not exceed 50 characters")
    @Column(name = "entity_type", length = 50)
    private String entityType; // 'budget', 'goal', 'recurring_template'

    @Column(name = "entity_id")
    private Long entityId; // riferimento generico all'ID dell'entità

    @Size(max = 1000, message = "Parameters must not exceed 1000 characters")
    @Column(name = "parameters", columnDefinition = "JSON", length = 1000)
    private String parameters; // JSON string: {"category":"Cibo", "amount":"150", "limit":"100"}

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Notification() {
    }

    public Notification(NotificationType notificationType, String entityType,
            Long entityId, String parameters) {
        this.notificationType = notificationType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.parameters = parameters;
        this.isRead = false;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Business methods
    public String buildTitle() {
        if (notificationType == null || parameters == null) {
            return "Notification";
        }

        // Simple parameter parsing for JSON-like format
        String[] keyValues = parseParameters();
        return notificationType.buildTitle(keyValues);
    }

    public String buildMessage() {
        if (notificationType == null || parameters == null) {
            return "No message available";
        }

        String[] keyValues = parseParameters();
        return notificationType.buildMessage(keyValues);
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public boolean isUnread() {
        return !isRead;
    }

    public NotificationSeverity getSeverity() {
        return notificationType != null ? notificationType.getSeverity() : NotificationSeverity.INFO;
    }

    // Helper method to parse JSON-like parameters
    private String[] parseParameters() {
        if (parameters == null || parameters.trim().isEmpty()) {
            return new String[0];
        }

        // Simple JSON parsing - in production use Jackson or similar
        String clean = parameters.replaceAll("[{}\"\\s]", "");
        String[] pairs = clean.split(",");
        String[] result = new String[pairs.length * 2];

        for (int i = 0; i < pairs.length; i++) {
            String[] keyValue = pairs[i].split(":");
            if (keyValue.length == 2) {
                result[i * 2] = keyValue[0];
                result[i * 2 + 1] = keyValue[1];
            }
        }

        return result;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{"
                + "id=" + id
                + ", type=" + (notificationType != null ? notificationType.getCode() : null)
                + ", entityType='" + entityType + '\''
                + ", entityId=" + entityId
                + ", isRead=" + isRead
                + ", createdAt=" + createdAt
                + '}';
    }
}
