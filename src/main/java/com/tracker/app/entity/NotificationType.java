package com.tracker.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "notification_types")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_type_id")
    private Long id;

    @NotBlank(message = "Notification code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code; // 'BUDGET_EXCEEDED', 'GOAL_REMINDER', 'RECURRING_DUE'

    @NotBlank(message = "Title template is required")
    @Size(max = 100, message = "Title template must not exceed 100 characters")
    @Column(name = "title_template", nullable = false, length = 100)
    private String titleTemplate; // "Budget {category} superato!"

    @NotBlank(message = "Message template is required")
    @Size(max = 500, message = "Message template must not exceed 500 characters")
    @Column(name = "message_template", nullable = false, length = 500)
    private String messageTemplate; // "Hai speso €{amount} su budget di €{limit}"

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 10)
    private NotificationSeverity severity = NotificationSeverity.INFO;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Constructors
    public NotificationType() {
    }

    public NotificationType(String code, String titleTemplate, String messageTemplate,
            NotificationSeverity severity) {
        this.code = code;
        this.titleTemplate = titleTemplate;
        this.messageTemplate = messageTemplate;
        this.severity = severity != null ? severity : NotificationSeverity.INFO;
        this.isActive = true;
    }

    // Business methods
    public String buildTitle(String... params) {
        String result = titleTemplate;
        for (int i = 0; i < params.length; i += 2) {
            if (i + 1 < params.length) {
                String placeholder = "{" + params[i] + "}";
                String value = params[i + 1];
                result = result.replace(placeholder, value);
            }
        }
        return result;
    }

    public String buildMessage(String... params) {
        String result = messageTemplate;
        for (int i = 0; i < params.length; i += 2) {
            if (i + 1 < params.length) {
                String placeholder = "{" + params[i] + "}";
                String value = params[i + 1];
                result = result.replace(placeholder, value);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public NotificationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "NotificationType{"
                + "id=" + id
                + ", code='" + code + '\''
                + ", severity=" + severity
                + ", isActive=" + isActive
                + '}';
    }
}
