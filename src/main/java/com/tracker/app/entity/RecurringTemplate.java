package com.tracker.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_templates")
public class RecurringTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    @NotBlank(message = "Template name is required")
    @Size(max = 100, message = "Template name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull(message = "Amount is required")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description", length = 255)
    private String description;

    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private TransactionType type;

    @NotNull(message = "Category is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "Interval days is required")
    @Positive(message = "Interval days must be positive")
    @Column(name = "interval_days", nullable = false)
    private Integer intervalDays; // 1=daily, 7=weekly, 30=monthly

    @Column(name = "last_generated")
    private LocalDate lastGenerated;

    @NotNull(message = "Next generation date is required")
    @Column(name = "next_generation", nullable = false)
    private LocalDate nextGeneration;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public RecurringTemplate() {
    }

    public RecurringTemplate(String name, BigDecimal amount, String description,
            TransactionType type, Category category, Integer intervalDays) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.category = category;
        this.intervalDays = intervalDays;
        this.isActive = true;
        // nextGeneration will be calculated in service layer
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Business methods
    public LocalDate calculateNextGeneration() {
        LocalDate baseDate = (lastGenerated != null) ? lastGenerated : LocalDate.now();
        return baseDate.plusDays(intervalDays);
    }

    public boolean isDue() {
        return LocalDate.now().isAfter(nextGeneration) || LocalDate.now().isEqual(nextGeneration);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(Integer intervalDays) {
        this.intervalDays = intervalDays;
    }

    public LocalDate getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(LocalDate lastGenerated) {
        this.lastGenerated = lastGenerated;
    }

    public LocalDate getNextGeneration() {
        return nextGeneration;
    }

    public void setNextGeneration(LocalDate nextGeneration) {
        this.nextGeneration = nextGeneration;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RecurringTemplate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", amount=" + amount
                + ", type=" + type
                + ", intervalDays=" + intervalDays
                + ", isActive=" + isActive
                + '}';
    }
}
