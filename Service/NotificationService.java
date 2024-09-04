package com.example.Event.Management.Service;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Event.Management.Entity.Notification;
import com.example.Event.Management.EventRepository.NotificationRepository;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void saveNotification(Notification notification) {
        try {
            notificationRepository.save(notification);
            logger.info("Notification saved: {}", notification);
        } catch (Exception e) {
            logger.error("Error saving notification: {}", e.getMessage());
            throw e;
        }
    }

    public void clearAllNotifications() {
        try {
            notificationRepository.deleteAll();
            logger.info("All notifications have been cleared.");
        } catch (Exception e) {
            logger.error("Error clearing notifications: {}", e.getMessage());
            throw e;
        }
    }
}
