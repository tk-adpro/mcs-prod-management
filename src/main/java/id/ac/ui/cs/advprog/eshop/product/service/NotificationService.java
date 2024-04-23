package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;

import java.util.Iterator;

public interface NotificationService {
    Notification create(Notification notification);
    Iterator<Notification> findAll();
    Notification findById(String id);
    Notification update(String id, Notification updatedNotification);
}
