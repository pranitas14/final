package com.example.Event.Management.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Event.Management.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllNotifications() {
        try {
            notificationService.clearAllNotifications();
            return ResponseEntity.ok("All notifications have been cleared.");
        } catch (Exception e) {
            logger.error("Error clearing notifications: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear notifications.");
        }
        
    }
}
