package com.tracker.app.entity;

/**
 * Enum per i tipi di notifica predefiniti con parametri sicuri.
 * Questo garantisce che i template e i parametri siano sempre consistenti.
 */
public enum NotificationTypeTemplate {
    
    // Budget notifications
    BUDGET_EXCEEDED(
        "BUDGET_EXCEEDED",
        "Budget superato: {" + NotificationParameters.CATEGORY + "}",
        "Hai speso €{" + NotificationParameters.SPENT + "} nella categoria {" + 
        NotificationParameters.CATEGORY + "}, superando il limite di €{" + 
        NotificationParameters.LIMIT + "}",
        NotificationSeverity.WARNING
    ),
    
    BUDGET_WARNING(
        "BUDGET_WARNING", 
        "Attenzione budget {" + NotificationParameters.CATEGORY + "}",
        "Hai raggiunto il {" + NotificationParameters.PERCENTAGE + 
        "}% del budget per {" + NotificationParameters.CATEGORY + 
        "} (€{" + NotificationParameters.SPENT + "}/€{" + NotificationParameters.LIMIT + "})",
        NotificationSeverity.INFO
    ),
    
    // Goal notifications
    GOAL_ACHIEVED(
        "GOAL_ACHIEVED",
        "Obiettivo raggiunto! 🎉",
        "Congratulazioni! Hai raggiunto l'obiettivo '{" + NotificationParameters.GOAL_NAME + 
        "}' con €{" + NotificationParameters.CURRENT_AMOUNT + "}",
        NotificationSeverity.SUCCESS
    ),
    
    GOAL_PROGRESS(
        "GOAL_PROGRESS",
        "Progresso obiettivo: {" + NotificationParameters.GOAL_NAME + "}",
        "Sei al {" + NotificationParameters.PROGRESS + 
        "}% dell'obiettivo '{" + NotificationParameters.GOAL_NAME + 
        "}' (€{" + NotificationParameters.CURRENT_AMOUNT + "}/€{" + 
        NotificationParameters.TARGET_AMOUNT + "})",
        NotificationSeverity.INFO
    ),
    
    GOAL_DEADLINE_WARNING(
        "GOAL_DEADLINE_WARNING",
        "Scadenza obiettivo vicina",
        "L'obiettivo '{" + NotificationParameters.GOAL_NAME + 
        "}' scade tra {" + NotificationParameters.DAYS_LEFT + "} giorni. " +
        "Progresso attuale: {" + NotificationParameters.PROGRESS + "}%",
        NotificationSeverity.WARNING
    ),
    
    // Recurring template notifications
    RECURRING_DUE(
        "RECURRING_DUE",
        "Transazione ricorrente programmata",
        "È il momento di registrare '{" + NotificationParameters.TEMPLATE_NAME + 
        "}' per €{" + NotificationParameters.AMOUNT + "}",
        NotificationSeverity.INFO
    ),
    
    RECURRING_OVERDUE(
        "RECURRING_OVERDUE",
        "Transazione ricorrente in ritardo",
        "La transazione '{" + NotificationParameters.TEMPLATE_NAME + 
        "}' era programmata per il {" + NotificationParameters.DATE + 
        "} e non è ancora stata registrata",
        NotificationSeverity.WARNING
    );
    
    private final String code;
    private final String titleTemplate;
    private final String messageTemplate;
    private final NotificationSeverity severity;
    
    NotificationTypeTemplate(String code, String titleTemplate, 
                           String messageTemplate, NotificationSeverity severity) {
        this.code = code;
        this.titleTemplate = titleTemplate;
        this.messageTemplate = messageTemplate;
        this.severity = severity;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getTitleTemplate() {
        return titleTemplate;
    }
    
    public String getMessageTemplate() {
        return messageTemplate;
    }
    
    public NotificationSeverity getSeverity() {
        return severity;
    }
    
    /**
     * Crea un'istanza di NotificationType da questo template
     */
    public NotificationType toEntity() {
        return new NotificationType(code, titleTemplate, messageTemplate, severity);
    }
}
