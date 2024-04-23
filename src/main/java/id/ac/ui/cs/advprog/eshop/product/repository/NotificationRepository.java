package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class NotificationRepository {
    private List<Notification> notificationData = new ArrayList<>();

    public Notification create(Notification notification) {
        if (notification == null) {
            return null;
        }
        if (notification.getNotificationId() == null || notification.getNotificationId().isEmpty()) {
            notification.setNotificationId(java.util.UUID.randomUUID().toString());
        }
        notificationData.add(notification);
        return notification;
    }

    public Iterator<Notification> findAll() {
        return notificationData.iterator();
    }

    public Notification findById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (Notification notification : notificationData) {
            if (notification.getNotificationId().equals(id)) {
                return notification;
            }
        }
        return null; // Return null if no notification is found with the given ID
    }

    public Notification update(String id, Notification updatedNotification) {
        if (id == null || id.isEmpty() || updatedNotification == null) {
            return null;
        }
        for (int i = 0; i < notificationData.size(); i++) {
            if (notificationData.get(i).getNotificationId().equals(id)) {
                updatedNotification.setNotificationId(id); // Ensure the updated notification has the correct ID
                notificationData.set(i, updatedNotification);
                return updatedNotification;
            }
        }
        return null;
    }
}
