package com.example.Event.Management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Event.Management.Entity.PushNotificationRequest;
import com.example.Event.Management.Entity.Notification;
import com.example.Event.Management.Service.NotificationService;
import com.example.Event.Management.Service.PushNotificationService;
import com.google.firebase.messaging.Notification.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class PushNotificationController {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationController.class);
    private final PushNotificationService pushNotificationService;
    private final NotificationService notificationService;

    public PushNotificationController(PushNotificationService pushNotificationService, NotificationService notificationService) {
        this.pushNotificationService = pushNotificationService;
        this.notificationService = notificationService;
    }

    @PostMapping("/notification/token")
    public ResponseEntity<String> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        try {
            // Send notification
            pushNotificationService.sendPushNotificationToToken(request);

            // Save notification to the database
            Notification notification = new Notification(request.getTitle(), request.getMessage(), request.getToken());
            notificationService.saveNotification(notification);

            return new ResponseEntity<>("Notification has been sent.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error sending token notification: {}", e.getMessage());
            return new ResponseEntity<>("Failed to send notification.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/push-notifications/clear")
    public ResponseEntity<String> clearAllPushNotifications() {
        try {
            notificationService.clearAllNotifications();
            return ResponseEntity.ok("All push notifications have been cleared.");
        } catch (Exception e) {
            logger.error("Error clearing push notifications: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear push notifications.");
        }
    }

	public Builder getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
}
