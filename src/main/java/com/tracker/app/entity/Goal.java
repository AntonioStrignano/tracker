package com.tracker.app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @NotBlank(message = "Goal name is required")
    @Size(max = 100, message = "Goal name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull(message = "Target amount is required")
    @PositiveOrZero(message = "Target amount must be positive or zero")
    @Column(name = "target_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal targetAmount;

    @PositiveOrZero(message = "Current amount must be positive or zero")
    @Column(name = "current_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "last_calculated", nullable = false)
    private LocalDateTime lastCalculated = LocalDateTime.now();

    @Column(name = "deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private GoalPriority priority = GoalPriority.MEDIUM;

    @Column(name = "is_achieved", nullable = false)
    private Boolean isAchieved = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Goal() {
    }

    public Goal(String name, BigDecimal targetAmount, LocalDate deadline, GoalPriority priority) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.priority = priority != null ? priority : GoalPriority.MEDIUM;
        this.currentAmount = BigDecimal.ZERO;
        this.lastCalculated = LocalDateTime.now();
        this.isAchieved = false;
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        // Auto-check if goal is achieved
        if (this.currentAmount.compareTo(this.targetAmount) >= 0) {
            this.isAchieved = true;
        }
    }

    // Business methods
    // Smart current amount management
    public BigDecimal getCurrentAmountForContext(String context) {
        return switch (context) {
            case "API_LIST" ->
                getCurrentAmountCached();           // Lista goals - cached OK
            case "API_DETAIL" ->
                getCurrentAmountFresh();          // Dettaglio singolo - fresh
            case "DASHBOARD" ->
                getCurrentAmountSmart();           // Dashboard - smart refresh
            case "TRANSACTION_CREATE" ->
                getCurrentAmountFresh();   // Dopo transazione - fresh
            default ->
                getCurrentAmountCached();
        };
    }

    // Sempre cached (per liste/dashboard)
    private BigDecimal getCurrentAmountCached() {
        return this.currentAmount;
    }

    // Sempre fresh (per dettagli/update) - placeholder for service injection
    private BigDecimal getCurrentAmountFresh() {
        // TODO: This will be implemented in service layer with transaction calculation
        // For now, return cached value and mark for refresh
        this.lastCalculated = LocalDateTime.now();
        return this.currentAmount;
    }

    // Smart: fresh solo se stale
    private BigDecimal getCurrentAmountSmart() {
        if (isStaleData()) {
            return getCurrentAmountFresh();
        }
        return getCurrentAmountCached();
    }

    // Configurable staleness based on priority
    private boolean isStaleData() {
        java.time.Duration staleThreshold = getStaleThresholdByPriority();
        return lastCalculated.isBefore(LocalDateTime.now().minus(staleThreshold));
    }

    private java.time.Duration getStaleThresholdByPriority() {
        return switch (this.priority) {
            case HIGH ->
                java.time.Duration.ofMinutes(15);    // Goal alta priorità = refresh frequente
            case MEDIUM ->
                java.time.Duration.ofHours(1);     // Media priorità = 1 ora
            case LOW ->
                java.time.Duration.ofHours(6);        // Bassa priorità = 6 ore
        };
    }

    // Update cached amount (called from service layer)
    public void updateCachedAmount(BigDecimal newAmount) {
        this.currentAmount = newAmount;
        this.lastCalculated = LocalDateTime.now();
    }

    public BigDecimal getProgressPercentage() {
        if (targetAmount.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return currentAmount.divide(targetAmount, 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getRemainingAmount() {
        return targetAmount.subtract(currentAmount);
    }

    public boolean isOverdue() {
        return deadline != null && LocalDate.now().isAfter(deadline) && !isAchieved;
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

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public GoalPriority getPriority() {
        return priority;
    }

    public void setPriority(GoalPriority priority) {
        this.priority = priority;
    }

    public Boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastCalculated() {
        return lastCalculated;
    }

    public void setLastCalculated(LocalDateTime lastCalculated) {
        this.lastCalculated = lastCalculated;
    }

    @Override
    public String toString() {
        return "Goal{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", targetAmount=" + targetAmount
                + ", currentAmount=" + currentAmount
                + ", priority=" + priority
                + ", isAchieved=" + isAchieved
                + '}';
    }
}
