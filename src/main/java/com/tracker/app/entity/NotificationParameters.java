package com.tracker.app.entity;

/**
 * Costanti per i parametri delle notifiche.
 * Usare queste costanti per evitare errori di battitura nei template.
 */
public final class NotificationParameters {
    
    // Budget-related parameters
    public static final String CATEGORY = "category";
    public static final String AMOUNT = "amount";
    public static final String LIMIT = "limit";
    public static final String SPENT = "spent";
    public static final String PERCENTAGE = "percentage";
    public static final String REMAINING = "remaining";
    
    // Goal-related parameters
    public static final String GOAL_NAME = "goalName";
    public static final String CURRENT_AMOUNT = "currentAmount";
    public static final String TARGET_AMOUNT = "targetAmount";
    public static final String PROGRESS = "progress";
    public static final String DEADLINE = "deadline";
    public static final String DAYS_LEFT = "daysLeft";
    
    // Transaction-related parameters
    public static final String TRANSACTION_AMOUNT = "transactionAmount";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String TYPE = "type"; // INCOME/EXPENSE
    
    // Recurring template parameters
    public static final String TEMPLATE_NAME = "templateName";
    public static final String FREQUENCY = "frequency";
    public static final String NEXT_DATE = "nextDate";
    
    // Generic parameters
    public static final String COUNT = "count";
    public static final String TOTAL = "total";
    public static final String PERIOD = "period";
    public static final String STATUS = "status";
    
    // Private constructor per evitare istanziazione
    private NotificationParameters() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
