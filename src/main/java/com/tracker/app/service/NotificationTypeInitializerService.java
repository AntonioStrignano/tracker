package com.tracker.app.service;

import com.tracker.app.entity.NotificationType;
import com.tracker.app.entity.NotificationTypeTemplate;
// TODO: import com.tracker.app.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Service per inizializzare i tipi di notifica nel database.
 * Questo service viene eseguito all'avvio dell'applicazione e popola
 * la tabella notification_types con i template predefiniti.
 * 
 * TODO: Decommentare quando avremo creato NotificationTypeRepository
 */
// @Service
public class NotificationTypeInitializerService /* implements CommandLineRunner */ {

    // @Autowired
    // private NotificationTypeRepository notificationTypeRepository;

    // @Override
    public void run(String... args) throws Exception {
        // TODO: Decommentare quando avremo NotificationTypeRepository
        // initializeNotificationTypes();
    }

    /**
     * Inizializza i tipi di notifica nel database se non esistono già.
     * Converte gli enum NotificationTypeTemplate in entità NotificationType.
     */
    private void initializeNotificationTypes() {
        // TODO: Implementare quando avremo il repository
        /*
        for (NotificationTypeTemplate template : NotificationTypeTemplate.values()) {
            // Controlla se il tipo esiste già
            if (!notificationTypeRepository.existsByCode(template.getCode())) {
                NotificationType notificationType = template.toEntity();
                notificationTypeRepository.save(notificationType);
                System.out.println("Initialized notification type: " + template.getCode());
            }
        }
        */
    }

    /**
     * Metodo per aggiornare i template esistenti (utile durante lo sviluppo).
     * ATTENZIONE: Questo sovrascrive i template esistenti!
     */
    public void updateNotificationTypes() {
        // TODO: Implementare quando avremo il repository
        /*
        for (NotificationTypeTemplate template : NotificationTypeTemplate.values()) {
            NotificationType existing = notificationTypeRepository.findByCode(template.getCode());
            if (existing != null) {
                // Aggiorna solo i template, mantiene ID e stato attivo
                existing.setTitleTemplate(template.getTitleTemplate());
                existing.setMessageTemplate(template.getMessageTemplate());
                existing.setSeverity(template.getSeverity());
                notificationTypeRepository.save(existing);
            } else {
                // Crea nuovo se non esiste
                NotificationType notificationType = template.toEntity();
                notificationTypeRepository.save(notificationType);
            }
        }
        */
    }
}
