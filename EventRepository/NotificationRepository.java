package com.example.Event.Management.EventRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Event.Management.Entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
