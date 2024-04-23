package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.create(notification);
    }

    @Override
    public List<Notification> findAll() {
        Iterator<Notification> notificationIterator = notificationRepository.findAll();
        List<Notification> allNotifications = new ArrayList<>();
        notificationIterator.forEachRemaining(allNotifications::add);
        return allNotifications;
    }

    @Override
    public Notification findById(String id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification update(String id, Notification updatedNotification) {
        return notificationRepository.update(id, updatedNotification);
    }
}
